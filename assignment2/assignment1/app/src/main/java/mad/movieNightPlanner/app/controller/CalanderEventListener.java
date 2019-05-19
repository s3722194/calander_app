package mad.movieNightPlanner.app.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.app.R;

import java.util.ArrayList;

import mad.movieNightPlanner.app.model.Event;
import mad.movieNightPlanner.app.view.EventDetails;

public class CalanderEventListener implements View.OnClickListener {
    private static final String TAG = "CalanderEventListener";

    private Context context;

    private ArrayList<Event> events;


    public CalanderEventListener(Context context, ArrayList<Event> events){

        this.context =context;

        this.events=events;

    }

    @Override
    public void onClick(View v) {

        Log.i(TAG, "onClick: " + events.size());

        if(events!=null){
            String[] eventTitle = new String[events.size()];

            for(int i =0;i<events.size();i++){
                eventTitle[i]=events.get(i).getTitle();
            }

            //shows the user a dialog for selecting which event they want to click on
            //then it display the event in the event editor
            AlertDialog.Builder builder = new AlertDialog.Builder(context);



            builder.setTitle(R.string.view_list_of_events)
                    .setItems(eventTitle, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(context, EventDetails.class);

                            intent.putExtra("eventID", events.get(which).getId());
                            context.startActivity(intent);
                        }
                    });
            AlertDialog dialog =builder.create();
            dialog.show();



        }


    }
}
