package com.example.app;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import mad.movieNightPlanner.app.controller.CalanderViewListener;
import mad.movieNightPlanner.app.controller.EditEventListener;
import mad.movieNightPlanner.app.controller.GoogleMapLocationListener;
import mad.movieNightPlanner.app.controller.ListOfEventsListener;
import mad.movieNightPlanner.app.controller.UserSettingsListener;
import mad.movieNightPlanner.app.model.FileLoader;
import mad.movieNightPlanner.app.view.LocationJobService;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private final String EXTRA_NOTIFICATION_ID ="w";
    private JobScheduler jobScheduler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AssetManager assetManager = getAssets();

        //loads event and movie files
        try {
            new FileLoader(assetManager, "events.txt", "movies.txt");
        } catch (IOException e) {
            Log.i(TAG, "onCreate: error trying to access the file");
            Log.i(TAG, "onCreate: error"+e.getMessage());
            e.printStackTrace();
        } catch(Exception e){
            Log.i(TAG, "onCreate: there was an error trying to read the files, not IOException");
            Log.i(TAG, "onCreate: error message: "+e.getMessage());
            e.printStackTrace();
        }

        Button addEventButton = findViewById(R.id.addEvent);

        addEventButton.setOnClickListener(new EditEventListener(this, -1));


        final Button buttonevents = findViewById(R.id.listOfEvents);
        buttonevents.setOnClickListener(new ListOfEventsListener(this));

        final Button buttoncal = findViewById(R.id.seeCalander);
        buttoncal.setOnClickListener(new CalanderViewListener(this));

        Button buttonMaps = findViewById(R.id.google_maps_soonest);
        buttonMaps.setOnClickListener(new GoogleMapLocationListener(this));

        Button buttonSettings = findViewById(R.id.user_settings);
        buttonSettings.setOnClickListener(new UserSettingsListener(this));
       // setAlarm();
        scheduleJob();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    // NOTE: We have set minimumSdkVersion below Build.VERSION_CODES.N and are doing a check for
    // whether we should execute this code
    @TargetApi(Build.VERSION_CODES.N)
    private void scheduleJob()
    {
        Log.i(TAG, "scheduleJob: starting to be schuelded ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            // create a builder to make a JobInfo for the JobService so we can schedule it
            // according to certain contraints (note use of ComponentName!)
            JobInfo.Builder builder = new JobInfo.Builder(LocationJobService.SERVICE_ID, new ComponentName(this,
                    LocationJobService.class));

            // delay start (only if it is not a periodic job!)
            builder.setMinimumLatency(1000*3);
            builder.setOverrideDeadline(1000*6);
            // minimum periodic is currently 15 minutes so only if you are patient enough to test!
            Log.d(TAG, String.format("Minimum periodic period (getMinPeriodMillis()): %d mins"
                    , TimeUnit.MILLISECONDS.toMinutes(JobInfo.getMinPeriodMillis())));
            // comment out setMinimumLatency() call above to do periodic scheduling
            //builder.setPeriodic(TimeUnit.MINUTES.toMillis(15));
            // requires network .. see API for other options
            //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
            // Schedule job
            Log.d(TAG, "Scheduling job");
            jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
            jobScheduler.schedule(builder.build());
        }
        else
            //just log a warning but could execute some alternative code such as a regular service
            versionWarning();
    }

    @Override
    protected void onStop()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            // cancel the job if we exit (but could just let it run)
            jobScheduler.cancel(LocationJobService.SERVICE_ID);
        }
        else versionWarning();
        super.onStop();
    }

    private void versionWarning()
    {
        Log.d(TAG, String.format("JobScheduler code requires API >=%d", Build.VERSION_CODES.N));
    }

    private void setAlarm(){
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);


        Calendar c= Calendar.getInstance();
        long interval =1000*30+ c.getTimeInMillis();
        Intent serviceIntent = new Intent(this, LocationJobService.class);
        PendingIntent service =PendingIntent.getService(this, LocationJobService.SERVICE_ID, serviceIntent, 0 );
        am.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis()+ 6*1000, service );
        //Log.i(TAG, "setAlarm: "+am.getNextAlarmClock().toString());
        Log.i(TAG, "setAlarm: alarm set");

    }



}
