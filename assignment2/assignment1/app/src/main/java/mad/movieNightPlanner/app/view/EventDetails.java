package mad.movieNightPlanner.app.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.app.R;

import java.time.LocalDate;
import java.util.ArrayList;

import mad.movieNightPlanner.app.controller.ContactPickerListener;
import mad.movieNightPlanner.app.controller.DatePickerListener;
import mad.movieNightPlanner.app.controller.ListOfMoviesListener;
import mad.movieNightPlanner.app.controller.RemoveContactListener;
import mad.movieNightPlanner.app.controller.RemoveEventListener;
import mad.movieNightPlanner.app.controller.SaveEventEditsListener;
import mad.movieNightPlanner.app.model.Event;
import mad.movieNightPlanner.app.model.Singleton;

public class EventDetails extends AppCompatActivity {
    private static final String TAG = "EventDetails";

    //event that is loaded into the event details
    private int eventID;

    //
    private ArrayList<String> contacts= new ArrayList<>();
    private SaveEventEditsListener saveEvent;


    private EditText eventName;
    private EditText venueName;
    private EditText startTime;
    private EditText endTime;


    private TextView contactsText;
    private TextView movieName;
    private TextView startDateText;
    private TextView endDateText;


    // constants for activity for result
    public final int PICK_CONTACT = 20;
    public final int ADD_MOVIE =15;
    private final int REMOVE_CONTACT =10;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);

        //checks if there is a preloaded event to be load
        Intent i=getIntent();
        eventID=i.getIntExtra("eventID",-1);

        initialisedTexts();
        initialisedButton();



        //if an event needs to be load into 
        if((Singleton.getInstance().getModel().getEvent(eventID))!=null){
            existingEvent();
        } else{
            initalDates();
        }


    }

    private void initialiseStartDateButton(){

        Button startDateButton = findViewById(R.id.add_start_date);

        final DatePickerDialog.OnDateSetListener startDatePickerListener= new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                Log.i(TAG, "onDateSet: start month: "+monthOfYear);
                String date[]={String.valueOf(dayOfMonth), String.valueOf(monthOfYear+1), String.valueOf(year)};
                saveEvent.setStartDate(date);
                startDateText.setText("Start Date: "+date[0]+"/"+date[1]+"/"+date[2]);
            }

        };


        startDateButton.setOnClickListener(new DatePickerListener(this, startDatePickerListener, eventID));
    }

    public void initialiseEndDateButton(){
        final Button endDateButton = findViewById(R.id.add_end_date);

        final DatePickerDialog.OnDateSetListener endDatePickerListener= new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                Log.i(TAG, "onDateSet: end month: "+monthOfYear);
                String date[]={String.valueOf(dayOfMonth),
                        String.valueOf(monthOfYear+1),
                        String.valueOf(year)};
                saveEvent.setEndDate(date);
                endDateText.setText("End Date: "+date[0]+"/"+date[1]+"/"+date[2]);
            }

        };

        endDateButton.setOnClickListener(new DatePickerListener(this, endDatePickerListener, eventID));

    }


    private void initialisedButton(){
        changeEventButtons();
        contactButtons();
        movieButton();
        initialiseStartDateButton();
        initialiseEndDateButton();

    }

    private void initialisedTexts(){
        eventName = findViewById(R.id.edit_event_title);
        venueName= findViewById(R.id.edit_text_venue);
        startTime = findViewById(R.id.edit_text_start_time);
        endTime = findViewById(R.id.edit_text_end_time);
        contactsText=findViewById(R.id.list_contacts);
        movieName=findViewById(R.id.list_of_movies_in_event);
        startDateText =findViewById(R.id.date_of_start_date);
        endDateText =findViewById(R.id.date_of_end_date);

    }

    private void changeEventButtons(){

        Button removeEvent =findViewById(R.id.remove_event);
        removeEvent.setOnClickListener(new RemoveEventListener(this, eventID));

        Button saveDetails = findViewById(R.id.event_done_button);
        saveEvent= new SaveEventEditsListener( eventID, this, "", contacts);
        saveDetails.setOnClickListener(saveEvent);

    }

    private void contactButtons(){
        Button removeContact =findViewById(R.id.remove_contact_button);
        removeContact.setOnClickListener(new RemoveContactListener(this));

        Button add_contact = findViewById(R.id.add_contacts_button);
        add_contact.setOnClickListener( new ContactPickerListener(this));

    }

    private void movieButton(){

        Button addMovie = findViewById(R.id.add_movie_button);
        addMovie.setOnClickListener(new ListOfMoviesListener(this));

        Button removeMovie =findViewById((R.id.delete_movie_button));

        //checks take of movie being removed
        removeMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEvent.setMovieID(null);
                movieName.setText("Movie: None");

            }
        });

    }

    private void initalDates(){
        LocalDate current=LocalDate.now();
        String[] currentDate = {String.valueOf(current.getDayOfMonth()),
                String.valueOf(current.getMonthValue()),
                String.valueOf(current.getYear())};

        //saves the inital date of the start date
        saveEvent.setStartDate(currentDate);

        //saves the inital state of the end date
        saveEvent.setEndDate(currentDate);
    }


    private void existingEvent(){
        //set preloaded text view values
        Event event = Singleton.getInstance().getModel().getEvent(eventID);
        eventName.setText(event.getTitle());
        venueName.setText(event.getVenue());
        startTime.setText(event.getStartTime().toString());
        endTime.setText(event.getEndTime().toString());

        //set movie
        if(event.getMovie()!=null){
            movieName.setText("Movie: "+event.getMovie().getTitle());
            saveEvent.setMovieID(event.getMovie().getID());
        }

        // set attendees
        if (event.getAttendees()!=null){
            String output="List of Contacts: ";
            for(String each: event.getAttendees()){
                output+=each+", ";
            }
            contactsText.setText(output);
            saveEvent.setContacts(new ArrayList<>(event.getAttendees()));
        }

        String [] endDate ={String.valueOf(event.getEndDate().getDayOfMonth()),
                String.valueOf(event.getEndDate().getMonthValue()),
                String.valueOf(event.getEndDate().getYear())};

        String [] startDate ={String.valueOf(event.getStartDate().getDayOfMonth()),
                String.valueOf(event.getStartDate().getMonthValue()),
                String.valueOf(event.getStartDate().getYear())};

        saveEvent.setStartDate(startDate);
        saveEvent.setEndDate(endDate);

        startDateText.setText("Start Date: "+startDate[0]+"/"+startDate[1]+"/"+startDate[2]);
        endDateText.setText("End Date: "+endDate[0]+"/"+endDate[1]+"/"+endDate[2]);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //adds contacts
        if (requestCode == PICK_CONTACT && resultCode == RESULT_OK) {
           addContact(data);

        //adds a movie
        } else if (requestCode==ADD_MOVIE && resultCode==RESULT_OK){
            addMovie(data);

            //removes contact
        } else if(requestCode==REMOVE_CONTACT && resultCode==RESULT_OK){
            removeContact(data);


        }
    }

    private void addContact(Intent data){
        Uri contactUri = data.getData();
        Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
        cursor.moveToFirst();

        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

        Log.d("name of contact",  name);

        boolean isAdded=false;
        for(String each: contacts){
            if(each.equals(name)){
                isAdded=true;
            }
        }
        //only adds contacts that haven't been added before
        if(!isAdded){
            contacts.add(name);

            String contactOutput ="List of Contacts: ";
            for(String each: contacts){
                if(each!=null){
                    contactOutput+=each +", ";
                }
            }
            contactsText.setText(contactOutput);

            //saves current state of the contacts
            saveEvent.setContacts(contacts);


            Log.i(TAG, "onActivityResult: add contact");
        }


    }

    private void addMovie(Intent data){

        String movieID = data.getStringExtra("movieID");

        //saves the current state of the movie
        saveEvent.setMovieID(movieID);
        movieName.setText("Movie: "+ Singleton.getInstance().getModel().getMovie(movieID).getTitle());
        Log.i(TAG, "onActivityResult: add movie id: " + movieID);

    }

    private void removeContact(Intent data){
        Uri contactUri = data.getData();
        Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
        cursor.moveToFirst();

        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

        //only removes a contact, if it's in the array list
        for(int i=0; i<contacts.size(); i++){
            if(contacts.get(i).equals(name)){
                contacts.remove(i);
                break;
            }
        }

        //saves the current state of the contacts
        saveEvent.setContacts(contacts);

        String contactOutput ="List of Contacts: ";
        for(String each: contacts){
            if(each!=null){
                contactOutput+=each +", ";
            }
        }

        contactsText.setText(contactOutput);

        Log.i(TAG, "onActivityResult: remove contact" );


    }



}
