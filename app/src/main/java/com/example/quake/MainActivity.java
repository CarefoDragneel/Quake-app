package com.example.quake;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        here, Arraylist with custom class in its generics stores an object of the new custom class
//        here we use the static method of the utility class to create an object of the arraylist
        ArrayList<Earthquake_items> earthquake_arraylist = QueryUtils.extractEarthquakeData();

//        accessing the list view from activity_main.xml
        ListView list = (ListView) findViewById(R.id.list);

//        creating an array adapter to enable view recycling
        EarthquakeAdapter adapter = new EarthquakeAdapter(this,earthquake_arraylist);

//        attaching the array adapter to the list view
        list.setAdapter(adapter);
    }
}