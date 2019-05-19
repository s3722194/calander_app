package mad.movieNightPlanner.app.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import mad.movieNightPlanner.app.view.EventList;

public class ListOfEventsListener implements View.OnClickListener  {


    private Context context;


    public ListOfEventsListener(Context context){

        this.context =context;

    }
    @Override
    public void onClick(View v) {

        //start event list activity
        Intent intent = new Intent(context, EventList.class);

        context.startActivity(intent);

    }
}
