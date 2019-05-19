package mad.movieNightPlanner.app.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import mad.movieNightPlanner.app.view.MapsActivity;

public class GoogleMapLocationListener implements View.OnClickListener {

    Context context;

    public GoogleMapLocationListener(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, MapsActivity.class);

        context.startActivity(intent);
    }
}
