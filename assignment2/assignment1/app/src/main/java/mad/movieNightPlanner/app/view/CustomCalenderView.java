package mad.movieNightPlanner.app.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.app.R;

import java.time.LocalDate;
import java.util.ArrayList;

import mad.movieNightPlanner.app.model.Event;
import mad.movieNightPlanner.app.model.EventModel;

public class CustomCalenderView extends AppCompatActivity {



    private CalanderAdapter mAdapter;
    private TextView monthName;
    private int currentMonth;
    private String[] months ={"January", "February ", "March", "April", "May", "" +
            "June", "July", "August", "September", "October", "November", "December"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calander_view);
        RecyclerView recyclerView =  findViewById(R.id.calander_view);


        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 7);
        recyclerView.setLayoutManager(layoutManager);

        monthName=findViewById(R.id.cal_month_name);

        LocalDate date =LocalDate.now();
        currentMonth=date.getMonthValue()-1;
        mAdapter = new CalanderAdapter( this, date.getMonthValue(), date.getYear());
        recyclerView.setAdapter(mAdapter);

        monthName.setText(months[currentMonth]);


        //cheks to see when the previous button is pressed, so the view can update
        Button previous = findViewById(R.id.previous_month);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMonth= mAdapter.decreaseMonth()-1;
                mAdapter.notifyDataSetChanged();
                monthName.setText(months[currentMonth]);
            }
        });

        //checks to see when the next button is pressed so the view can update
        Button next =findViewById(R.id.next_month);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMonth=mAdapter.increaseMonth()-1;
                mAdapter.notifyDataSetChanged();
                monthName.setText(months[currentMonth]);
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();

        mAdapter.notifyDataSetChanged();
    }




}


