package com.example.quake;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


// This is an custom array adapter in order to recycle views
public class EarthquakeAdapter extends ArrayAdapter<Earthquake_items> {

//    Defined the constructor for custom adapter
/*    using super will take care of all the initialisations in the original ArrayAdapter and we need to define
       only the new stuff here */
    public EarthquakeAdapter(Activity context, ArrayList<Earthquake_items> li){
        super(context,0,li);
    }

    private String place = new String();
    private String distance = new String();


//    We override the getView method as this is the sole reason for making a custom Adapter class
//    Since, default Adapter class has a basic list items view so, we create custom Adapter
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        we create the listView object, convertView will call some view
        View listview = convertView;

//        if the convertView is not present then listView will inflate or use the one we are providing using below statements
        if(listview == null){
            listview = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_items,parent,false);
        }

//        we create an object of the custom class because we want to access the list items present at different positions
//        @param: position -> defines each list items
        Earthquake_items list_items = getItem(position);


//        DecimalFormat class is same as SimpleDateFormat
//        it is used to change the format of decimal to specify how many digits should be present after the decimal point
        DecimalFormat magnitude_format = new DecimalFormat("0.0");
//        here we use format method to convert into desired format and this method returns a String value
        String magnitude = magnitude_format.format(list_items.getEarthquake_magnitude());

//      Here we set the text in each text view present in the new list items layout
        TextView  magnitude_textbox= (TextView) listview.findViewById(R.id.magnitude_layout);
        magnitude_textbox.setText(magnitude);

//        GradientDrawable class is used to modify the Drawable class elements, it extends Drawable class
//        getBackground method returns a Drawable type object, so we need to type cast it
        GradientDrawable magnitude_circle = (GradientDrawable) magnitude_textbox.getBackground();

//        here we obtain the desired color from the defined getMagnitudeColor method
        int desired_magnitude_color = getMagnitudeColor(list_items.getEarthquake_magnitude());

//        here we set the color of the magnitude circle
        magnitude_circle.setColor(desired_magnitude_color);


//      Below code is an algorithm to extract the Earthquake_place string in two parts to create
//        this is done to keep them in two textboxes adhering to the design specs provided in the course
        String str = list_items.getEarthquake_place();

//        In this algo we first find the index  of "of" from the main string
//        then we store the substring starting from the start to index+2 in distance string
        int index = str.indexOf("of");
//        if "of" is not present then we save "Near To" in distance
        if(index==-1) {
            distance = "Near to";
            index=0;
        }
        else{
            distance = str.substring(0,index+2);
            index += 3;
        }

//        rest part is stored in place string
        place = str.substring(index,str.length());

//        Here we store the distance value in its respective TextBox
        TextView distance_textbox = (TextView) listview.findViewById(R.id.distance_layout);
        distance_textbox.setText(distance);

//        Here we store the distance value in its respective TextBox
        TextView place_textbox = (TextView) listview.findViewById(R.id.place_layout);
        place_textbox.setText(place);


//        creating Date object in order use it in SimpleDateFormat class to format data in to desired form
        Date dateObject = new Date(list_items.getEarthquake_date());

//        SimpleDateFormat class is used to create an object which is used to format the JSON time value in milliseconds to date
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");

//        here we set the formatted JSON time to desired date format and store it in the TextView
//        here format method returns a String data
        TextView date_textbox = (TextView) listview.findViewById(R.id.date_layout);
        date_textbox.setText(dateFormat.format(dateObject));

//        SimpleDateFormat class is used to create an object which is used to format the JSON time value in milliseconds to time
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");

//        here we set the formatted JSON time to desired time format and store it in the TextView
//        here format method returns a String data
        TextView time_textbox = (TextView) listview.findViewById(R.id.time_layout);
        time_textbox.setText(timeFormat.format(dateObject));


/*        after defining the contents of each view in the earthquake_list_items.xml file, we return it to set them in the
        layout file */
        return listview;
    }


//    this method is to obtain right color resource for the drawable circle
//    @param: double magnitude -> the magnitude value
//    return the resource id of the color required
    int getMagnitudeColor(double magnitude){

//        this statement is to get the switch changer
//        it uses the floor method to obtain the int rounded value to greatest smaller integer
        int swt = (int)Math.floor(magnitude);
//        this is to store magnitude resource id
        int magnitude_resource_id = 0;
//        these are the switch cases to specify the correct color according to the magnitude 
        switch (swt){
            case 0:
            case 1:
                magnitude_resource_id = R.color.magnitude1;
                break;
            case 2:
                magnitude_resource_id = R.color.magnitude2;
                break;
            case 3:
                magnitude_resource_id = R.color.magnitude3;
                break;
            case 4:
                magnitude_resource_id = R.color.magnitude4;
                break;
            case 5:
                magnitude_resource_id = R.color.magnitude5;
                break;
            case 6:
                magnitude_resource_id = R.color.magnitude6;
                break;
             case 7:
                magnitude_resource_id = R.color.magnitude7;
                 break;
             case 8:
                magnitude_resource_id = R.color.magnitude8;
                 break;
             case 9:
                magnitude_resource_id = R.color.magnitude9;
                 break;
            default:
                magnitude_resource_id = R.color.magnitude10plus;
                break;
        }
//      in return statement we enter the extracted color from its resource id;
        return ContextCompat.getColor(getContext(), magnitude_resource_id);
    }

}
