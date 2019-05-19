package mad.movieNightPlanner.app.model;

import java.io.Serializable;

public abstract class AbstractMovie implements Movie, Serializable {

    private String id;
    private String title;
    private String year;
    private String poster;

    public AbstractMovie(String id, String title, String year, String poster) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.poster = poster;
    }

    public String getTitle(){
        return title;
    }

    public String getYear(){
        return year;
    }

    public String getPoster(){
        return poster;
    }

    public String getID() {return id;}
}
