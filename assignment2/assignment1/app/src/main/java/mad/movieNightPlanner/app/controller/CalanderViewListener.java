package mad.movieNightPlanner.app.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import mad.movieNightPlanner.app.view.CustomCalenderView;

public class CalanderViewListener implements View.OnClickListener {

    private Context context;



    public CalanderViewListener(Context context){

        this.context =context;


    }
    @Override
    public void onClick(View v) {

        //starts the custom calander view activity
        Intent intent = new Intent(context, CustomCalenderView.class);

        context.startActivity(intent);
    }


}
