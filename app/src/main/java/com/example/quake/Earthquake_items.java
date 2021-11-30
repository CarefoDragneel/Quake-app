package com.example.quake;

// Custom class to store each list items about the earthquake information
public class Earthquake_items {

//    These are instance variables of the this custom class
    private final String earthquake_magnitude;
    private final String earthquake_place;
    private final long earthquake_date;

//    Constructor to initialise the data items and store in the instance variables
    public Earthquake_items(String m, String p,long d){
        earthquake_magnitude = m;
        earthquake_place = p;
        earthquake_date = d;
    }

//    get method to receive earthquake magnitude
    String getEarthquake_magnitude(){
        return earthquake_magnitude;
    }
//    get method to receive earthquake place of occurrence
    String getEarthquake_place(){
        return earthquake_place;
    }
//    get method to receive earthquake place
    long getEarthquake_date(){
        return earthquake_date;
    }

}
