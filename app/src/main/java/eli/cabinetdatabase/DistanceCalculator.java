package eli.cabinetdatabase;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.LoginFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.location.Location;
import android.location.LocationManager;
import android.location.Geocoder;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.net.URL;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.Scanner;


public class DistanceCalculator extends FragmentActivity {

    private static Location factoryLocation;
    private static TextView driveDistance;

    private static LocationManager locationManager;
    private static final long MIN_DISTANCE_FOR_UPDATE = 10;
    private static final long MIN_TIME_FOR_UPDATE = 1000 * 60 * 2;


    private static String apiKey = "AIzaSyBaTFeAAzbuF9JgBU49SdYygGYFsiUdFwU";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void getLocation(String provider) {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

// Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {}

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

// getting GPS status
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

// check if GPS enabled
        if (isGPSEnabled) {

            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (location != null) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                try {
                    requestAndParseDistance(location);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                if (location != null) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                    try {
                        requestAndParseDistance(location);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    //setText on View
                    driveDistance = (TextView) findViewById(R.id.dist);
                    driveDistance.setText("Current location not available");
                }
            }
        }
    }



    public void calculateDistance(View v) throws Exception {
        getLocation(LocationManager.NETWORK_PROVIDER);
    }

    public void requestAndParseDistance(Location destination) throws Exception {
        String dist;
        //build URL request to distance matrix
        String uri = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="
                + URLEncoder.encode(Double.toString(factoryLocation.getLatitude()),"UTF-8") + ","
                + URLEncoder.encode(Double.toString(factoryLocation.getLongitude()), "UTF-8") +
        "&destinations=" + URLEncoder.encode(Double.toString(destination.getLatitude()), "UTF-8") +
                "," + URLEncoder.encode(Double.toString(destination.getLongitude()), "UTF-8") +
        "&units=imperial&key=" + URLEncoder.encode(apiKey,"UTF-8");
        URL url = new URL(uri);

        //Start Async Task to get JSON file from Distance Matrix
        new DistanceMatrixTask().execute(url);


    }

    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
            }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_distance, container, false);
            factoryLocation = new Location("gps");
            factoryLocation.setLatitude(35.791784);
            factoryLocation.setLongitude(-80.926537);

            return rootView;
        }
    }

    //OpenStream and Scan reply from distance matrix *NEED TO DO THIS IN ASYNC TASK OR THREAD*
    public class DistanceMatrixTask extends AsyncTask<URL, Integer, String> {
        @Override
        protected String doInBackground(URL... url){
            String requestData="";

            //openStream() with Distance Matrix URL and Scan into String
            final InputStream in;
            try {
                in = url[0].openStream();
                Scanner scan;
                scan = new Scanner(in);
                while (scan.hasNext()) {
                    requestData += scan.nextLine();
                }
                scan.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return requestData;
        }

        protected void onProgressUpdate(Integer... progress) {
            setProgress(progress[0]);
        }

        protected void onPostExecute(String requestData){
            //parse JSON from Distance Matrix
            String distString="";
            JSONArray rowsArray = null;
            double distanceInMiles=-1;
            try {
                // Getting Array of Distance Matrix Results
                JSONObject json = new JSONObject(requestData);
                rowsArray = json.getJSONArray("rows");
                JSONObject rowsObject = rowsArray.getJSONObject(0);//only one element in this array
                JSONArray elementsArray = rowsObject.getJSONArray("elements");
                JSONObject elementsObject = elementsArray.getJSONObject(0);//only one element in this array
                JSONObject distanceObject = elementsObject.getJSONObject("distance");
                distanceInMiles = (distanceObject.getDouble("value"))/1609.344; //distance in meters converted to miles

                }catch (JSONException e) {
                    e.printStackTrace();
                }

            //Display Driving Distance on View
            distString = Double.toString(distanceInMiles);
            int indexOfDecimal = distString.indexOf('.');
            distString = distString.substring(0, indexOfDecimal);

            //setText on View
            driveDistance = (TextView) findViewById(R.id.dist);
            driveDistance.setText(distString +" Miles");
        }

    };


}
