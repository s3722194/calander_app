package mad.movieNightPlanner.app.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public abstract class AbstractEvent implements Event, Serializable {
    private int id;
    private String title;
    private LocalDate  startDate;
    private LocalTime startTime;
    private LocalDate  endDate;
    private LocalTime endTime;
    private String venue;
    private String location;
    private List<String> attendees;
    private Movie movie;


    public AbstractEvent(int id, String title,
                    LocalDate  startDate, LocalTime startTime,
                    LocalDate  endDate,LocalTime endTime,
                    String venue, String location) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.venue = venue;
        this.location = location;
        this.id = id;
    }

    public int getId(){return id;}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getLocation() {
        return location;
    }


    public void setAttendees(List<String> attendees) {
        this.attendees = attendees;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public List<String> getAttendees() {
        return attendees;
    }

    public void removeMovie(){
        movie=null;
    }

    public void removeAttendee(String attendee){
        for(int i=0; i<attendees.size();i++){
            if(attendees.get(i).equals(attendee)){
                attendees.remove(i);
                break;
            }
        }
    }
}
