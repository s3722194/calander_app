package mad.movieNightPlanner.app.controller;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;

import java.time.LocalDate;

import mad.movieNightPlanner.app.model.Event;
import mad.movieNightPlanner.app.model.Singleton;


public class DatePickerListener implements View.OnClickListener {

    private static final String TAG = "DatePickerListener";

    private Context context;
    private DatePickerDialog.OnDateSetListener listener;
    private int eventID;

    public DatePickerListener(Context context, DatePickerDialog.OnDateSetListener listener, int eventID) {
        this.context = context;
        this.listener = listener;
        this.eventID = eventID;
    }

    @Override
    public void onClick(View v) {
        DatePickerDialog startDatePicker=null;
        Log.i(TAG, "onClick: eventID" +eventID);
        if(eventID==-1){
            LocalDate current = LocalDate.now();
            startDatePicker = new DatePickerDialog(context,
                    listener,
                    current.getYear(),
                    current.getMonthValue()-1,
                    current.getDayOfMonth());

        } else{
            if(Singleton.getInstance().getModel().getEvent(eventID)!=null){
                Event event = Singleton.getInstance().getModel().getEvent(eventID);
                startDatePicker = new DatePickerDialog(context,
                        listener,
                        event.getEndDate().getYear(),
                        event.getEndDate().getMonthValue()-1,
                        event.getEndDate().getDayOfMonth());
            } else{
                Log.i(TAG, "onClick:  eventID doesn't exist");
            }

        }
        if(startDatePicker!=null){
            startDatePicker.show();
        }


    }
}
