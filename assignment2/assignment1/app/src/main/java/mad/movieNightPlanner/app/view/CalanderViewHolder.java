package mad.movieNightPlanner.app.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.app.R;

public class CalanderViewHolder extends RecyclerView.ViewHolder {

    TextView dateNum;
    TextView events;

    LinearLayout cellLayout;
    public CalanderViewHolder(View itemView) {
        super(itemView);

        events =itemView.findViewById(R.id.cell_events);
        dateNum =itemView.findViewById(R.id.cell_number);

        cellLayout=itemView.findViewById(R.id.cell_layout);
    }

}
