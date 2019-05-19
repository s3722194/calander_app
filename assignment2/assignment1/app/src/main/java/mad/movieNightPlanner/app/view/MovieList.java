package mad.movieNightPlanner.app.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.app.R;

import mad.movieNightPlanner.app.model.Singleton;

public class MovieList extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.movie_list);
        RecyclerView recyclerView =  findViewById(R.id.movie_list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter mAdapter = new MovieListAdapter(Singleton.getInstance().getModel().getAllMovies(), this);
        recyclerView.setAdapter(mAdapter);



    }


}
