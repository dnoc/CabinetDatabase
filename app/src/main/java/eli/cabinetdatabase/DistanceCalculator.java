package eli.cabinetdatabase;


import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;


public class DistanceCalculator extends FragmentActivity{

    private static Location factoryLocation;
    private static LocationManager locationFinder;
    private static TextView distance;
    private static String apiKey = "AIzaSyD4bAs8KvHQiT_tF6GQIDBycm-g8CdNin4";

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

    public Location getLocation(){

        LocationListener loc_listener = new LocationListener() {

            public void onLocationChanged(Location l) {}

            public void onProviderEnabled(String p) {}

            public void onProviderDisabled(String p) {}

            public void onStatusChanged(String p, int status, Bundle extras) {}
        };
        locationFinder.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, loc_listener);
        Location currentLocation = locationFinder.getLastKnownLocation("gps");

        if(currentLocation == null){
            currentLocation.setLongitude(40.002546);
            currentLocation.setLatitude(-83.015276);
        }


        return currentLocation;
    }

    public void calculateDistance(View v) throws Exception {
        Location currentLocation = getLocation();
        String distString = requestAndParseDistance(currentLocation);
        distance = (TextView) findViewById(R.id.dist);
        distance.setText(distString);
    }

    public String requestAndParseDistance(Location destination) throws Exception {
        String dist;
        //build request to distance matrix
        String uri = "https://maps.googleapis.com/maps/api/distancematrix/json?";
        uri += "origins=" + URLEncoder.encode(Double.toString(factoryLocation.getLatitude()),"UTF-8") + ","
                + URLEncoder.encode(Double.toString(factoryLocation.getLongitude()), "UTF-8");
        uri += "&destinations" + URLEncoder.encode(Double.toString(destination.getLatitude()), "UTF-8") + ","
                + URLEncoder.encode(Double.toString(destination.getLongitude()), "UTF-8");
        uri += "&units=imperial";
        uri += "&key=" + URLEncoder.encode(apiKey,"UTF-8");

        URL url = new URL(uri);


        //Scan reply from distance matrix NEED TO DO THIS IN ASYNC TASK OR THREAD

        Scanner scan;
        InputStream in = url.openStream();
        scan = new Scanner(in);

        String requestData = "";
        while (scan.hasNext())
            requestData += scan.nextLine();
        scan.close();

        //parse JSON from Distance Matrix
        JSONObject json = new JSONObject(requestData);
        JSONObject rows = json.getJSONObject("rows");
        JSONObject elements = rows.getJSONObject("elements");
        JSONObject distance = elements.getJSONObject("distance");
        dist = distance.getString("text");

        return dist;
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
            locationFinder = (LocationManager) rootView.getContext()
                    .getSystemService(Context.LOCATION_SERVICE);
            return rootView;
        }
    }


}
