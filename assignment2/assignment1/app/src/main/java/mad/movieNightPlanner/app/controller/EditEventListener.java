package mad.movieNightPlanner.app.controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import mad.movieNightPlanner.app.view.EventDetails;

public class EditEventListener implements View.OnClickListener  {
    private static final String TAG = "EditEventListener";
    private Context context;
    private int eventID;


    public EditEventListener(Context context, int eventID){

        this.context =context;
        this.eventID=eventID;

    }

    @Override
    public void onClick(View v) {

        Log.i(TAG, "edit event button");

        //EventDetails details = new EventDetails(model, eventID);

        Intent intent = new Intent(context, EventDetails.class);

        intent.putExtra("eventID", eventID);
        context.startActivity(intent);


    }
}
