package mad.movieNightPlanner.app.view;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import mad.movieNightPlanner.app.controller.EditEventListener;
import mad.movieNightPlanner.app.model.Event;
import mad.movieNightPlanner.app.model.Singleton;
import mad.movieNightPlanner.app.model.SortByDateAndTime;

public class EventListAdapter extends RecyclerView.Adapter<EventListViewHolder> {

    private static final String TAG = MovieListAdapter.class.getName();
    private boolean sortByDesecnding=false;

    private Context context;

    public EventListAdapter( Context context) {

        this.context=context;
    }

    @NonNull
    @Override
    public EventListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_items, viewGroup, false);
        EventListViewHolder holder = new EventListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventListViewHolder eventListViewHolder, int i) {

        //gets the events from the model
        ArrayList<Event> events= Singleton.getInstance().getModel().getAllEvents();

        //checks to see how the list should be sorted
        Collections.sort(events, new SortByDateAndTime());
        if(sortByDesecnding){
            Collections.reverse(events);
        }

        Log.i(TAG, "onBindViewHolder: called" + i);

        //displays the event data
        eventListViewHolder.eventName.setText(events.get(i).getTitle());
        eventListViewHolder.venue.setText(events.get(i).getVenue());
        eventListViewHolder.startTime.setText(events.get(i).getStartTime().toString());
        eventListViewHolder.endTime.setText(events.get(i).getEndTime().toString());
        eventListViewHolder.date.setText(events.get(i).getStartDate().toString()+" to " + events.get(i).getEndDate().toString());

        if(events.get(i).getMovie()!=null){
            eventListViewHolder.movies.setText("Movie: "+(events.get(i).getMovie().getTitle()));
        } else{
            eventListViewHolder.movies.setText("Movie: None");

        }

        //displays contacts
        if(events.get(i).getAttendees()!=null && events.get(i).getAttendees().size()!=0){
            String output="List of contacts: ";
            for(String attendees: events.get(i).getAttendees()){
                output+=attendees +", ";
            }
            eventListViewHolder.contacts.setText(output);

        } else{
            eventListViewHolder.contacts.setText("Contacts: none");
        }



        eventListViewHolder.eventLayout.setOnClickListener( new EditEventListener(context, events.get(i).getId()));

    }

    public void setSortByDesecnding(boolean sort){
        this.sortByDesecnding=sort;
    }

    @Override
    public int getItemCount() {
        return Singleton.getInstance().getModel().getAllEvents().size();
    }


}
