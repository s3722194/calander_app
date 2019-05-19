package mad.movieNightPlanner.app.controller;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import mad.movieNightPlanner.app.model.Singleton;

public class RemoveEventListener implements View.OnClickListener  {

    private Context context;
    private int eventID;

    public RemoveEventListener(Context context, int eventID) {
        this.context = context;
        this.eventID = eventID;
    }

    @Override
    public void onClick(View v) {

        //only removes the event if the event exist
        if(eventID!=-1){
            Singleton.getInstance().getModel().removeEvent(eventID);
            ((Activity)context).finish();

        } else{
            Toast.makeText(context, "Event doesn't exist!", Toast.LENGTH_LONG).show();
        }


    }
}
