package mad.movieNightPlanner.app.model;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import mad.movieNightPlanner.app.view.NotificationJobService;


public class DistanceMatrixHttp extends AsyncTask<Void, Integer, String> {

    private static final String TAG = "DistanceMatrixHttp";
    private String martix_url;

    private int eventID;

    private Context context;


    public DistanceMatrixHttp(String martix_url, int eventID, Context context) {
        this.martix_url = martix_url;

        this.eventID=eventID;
        this.context=context;



    }

    @Override
    protected String doInBackground(Void... voids) {
        String json=null;

        HttpClient httpclient = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(martix_url);

        try
        {
            Log.i(TAG, "doInBackground: starting to conecto to matrix api");
            // the easy way using BasicResponseHandler
            String responseBody = httpclient.execute(getRequest, new BasicResponseHandler());
            // log the full HTML
            Log.i(TAG, "doInBackground: "+responseBody);

            // the manual way retrieving a content entity
            HttpResponse response = httpclient.execute(getRequest);
            HttpEntity entity = response.getEntity();

            BufferedReader br =new BufferedReader(new InputStreamReader(entity.getContent()));

            StringBuilder htmlStringBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null)
            {
                // log individual line
                Log.i(TAG, line);
                htmlStringBuilder.append(line);

            }

            json = htmlStringBuilder.toString();
            //matrix = new DistanceMatrixJSON(json);




        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return json;
    }

    protected void onPostExecute(String result) {
        Intent i = new Intent();
        i.putExtra("eventID", eventID);
        i.putExtra("json", result);
        NotificationJobService.enqueueWork(context, i);

    }


}
