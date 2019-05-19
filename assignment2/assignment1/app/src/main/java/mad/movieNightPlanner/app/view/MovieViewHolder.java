package mad.movieNightPlanner.app.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.app.R;

public  class MovieViewHolder extends RecyclerView.ViewHolder{

    TextView movieName;
    ImageView imageView;
    TextView movieYear;
    RelativeLayout movieLayout;
    public MovieViewHolder(View itemView) {
        super(itemView);
        movieName =itemView.findViewById(R.id.movie_name);
        imageView =itemView.findViewById(R.id.movie_poster);
        movieYear =itemView.findViewById(R.id.movie_year);
        movieLayout=itemView.findViewById(R.id.movie_layout);
    }
}
