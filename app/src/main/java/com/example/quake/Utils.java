package com.example.quake;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class Utils {

/*    we know that .class is used to make an object of Class class which in turn is used to access elements of
    the particular class */
//    below is used to obtain the Simple name of Utils class
    private static final String LOG_TAG = Utils.class.getSimpleName();

//     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
//     * Since this is a utility class so it contains static methods and variables which can be accessed
//     * without creating objects, thus private constructor
    private Utils() {}

//    This method is to define URL, initiate InputStream and JSON parsing
//    This is the method which will be called by the Earthquake_AsyncTask inner class
    public static ArrayList<Earthquake_items> fetchEarthquakeData (String request_URL){

//        URL is a predefined class which converts String data into a URL query
        URL url = createURL(request_URL);

        String jsonResponse = null;
        try {
            jsonResponse = makeHTTPRequest(url);
        }catch(IOException e){
            Log.e(LOG_TAG,"Error closing the input stream",e);
        }

        ArrayList<Earthquake_items> arraylist = extractFeatureFromJSON(jsonResponse);

        return arraylist;
    }

//    This method is used to convert the String into url
    private static URL createURL(String StringURL){

//        Below we create url using URL constructor
//        It can return an exception if the URL cannot be formed
//        NOTE:- a variable when created in try block has its scope in the try block only
        URL url = null;
        try {
            url = new URL(StringURL);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG,"Error in creating the query URL",e);
        }
        return url;
    }


//    This method is used to make a HTTP request to the URL
//    it returns the JSON data which is extracted from the Input Stream
    private static String makeHTTPRequest(URL url) throws IOException {
//        this variable stores the value of the json data from input stream
        String jsonResponse = "";

//        We are making the code robust by taking all possible scenarios in consideration
//        here we cover the case if URL is null
        if (url == null)
            return jsonResponse;

//        this variable is used to create the connection and send http request to url
        HttpURLConnection urlConnection = null;

//        this variable is used to store the input stream which is parsed to get the json data
        InputStream inputStream = null;

//
        try{
//            using this we on the connection from the url
            urlConnection = (HttpURLConnection) url.openConnection();
//            using this we specify the read time out after which it will stop reading
            urlConnection.setReadTimeout(10000);
//            this specifies the request method of HTTP request
            urlConnection.setRequestMethod("GET");
//            using this we specify the connection timeout
            urlConnection.setConnectTimeout(15000);
//            using this we on the connection
            urlConnection.connect();

//            using this block we check if the connection is established in order to get the input stream
            if(urlConnection.getResponseCode()==200){
//                using this method we get the input stream
                inputStream = urlConnection.getInputStream();
//                using this method we get the json value from the input stream
                jsonResponse = readInputStream(inputStream);
            }
            else {
//                here, we print a log statement which uses below method to receive the response code
                Log.e(LOG_TAG,"Error response code"+urlConnection.getResponseCode());
            }
        }catch (IOException e){
            Log.e(LOG_TAG,"Problem in retrieving the JSON data",e);
        }finally {
//            here we close the streams and disconnect the connection so that our code is clean
            if (urlConnection != null) urlConnection.disconnect();
            if(inputStream != null) inputStream.close();
        }

//        here, we return the json data extracted from the input stream
        return jsonResponse;
    }


//    this method is used to extract json data from the input stream
//    it is called in makeHTTPRequest method
    private static String readInputStream(InputStream inputStream) throws IOException{

//        we use a string builder instead of simple string because we are creating a string small part at a time
        StringBuilder output = new StringBuilder();

        if (inputStream != null){
//            InputStreamReader class is used to read the binary input stream and extract readable content in UTF-8 specified form
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));

//            to make above process quicker, we switch to Buffer Reader
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

//            reads a line in the buffered reader
            String line = bufferedReader.readLine();

            while(line != null){
                output.append(line);
                line = bufferedReader.readLine();
            }
        }

//        toString is the method of the Object class which are inherited by all classes implicitly
//        used to convert object in to its underlying String
        return output.toString();
    }


    private static ArrayList<Earthquake_items> extractFeatureFromJSON(String earthquakeJson){

//        we create an arraylist which will be returned and store the parsed values from JSON
        ArrayList<Earthquake_items> arrayList = new ArrayList<>();

//        TextUtils is a utility class which provides various methods to perform tasks on text based String
//        Here, we check if json String is provided or not
        if(TextUtils.isEmpty(earthquakeJson))
            return null;

        try {

//            creating an object of the entire JSON file
            JSONObject root = new JSONObject(earthquakeJson);

//        creating an object to access the values in features key
            JSONArray featuresArray = root.getJSONArray("features");

            for(int i=0;i<featuresArray.length();i++){

//                extract current object at a particular reference
                JSONObject currentObject = featuresArray.getJSONObject(i);
//                we extract properties from the currentObject
                JSONObject properties = currentObject.getJSONObject("properties");
//                Similarly extracted all necessary values
                double magnitude = properties.getDouble("mag");
                String place = properties.getString("place");
                long day = properties.getLong("time");
//                stored all the extracted data in an arraylist
                arrayList.add(new Earthquake_items(magnitude,place,day));
            }
        } catch (JSONException e){
            Log.e(LOG_TAG,"Problem in parsing the JSON data",e);
        }

//        here we return the arraylist
        return arrayList;
    }

}
