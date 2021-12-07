package com.example.quake;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.nio.channels.AsynchronousChannelGroup;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

//        below is the URL to which we send the request and get the info
    private static final String RESOURCE_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=20";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        here we create an object of Earthquake_AsyncTask class which is used to receive data from the internet
//        in a different thread
        Earthquake_AsyncTask task = new Earthquake_AsyncTask();
//        execute method is used to command the start of the http request
        task.execute(RESOURCE_URL);
    }


//    this method is used to make changes in the UI
    private void updateUI(ArrayList<Earthquake_items> arrayList){

//        accessing the list view from activity_main.xml
        ListView list = (ListView) findViewById(R.id.list);

//        creating an array adapter to enable view recycling
        EarthquakeAdapter adapter = new EarthquakeAdapter(this, arrayList);

//        attaching the array adapter to the list view
        list.setAdapter(adapter);
    }


//    here we create an inner class which will help us in implementing multi-threading in the app
    private class Earthquake_AsyncTask extends AsyncTask<String, Void,ArrayList<Earthquake_items>>{

//        we override the doInBackground method which will be implemented in the background thread
        @Override
        protected ArrayList<Earthquake_items> doInBackground(String... urls) {

//            this is to make our code robust
//            here, if there is no value in URL then this block of code will take care of it
            if( urls.length<1 || urls[0]==null)
                return null;

//            here we implement the fetchEarthquakeData method in the background which sends the http request
//            and extracts as well as parses the json data
            ArrayList<Earthquake_items> arraylist = Utils.fetchEarthquakeData(urls[0]);
            return arraylist;
        }

//        we override this method to update UI from the extracted json data
//        this method takes in the returned value of doInBackground method as argument
//        it runs in the Main thread
        @Override
        protected void onPostExecute(ArrayList<Earthquake_items> arrayList) {

            if(arrayList==null){
                return;
            }
//            here, we implement the change in UI method defined earlier
            updateUI(arrayList);
        }
    }
}
