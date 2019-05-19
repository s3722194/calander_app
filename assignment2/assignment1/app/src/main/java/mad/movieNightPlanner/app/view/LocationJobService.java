package mad.movieNightPlanner.app.view;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import mad.movieNightPlanner.app.model.DistanceMatrixHttp;
import mad.movieNightPlanner.app.model.Event;
import mad.movieNightPlanner.app.model.Singleton;

public class LocationJobService extends JobService {

    private static final String TAG = "LocationJobService";

    public final static int SERVICE_ID = 100;

    public static int matrixCounter=0;

    private JobThread jobThread;

    public  class JobThread extends Thread {
        private static final int SECS = 10;
        private JobParameters params;

        private JobThread(JobParameters params) {
            this.params = params;
        }

        // run an example dummy task for i SECS
        @Override
        public void run() {
            Log.i(TAG, "run: location serivce is running");
            getUserLocation();

//

            jobFinished(params, false);
        }

        private void getUserLocation(){
            final LocationManager locationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);
            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    // Called when a new location is found by the network location provider.
                    getDistanceMatrix(location);


                    locationManager.removeUpdates(this);
                }

                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                public void onProviderEnabled(String provider) {
                }

                public void onProviderDisabled(String provider) {
                }
            };
            try{
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener, Looper.getMainLooper());

            } catch(SecurityException e){
                e.printStackTrace();

            } catch(Exception e){
                e.printStackTrace();
            }

        }

        private void getDistanceMatrix(Location location){
            double userLat= location.getLatitude();
            double userLong =location.getLongitude();
            //String matrix_url ="https://maps.googleapis.com/maps/api/distancematrix/json?origins=";

            for(Event event: Singleton.getInstance().getModel().getAllEvents()){
                String matrix_url ="https://maps.googleapis.com/maps/api/distancematrix/json?origins=";
                Log.i(TAG, "getDistanceMatrix: event id: "+event.getId());
                matrix_url+=userLat+","+userLong+"&destinations=";
                matrix_url+=event.getLocation()+"&mode=driving&key=AIzaSyAl1wrF2bsqeCM-ALe2Ssa4OYOb_ket7A4";
                matrix_url=matrix_url.replaceAll("\\s+","");
                Log.i(TAG, "getDistanceMatrix: "+matrix_url);
                DistanceMatrixHttp matrix = new DistanceMatrixHttp( matrix_url, event.getId(), getApplicationContext());
                matrix.execute();
                break;
            }


          //  String matrix_url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=-37.730222,145.001974&destinations=-37.809010,144.965281&mode=driving&key=AIzaSyAl1wrF2bsqeCM-ALe2Ssa4OYOb_ket7A4";


            Log.i(TAG, "getDistanceMatrix: getting matrix");

            //matrix.
            


        }

        


    }

    @Override
    public boolean onStartJob(JobParameters params)
    {
        Log.i(TAG, "onStartJob: location service job has started");
        jobThread = new JobThread(params);
        jobThread.start();
        // signal that we are doing some work
        return true;
    }
    


    @Override
    // this is only called if we have to finish early because schedule criteria have changed
    // or we explicitly cancel if the Activity is stopped
    public boolean onStopJob(JobParameters params)
    {
        Log.d(TAG, "Job finished - calling interrupt() on worker thread");
        if (jobThread != null)
            jobThread.interrupt();
        // signal that we have finished
        return false;
    }






}
