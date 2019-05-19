package mad.movieNightPlanner.app.model;

import java.time.LocalDate;
import java.time.LocalTime;


public class EventImp extends AbstractEvent  {


    public EventImp(int id, String title,
                    LocalDate  startDate, LocalTime startTime,
                    LocalDate  endDate, LocalTime endTime,
                    String venue, String location) {
        super(id, title, startDate, startTime, endDate, endTime, venue, location);
    }




}