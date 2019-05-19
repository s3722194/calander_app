package mad.movieNightPlanner.app.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;


public class EventModel implements Serializable {

    private static final String TAG = "EventModel";

    private HashMap<Integer, Event> events = new HashMap<>();
    private ArrayList<Movie> movies = new ArrayList<>();
    private  int nextMovieID=1;
    private int nextEventID=1;


    public EventModel(){

    }

    public void changeAttendeesToEvent(int id, ArrayList<String> attendees){
        events.get(id).setAttendees(attendees);

    }


    public int addEvent(String title, String[] startDateArray, String[] startTimeArray, boolean isStartPM,
                         String[] endDateArray, String[] endTimeArray, boolean isEndPM,
                         String venue, String location ){
        LocalDate startDate = strDateToLocalDate(startDateArray);
        LocalDate endDate = strDateToLocalDate(endDateArray);
        LocalTime startTime = strDateToLocalTime(startTimeArray, isStartPM);
        LocalTime endTime = strDateToLocalTime(endTimeArray, isEndPM);

        boolean validDate=false;

        //checks to see if date is valid
        if(startDate.compareTo(endDate)==0){
            if(startTime.compareTo(endTime)<0){
                validDate=true;
            }

        } else if (startDate.compareTo(endDate)<0){
            validDate=true;

        }


        if(validDate){

            //adds event
            events.put(nextEventID, new EventImp( nextEventID,title, startDate, startTime,
                    endDate, endTime,venue, location));
            nextEventID++;
            return nextEventID-1;


        } else{
            //if can't add event returns -1
            return -1;
        }

    }

    public Movie getMovie(String id){
        Movie movie=null;
        for(Movie each: movies){
            if(each.getID().equals(id)){
                movie=each;
                break;
            }
        }
        return movie;
    }

    //converts dateArray to a localDate
    public LocalDate strDateToLocalDate(String[] dateArray){
        LocalDate date;
        date = LocalDate.of(Integer.parseInt(dateArray[2]) ,
                Integer.parseInt(dateArray[1]),
                Integer.parseInt(dateArray[0]));
        return date;

    }

    public LocalTime strDateToLocalTime(String[] timeArray, boolean isPM) {

        //converts timeArray to a localTime
        LocalTime time=null;


        if(timeArray.length==2){
            if (isPM){
                if(Integer.parseInt(timeArray[0])!=12){
                    time = LocalTime.of(Integer.parseInt(timeArray[0])+12,
                            Integer.parseInt(timeArray[1]));

                }else{
                    time = LocalTime.of(Integer.parseInt(timeArray[0]) ,
                            Integer.parseInt(timeArray[1]));

                }

            } else {
                time = LocalTime.of(Integer.parseInt(timeArray[0]),
                        Integer.parseInt(timeArray[1]));
            }

        } else if (timeArray.length==3){
            if (isPM) {

                if(Integer.parseInt(timeArray[0])!=12){
                    time = LocalTime.of(Integer.parseInt(timeArray[0])+12,
                            Integer.parseInt(timeArray[1]),
                            Integer.parseInt(timeArray[2]));
                } else{
                    time = LocalTime.of(Integer.parseInt(timeArray[0]) ,
                            Integer.parseInt(timeArray[1]),
                            Integer.parseInt(timeArray[2]));

                }

            } else {
                time = LocalTime.of(Integer.parseInt(timeArray[0]),
                        Integer.parseInt(timeArray[1]),
                        Integer.parseInt(timeArray[2]));
            }

        }


        return time;
    }


    public ArrayList<Event> getAllEvents(){

        return new ArrayList<>(events.values());
    }



    public void addMovie(Movie movie){
        movies.add(movie);
        nextMovieID++;
    }

    public void addMovieToEvent(int id, String movieID){
        if(events.containsKey(id)){
            for(Movie each: movies){
                if(each.getID().equals(movieID)){

                    events.get(id).setMovie(each);
                    break;

                }
            }

        }
    }

    public ArrayList<Movie> getAllMovies(){
        return movies;
    }

    public Event getEvent(int id){
        return events.get(id);
    }

    public void addEvent(Event event){
        events.put(event.getId(), event);
        nextEventID++;
    }

    public void editEvent(int id, String title, String[] startDateArray, String[] startTimeArray, boolean isStartPM,
                           String[] endDateArray, String[] endTimeArray, boolean isEndPM,
                           String venue, String movieID, ArrayList<String> contacts){

        LocalDate startDate = strDateToLocalDate(startDateArray);
        LocalDate endDate = strDateToLocalDate(endDateArray);
        LocalTime startTime = strDateToLocalTime(startTimeArray, isStartPM);
        LocalTime endTime = strDateToLocalTime(endTimeArray, isEndPM);

        if(events.containsKey(id)){
            if(title!=null){
                events.get(id).setTitle(title);
            }

            if(startDate!=null){
                events.get(id).setStartDate(startDate);
            }

            if(endDate!=null){
                events.get(id).setEndDate(endDate);
            }

            if(startTime!=null){
                events.get(id).setStartTime(startTime);
            }
            if(endTime!=null){
                events.get(id).setEndTime(endTime);
            }

            if(venue!=null){
                events.get(id).setVenue(venue);
            }
            if(movieID!=null){
                Movie movie=getMovie(movieID);
                events.get(id).setMovie(movie);
            }
            if(contacts.size()!=0){
                events.get(id).setAttendees(contacts);
            }

        }

    }

    public  void removeMovie(int eventID) {
        events.get(eventID).removeMovie();
    }

    public void removeEvent(int eventID){
        events.remove(eventID);
    }


}
