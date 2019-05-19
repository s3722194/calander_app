package mad.movieNightPlanner.app.view;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.app.R;

import org.json.JSONException;

import mad.movieNightPlanner.app.controller.DismissBroadcastReceiver;
import mad.movieNightPlanner.app.model.DistanceMatrixJSON;
import mad.movieNightPlanner.app.model.Event;
import mad.movieNightPlanner.app.model.Singleton;

public class NotificationJobService extends JobIntentService {
    private static final String TAG = "NotificationJobService";

    private String CHANNEL_ID="EVENTS";

    private int eventID;




    private final static int JOB_ID =200;

    /**
     * Convenience method for enqueuing work in to this service.
     */
    public static void enqueueWork(Context context, Intent work)
    {
        enqueueWork(context, NotificationJobService.class, JOB_ID, work);
    }
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        eventID= intent.getIntExtra("eventID", -1);
        String json =intent.getStringExtra("json");
        try {
            DistanceMatrixJSON matrix = new DistanceMatrixJSON(json);
            String status = matrix.getStatus();
            int duration = matrix.getDuration();
            Log.i(TAG, "onHandleWork: status"+ status);
            Log.i(TAG, "onHandleWork: duration"+duration);
            createNotification();

        } catch (JSONException e) {
            Log.i(TAG, "onHandleWork: error with the json parsing");
            e.printStackTrace();
        }
        Log.i(TAG, "onHandleWork: notfication work started" + eventID );

    }

    public void createNotification(){

        Event event= Singleton.getInstance().getModel().getEvent(eventID);

        Intent dismissIntent = new Intent(this, DismissBroadcastReceiver.class);
        dismissIntent.setAction("w");
        dismissIntent.putExtra("eventID", eventID);

        PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(this, 0, dismissIntent, PendingIntent.FLAG_ONE_SHOT);
        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(event.getTitle())
                .setContentText(event.getVenue()+" , "+event.getStartDate())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                .addAction(R.drawable.ic_launcher_background, "Dismiss",
                        dismissPendingIntent);
//                .addAction(R.drawable.ic_launcher_background, "Cancel",
//                        dismissPendingIntent)
//                .addAction(R.drawable.ic_launcher_background, "Remind",
//                        dismissPendingIntent);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);



        notificationManager.notify(eventID, builder.build());



    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.common_google_play_services_notification_channel_name);
            String description = getString(R.string.channel_description);
            int importance = android.app.NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            android.app.NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
