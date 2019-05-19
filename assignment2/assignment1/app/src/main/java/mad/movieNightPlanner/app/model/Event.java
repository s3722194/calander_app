package mad.movieNightPlanner.app.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface Event  {

    int getId();

    String getTitle();

    void setTitle(String title);

    LocalDate getStartDate();

    void setStartDate(LocalDate startDate);

    LocalTime getStartTime();

    void setStartTime(LocalTime startTime);

    LocalDate getEndDate();

    void setEndDate(LocalDate endDate);

    LocalTime getEndTime();

    void setEndTime(LocalTime endTime);

    String getVenue();

    void setVenue(String venue);

    String getLocation();

    void removeMovie();

    void removeAttendee(String attendee);


    void setAttendees(List<String> attendees);

    Movie getMovie();

    void setMovie(Movie movie);

    List<String> getAttendees();
}
