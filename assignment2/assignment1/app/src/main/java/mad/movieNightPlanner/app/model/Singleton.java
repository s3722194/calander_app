package mad.movieNightPlanner.app.model;

public class Singleton {

    private EventModel model= new EventModel();
    public EventModel getModel() {return model;}
    public void setModel(EventModel data) {this.model = data;}

    private static final Singleton holder = new Singleton();
    public static Singleton getInstance() {return holder;}
}
