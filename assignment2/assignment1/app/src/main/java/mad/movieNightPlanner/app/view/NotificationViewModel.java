package mad.movieNightPlanner.app.view;

import java.util.ArrayList;

public class NotificationViewModel {

    private ArrayList<Integer> dismissed= new ArrayList<>();
    private ArrayList<Integer> remindAgain = new ArrayList<>();

    public void setDismissed(ArrayList<Integer> dismissed) {
        this.dismissed = dismissed;
    }

    public ArrayList<Integer> getRemindAgain() {
        return remindAgain;
    }

    public void setRemindAgain(ArrayList<Integer> remindAgain) {
        this.remindAgain = remindAgain;
    }


    private static final NotificationViewModel holder = new NotificationViewModel();
    public static NotificationViewModel getInstance() {return holder;}
}
