package eli.cabinetdatabase;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.TextView;
import android.widget.Button;
import android.location.Location;

import org.junit.Assert;
import org.junit.Test;

import eli.cabinetdatabase.DistanceCalculator;
/**
 * Created by Drew on 4/17/2015.
 */
public class DistanceCalculatorTest extends ActivityInstrumentationTestCase2<DistanceCalculator> {
    private DistanceCalculator activity;

    public DistanceCalculatorTest() {
        super (DistanceCalculator.class);

    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    //'Instrumentation run failed due to NullPointerException'
    public void testLargeDistance() {
        Location loc = new Location("gps");
        loc.setLongitude(34.030742);
        loc.setLatitude(-118.225652);
        try {
            activity.requestAndParseDistance(loc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TextView distString = (TextView) activity.findViewById(R.id.dist);
        assertEquals("2395 Miles", distString.getText());
    }


    public void testZeroDistance() {
        Location loc = new Location("gps");
        loc.setLongitude(35.791784);
        loc.setLatitude(-80.926537);
        try {
            activity.requestAndParseDistance(loc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TextView distString = (TextView) activity.findViewById(R.id.dist);
        assertEquals("0 Miles", distString.getText());
    }

    //getting an error that distString.getText = "TEXT" (doesnt change)
    //maybe the emulator can't get location
    public void testAverageDistance () {
        Location loc = new Location("gps");
        loc.setLongitude(39.767759);
        loc.setLatitude(-86.158113);
        try {
            activity.requestAndParseDistance(loc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TextView distString = (TextView) activity.findViewById(R.id.dist);
        assertEquals("539 Miles", distString.getText());
    }


    public void testNullDistance () {
        Location loc = new Location("gps");
        try {
            activity.requestAndParseDistance(loc);
        } catch (Exception e) {
            assertTrue(true);
        }
        assertTrue(false);
    }

    /* Thought about testing based on provider,
    * but running getLocation would use current location, not one that we can hardcode.
    public void GPSProviderTest () {}
    public void NetworkProviderTest () {}
    public void NullProviderTest() {}
    */

    @Override
    protected void tearDown() throws Exception { super.tearDown(); }
}
