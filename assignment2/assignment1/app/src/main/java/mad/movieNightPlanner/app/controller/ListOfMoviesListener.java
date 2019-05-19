package mad.movieNightPlanner.app.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import mad.movieNightPlanner.app.view.MovieList;

public class ListOfMoviesListener implements View.OnClickListener  {
    private static final String TAG = "EditEventListener";
    private Context context;
    private final int ADD_MOVIE =15;

    public ListOfMoviesListener(Context context){

        this.context =context;

    }



    @Override
    public void onClick(View v) {

        Log.i(TAG, "movie list button");


        //starts add movie activity
        Intent intent = new Intent(context, MovieList.class);


        ((Activity)context).startActivityForResult(intent, ADD_MOVIE);

    }
}
