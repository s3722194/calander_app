package mad.movieNightPlanner.app.controller;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DismissBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "DismissBroadcastReceive";
    
    @Override
    public void onReceive(Context context, Intent intent) {
        int i =intent.getIntExtra("eventID",-1);
        Log.i(TAG, "onReceive: noftication has been dismiss");
       // NotificationViewModel.getInstance().g
        NotificationManager nm = context.getSystemService(NotificationManager.class);
        nm.cancel(i);
        
    }

}
