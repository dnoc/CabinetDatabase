package eli.cabinetdatabase;


import android.app.Activity;
import android.content.Context;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;


public class DistanceCalculator extends FragmentActivity {

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

    public Location getLocation() {
        Location currentLocation = locationFinder.getLastKnownLocation("gps");
        return currentLocation;
    }

    public void calculateDistance() throws Exception {
        Location currentLocation = getLocation();
        String distString = requestAndParseDistance(currentLocation);
        distance = (TextView) findViewById(R.id.dist);
        distance.setText(distString);
    }

    public String requestAndParseDistance(Location destination) throws Exception {
        String dist;
        String uri = "https://maps.googleapis.com/maps/api/distancematrix/json?";
        uri += "origins=" + Double.toString(factoryLocation.getLatitude()) + ","
                + Double.toString(factoryLocation.getLongitude());
        uri += "&destinations" + Double.toString(destination.getLatitude()) + ","
                + Double.toString(destination.getLongitude());
        uri += "&units=imperial";
        uri += "&key=" + apiKey;

        String urlString;
        URL url;
        try {
            urlString = URLEncoder.encode(uri, StandardCharsets.UTF_8.name());
            try {
                url = new URL(urlString);
            } catch (MalformedURLException e) {
                throw new AssertionError("Bad URL");
            }
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError("UTF-8 is unknown");
        }

        Scanner scan;
        try {
            scan = new Scanner(url.openStream());
        } catch (IOException e) {
            throw new AssertionError("Request Error");
        }
        String requestData = "";
        while (scan.hasNext())
            requestData += scan.nextLine();
        scan.close();

        JSONObject json = new JSONObject(requestData);




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
