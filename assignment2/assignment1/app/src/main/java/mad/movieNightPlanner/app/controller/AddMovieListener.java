package mad.movieNightPlanner.app.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import mad.movieNightPlanner.app.model.EventModel;

public class AddMovieListener implements View.OnClickListener {
    private static final String TAG = "AddMovieListener";


    private Context context;
    private String movieID;

    public AddMovieListener( Context context, String movieID) {

        this.context = context;
        this.movieID = movieID;
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick: " + movieID);

        //returns a movie that the user has clicked on
        Intent returnIntent = new Intent();
        returnIntent.putExtra("movieID",movieID);
        ((Activity)context).setResult(Activity.RESULT_OK,returnIntent);

        ((Activity)context).finish();

    }
}
