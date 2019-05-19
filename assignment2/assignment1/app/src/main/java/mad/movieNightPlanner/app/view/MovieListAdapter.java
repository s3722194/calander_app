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

import mad.movieNightPlanner.app.controller.AddMovieListener;
import mad.movieNightPlanner.app.model.Movie;

public class MovieListAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private static final String TAG = MovieListAdapter.class.getName();

    private ArrayList<Movie> movies;
    private Context context;


    public MovieListAdapter(ArrayList<Movie> movieNames, Context context) {
        this.movies = movieNames;
        this.context = context;

    }


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_items, viewGroup, false);
        MovieViewHolder holder = new MovieViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder viewHolder, int i) {
        Log.i(TAG, "onBindViewHolder: called");

        //display movie info
        viewHolder.movieName.setText(movies.get(i).getTitle());
        viewHolder.movieYear.setText(movies.get(i).getYear());

       //displays image
        if(i==1){
            viewHolder.imageView.setImageResource(R.drawable.hackers);
        }

        String movieID = movies.get(i).getID();
        viewHolder.movieLayout.setOnClickListener(new AddMovieListener(context, movieID));

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }



}
