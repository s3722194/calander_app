package mad.movieNightPlanner.app.model;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.time.LocalDate;
import java.time.LocalTime;

public class FileLoader {


    private AssetManager assetManager;
    private String eventFile;
    private String moveFile;

    public FileLoader( AssetManager assetManager, String eventFile, String movieFile) throws IOException {
        this.assetManager=assetManager;
        this.eventFile=eventFile;
        this.moveFile=movieFile;

        addEventsFromFile();
        addMoviesFromFile();
    }

    //reads event for file
    private void addEventsFromFile() throws IOException {

        Reader r;


            InputStream inputStream = assetManager.open(eventFile);



            r = new BufferedReader(new InputStreamReader(inputStream));


            StreamTokenizer eventStream = new StreamTokenizer(r);

            eventStream.commentChar('/');

            int t;
            int count=0;
            String id = null;
            String title = null;
            String strStartDate;
            LocalDate startDate = null;
            LocalTime startTime = null;
            LocalTime endTime = null;
            String strEndDate;
            LocalDate  endDate = null;
            String venue = null;
            String location = null;


            while((t=eventStream.nextToken()) != StreamTokenizer.TT_EOF){

                if (eventStream.sval !=null){
                    count++;

                    if(count==1){
                        id=eventStream.sval;
                    }
                    else if(count==2){
                        title=eventStream.sval;

                    } else if (count==3){

                        strStartDate=eventStream.sval;

                        String[] startArray =strStartDate.split(" ");
                        String[] dateArray =startArray[0].split("/");
                        String[] timeArray =startArray[1].split(":");

                        boolean isPM=false;
                        if(startArray[2].equalsIgnoreCase("PM")){
                            isPM=true;
                        }

                        startTime =Singleton.getInstance().getModel().strDateToLocalTime(timeArray,isPM);
                        startDate= Singleton.getInstance().getModel().strDateToLocalDate(dateArray);

                    } else if(count==4){
                        strEndDate=eventStream.sval;

                        String[] endArray =strEndDate.split(" ");
                        String[] dateArray =endArray[0].split("/");
                        String[] timeArray =endArray[1].split(":");

                        boolean isPM=false;
                        if(endArray[2].equalsIgnoreCase("PM")){
                            isPM=true;
                        }

                        endTime =Singleton.getInstance().getModel().strDateToLocalTime(timeArray, isPM);
                        endDate= Singleton.getInstance().getModel().strDateToLocalDate(dateArray);

                    } else if(count ==5){
                        venue=eventStream.sval;
                    } else if(count ==6) {
                        location=eventStream.sval;
                    }
                }
                if(count ==6){
                    Singleton.getInstance().getModel().addEvent(new EventImp(Integer.parseInt(id), title,
                            startDate, startTime,
                            endDate, endTime, venue, location));

                    count =0;

                }


            }




    }


    //reads movie for file
    private void addMoviesFromFile() throws IOException {
        Reader r;

        InputStream inputStream = assetManager.open(moveFile);



        r = new BufferedReader(new InputStreamReader(inputStream));
        StreamTokenizer eventStream = new StreamTokenizer(r);
        eventStream.commentChar('/');
        int t;
        int count=0;
        String id = null;
        String title = null;
        String year = null;
        String poster = null;
        while((t=eventStream.nextToken()) != StreamTokenizer.TT_EOF){

            if (eventStream.sval !=null){
                count++;
                if(count==1){
                    id=eventStream.sval;
                } else if(count==2){
                    title=eventStream.sval;
                } else if (count==3){
                    year=eventStream.sval;
                } else if(count==4){
                    poster=eventStream.sval;
                }
                if(count ==4){
                    Singleton.getInstance().getModel().addMovie(new MovieImpl(id, title, year, poster ));


                    count =0;
                }
            }
        }


    }
}
