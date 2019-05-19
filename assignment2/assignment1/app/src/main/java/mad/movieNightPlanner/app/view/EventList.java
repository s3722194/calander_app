package mad.movieNightPlanner.app.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.app.R;

import mad.movieNightPlanner.app.model.Singleton;

public class EventList extends AppCompatActivity {

    private static final String TAG = "EventList";


    private EventListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list);
        RecyclerView recyclerView =  findViewById(R.id.event_list);

        Log.i(TAG, "onCreate: " + Singleton.getInstance().getModel().getAllEvents().size());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new EventListAdapter( this);
        recyclerView.setAdapter(mAdapter);

        // if the event list should be sorted by ascending order
        Button ascending =findViewById(R.id.sort_ascending);
        ascending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.setSortByDesecnding(false);
                mAdapter.notifyDataSetChanged();
            }
        });

        //if the event list should be sorted by descending order
        Button descending =findViewById(R.id.sort_decsedning);
        descending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.setSortByDesecnding(true);
                mAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }


}
