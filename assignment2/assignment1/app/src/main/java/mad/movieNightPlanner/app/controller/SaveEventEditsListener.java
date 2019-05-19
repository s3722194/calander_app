package mad.movieNightPlanner.app.controller;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app.R;

import java.time.LocalDate;
import java.util.ArrayList;

import mad.movieNightPlanner.app.model.Singleton;

public class SaveEventEditsListener implements View.OnClickListener {
    private static final String TAG = "SaveEventEditsListener";

    private int eventID;
    private Context context;
    private String movieID;
    private ArrayList contacts;
    private String[] startDate;
    private String[] endDate;

    public SaveEventEditsListener(int eventID, Context context, String movieID, ArrayList<String> contacts) {

        this.contacts=contacts;
        this.movieID=movieID;
        this.eventID = eventID;
        this.context = context;
    }


    //saves the current state of the event editor, so when the user clicks save the event can be editor or created
    public void setContacts(ArrayList<String> contacts){
        this.contacts=contacts;
    }

    public void setMovieID(String movieID){
        this.movieID=movieID;
    }

    public void setStartDate(String[] date){
        this.startDate=date;
        Log.i(TAG, "setStartDate: "+date[0]+date[1]+date[2]);
    }

    public void setEndDate(String[] date){this.endDate=date;}

    @Override
    public void onClick(View v) {


        EditText eventName = ((Activity)context).findViewById(R.id.edit_event_title);
        EditText venueName= ((Activity)context).findViewById(R.id.edit_text_venue);
        EditText startTime = ((Activity)context).findViewById(R.id.edit_text_start_time);
        EditText endTime = ((Activity)context).findViewById(R.id.edit_text_end_time);



        boolean canCreate = false;

        //finds the start and end dates and creates a LocalDate, so they can easily be compared
        LocalDate start=LocalDate.of(Integer.parseInt(startDate[2]), Integer.parseInt(startDate[1]), Integer.parseInt(startDate[0]));
        LocalDate end =LocalDate.of(Integer.parseInt(endDate[2]), Integer.parseInt(endDate[1]), Integer.parseInt(endDate[0]));

        //checks that the vaules aren't null
        if( startTime.getText()!=null && endTime.getText()!=null &&
                eventName.getText()!=null && venueName.getText()!=null) {

            //gets the start and ends times adds them to an array
            String[] startTimeArray = startTime.getText().toString().split(":");
            String[] endTimeArray = endTime.getText().toString().split(":");


            String eventNameString = eventName.getText().toString();
            String venueNameString = venueName.getText().toString();

            //checks that the
            if (!(eventNameString.equals("")) && !(venueNameString.equals("")) && startTimeArray.length == 2 &&
                    endTimeArray.length == 2) {

                //checks that the start time is between 0-24 hour time
                if (Integer.parseInt(startTimeArray[0]) >= 0 && Integer.parseInt(startTimeArray[0]) < 24) {

                    //checks that the end time is between 0-24 hour time
                    if (Integer.parseInt(endTimeArray[0]) >= 0 && Integer.parseInt(endTimeArray[0]) < 24) {

                        //checks that the mintues in start time are between 0 and 60
                        if (Integer.parseInt(startTimeArray[1]) >= 0 && Integer.parseInt(startTimeArray[0]) < 60) {

                            //checks that the minutes in end time are between 0 and 60
                            if (Integer.parseInt(endTimeArray[1]) >= 0 && Integer.parseInt(endTimeArray[0]) < 60) {

                                //sees if the start date is less then end date
                                if(start.compareTo(end)<0){
                                    canCreate=true;

                                    // sees if the start and end time are equal
                                } else if(start.compareTo(end)==0){

                                    //if start and end time are equal checks that start time is less then end time
                                    if (Integer.parseInt(startTimeArray[0]) < Integer.parseInt(endTimeArray[0])) {
                                        canCreate = true;

                                    } else if (Integer.parseInt(startTimeArray[0]) == Integer.parseInt(endTimeArray[0])) {

                                        //checks if start time minutes are less end minutes
                                        if (Integer.parseInt(startTimeArray[1]) < Integer.parseInt(endTimeArray[1])) {
                                            canCreate = true;
                                        }

                                    }

                                }
                            }

                        }

                    }
                }
            }

            if (canCreate) {

                //if no event exists, adds event
                if(eventID==-1){

                    int eventID= Singleton.getInstance().getModel().addEvent(eventName.getText().toString(),
                            startDate, startTimeArray, false,
                            endDate, endTimeArray, false,
                            venueName.getText().toString(), "-12393847,98384875");

                    if(movieID!=null){
                        Singleton.getInstance().getModel().addMovieToEvent(eventID, movieID);
                    } else{
                        Singleton.getInstance().getModel().removeMovie(eventID);
                    }


                    Singleton.getInstance().getModel().getEvent(eventID).setAttendees(contacts);


                    // if event exists edits event
                } else{
                    Singleton.getInstance().getModel().editEvent(eventID,eventName.getText().toString(),
                            startDate, startTimeArray, false,
                            endDate, endTimeArray, false,
                            venueName.getText().toString(), movieID, contacts);
                    if(movieID!=null){
                        Singleton.getInstance().getModel().addMovieToEvent(eventID, movieID);
                    } else{
                        Singleton.getInstance().getModel().removeMovie(eventID);
                    }


                    Singleton.getInstance().getModel().getEvent(eventID).setAttendees(contacts);

                }

                ((Activity) context).finish();
            }
        }

        //if event can't be created then a toast pops up to warn the user
        if(!canCreate){

            Toast.makeText(context, "Invalid event", Toast.LENGTH_LONG).show();
        }
        Log.i(TAG, "onClick: " + Singleton.getInstance().getModel().getAllEvents().size());

    }
}
