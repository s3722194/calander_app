package mad.movieNightPlanner.app.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class DistanceMatrixJSON {

    private static final String TAG = "DistanceMatrixJSON";

    private String matrix;
    private int duration;
    private String status;

    public DistanceMatrixJSON(String matrix) throws JSONException {
        this.matrix = matrix;
        parsing();

    }

    private void parsing() throws JSONException {


        JSONObject location = new JSONObject(matrix)
                .getJSONArray("rows")
                .getJSONObject(0)
                .getJSONArray("elements")
                .getJSONObject(0)
                .getJSONObject("duration");
        status = new JSONObject(matrix).getString("status");

        Log.i(TAG, "parsing: status: "+ status);
        duration = location.getInt("value");
        Log.d(TAG, "parsing: duration: "+ duration);

    }

    public int getDuration() {
        return duration;
    }

    public String getStatus() {
        return status;
    }
}
