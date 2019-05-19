package mad.movieNightPlanner.app.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import mad.movieNightPlanner.app.view.UserSettings;

public class UserSettingsListener implements View.OnClickListener {

    Context context;

    public UserSettingsListener(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(context, UserSettings.class);
        context.startActivity(intent);

    }
}
