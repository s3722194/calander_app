package mad.movieNightPlanner.app.model;

import java.util.Comparator;

//used to sort the events by date and time

public class SortByDateAndTime implements Comparator<Event> {


    @Override
    public int compare(Event a, Event b) {

        int value= a.getStartDate().compareTo(b.getEndDate());

        if(value==0){
            value=a.getStartTime().compareTo(b.getStartTime());
        }

        return value;
    }
}
