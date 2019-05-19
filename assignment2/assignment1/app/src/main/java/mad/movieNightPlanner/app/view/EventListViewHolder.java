package mad.movieNightPlanner.app.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.app.R;

public class EventListViewHolder extends RecyclerView.ViewHolder {


    TextView eventName;
    TextView startTime;
    TextView endTime;
    TextView date;
    TextView venue;
    TextView contacts;
    TextView movies;

    RelativeLayout eventLayout;
    public EventListViewHolder(View itemView) {
        super(itemView);

        eventName =itemView.findViewById(R.id.event_name);
        startTime =itemView.findViewById(R.id.start_time);
        endTime =itemView.findViewById(R.id.end_time);
        date =itemView.findViewById(R.id.date_label);
        venue =itemView.findViewById(R.id.venue_list_label);
        contacts=itemView.findViewById(R.id.contacts_event_list);
        movies=itemView.findViewById(R.id.movie_event_list);


        eventLayout=itemView.findViewById(R.id.event_list_layout);
    }
}
