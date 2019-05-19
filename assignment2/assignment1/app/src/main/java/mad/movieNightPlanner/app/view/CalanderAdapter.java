package mad.movieNightPlanner.app.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app.R;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

import mad.movieNightPlanner.app.controller.CalanderEventListener;
import mad.movieNightPlanner.app.model.Event;
import mad.movieNightPlanner.app.model.Singleton;

public class CalanderAdapter extends RecyclerView.Adapter<CalanderViewHolder> {

    private static final String TAG = "CalanderAdapter";



    private int gridCells = 42;

    private LocalDate current = LocalDate.now();
    private LocalDate display = LocalDate.now();
    private int month;
    private int year;
    private Context context;



    public CalanderAdapter(Context context, int month, int year) {

        this.context=context;
        this.month = month;
        this.year = year;


        findDate();


    }

    public int increaseMonth(){
        if(month==12){
            month=1;
            year++;

        } else{
            month++;
        }
        findDate();
        return month;
    }

    public int decreaseMonth(){
        if(month==1){
            year--;
            month=12;
        } else{
            month--;
        }
        findDate();
        return month;
    }

    @NonNull
    @Override
    public CalanderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.calander_cell, viewGroup, false);
        CalanderViewHolder holder = new CalanderViewHolder(view);
        return holder;



    }

    @Override
    public void onBindViewHolder(@NonNull CalanderViewHolder calanderViewHolder, int i) {

        ArrayList<Event> todayEvents= new ArrayList<>();

        //adds events to today events
        if(display.getYear()==year){
            if(display.getMonthValue()==month){
                for(Event event: Singleton.getInstance().getModel().getAllEvents()){
                    if(event.getStartDate().equals(display)){
                        todayEvents.add(event);
                    }
                }
            }

        }


        Log.i(TAG, "onBindViewHolder: called" + i);


        calanderViewHolder.dateNum.setText(String.valueOf(display.getDayOfMonth()));
        String output="";

        //outputs todays events
        for(Event event: todayEvents){
            output+=event.getTitle()+"\n";
        }
        calanderViewHolder.events.setText(output);

            display = display.plusDays(1);


        calanderViewHolder.cellLayout.setOnClickListener(new CalanderEventListener(context, todayEvents));

        if(i==gridCells-1){
            findDate();
        }
    }


    //finds the starting date of the calender month
    public void findDate() {
        display=LocalDate.now();

        if (current.getYear() == year) {


        } else if (year < current.getYear()) {
            int yearDiff = current.getYear() - year;
            display = display.minusYears(yearDiff);

        } else if (year > current.getYear()) {
            int yearDiff = year - current.getYear();
            display = display.minusYears(yearDiff);
        }


        if (current.getMonthValue() == month) {


        } else if (month < current.getMonthValue()) {
            int monthDiff = current.getMonthValue() - month;
            display = display.minusMonths(monthDiff);


        } else if (month > current.getMonthValue()) {
            int monthDiff = month - current.getMonthValue();

            display = display.plusMonths(monthDiff);
        }

        display = display.minusDays((display.getDayOfMonth() - 1));
        DayOfWeek day = DayOfWeek.of(7);
        while (display.getDayOfWeek() != day) {
            display = display.minusDays(1);

        }


    }


    @Override
    public int getItemCount() {
        return gridCells;
    }



}

