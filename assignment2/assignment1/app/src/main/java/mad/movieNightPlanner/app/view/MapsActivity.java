package mad.movieNightPlanner.app.view;


import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.app.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import mad.movieNightPlanner.app.model.Event;
import mad.movieNightPlanner.app.model.Singleton;
import mad.movieNightPlanner.app.model.SortByDateAndTime;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "MapsActivity";

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_maps_activity);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LocalDate current =LocalDate.now();
        ArrayList<Event> events = Singleton.getInstance().getModel().getAllEvents();
        ArrayList<Event> soonestEvents = new ArrayList<>();
        Collections.sort(events, new SortByDateAndTime());
        for(Event event: events){
            if(event.getStartDate().compareTo(current)>=0){
                soonestEvents.add(event);
            }
        }

        if(!soonestEvents.isEmpty()){
            for(int i =0;i<3;i++){
                if(soonestEvents.size()>=i){

                    //creates location into a double array
                    String[] strLocation = soonestEvents.get(i).getLocation().split(",");
                    double[] location= new double[2];
                    location[0]=Double.parseDouble(strLocation[0]);
                    location[1]=Double.parseDouble(strLocation[1]);

                    //adds the location to google maps activity
                    LatLng eventLocation = new LatLng(location[0], location[1]);
                    mMap.addMarker(new MarkerOptions().position(eventLocation).title("Marker in "+soonestEvents.get(i).getTitle()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eventLocation, 15));
                    Log.i(TAG, "onMapReady: " +soonestEvents.get(i).getId()+" Location: "+ soonestEvents.get(i).getLocation());

                }

            }
        }
//        mMap.animateCamera(CameraUpdateFactory.zoomIn());
//        mMap.animateCamera(CameraUpdateFactory.zoomIn());
//        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        Log.i(TAG, "onMapReady: done");


        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
