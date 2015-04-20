package eli.cabinetdatabase;

import android.app.Instrumentation;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.TextView;
import android.widget.Button;
import org.junit.Test;
import eli.cabinetdatabase.MainActivity;


/**
 * Created by Drew on 4/17/2015.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private MainActivity activity;
    private Button catalogButton;
    private Button distanceCalcButton;
    private Button lastSearchButton;
    private static long TIMEOUT_MS = 12000;


    public MainActivityTest() {
        super(MainActivity.class);

    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        catalogButton = (Button) activity.findViewById(R.id.catalog_button);
        distanceCalcButton = (Button) activity.findViewById(R.id.toDistCalc);
        lastSearchButton = (Button) activity.findViewById(R.id.lastSearchButton);
    }

    public void testToCatalog () {
        Instrumentation.ActivityMonitor catalogMonitor =
                getInstrumentation().addMonitor(
                        eli.cabinetdatabase.Search.class.getName(), null, false);
        TouchUtils.clickView(this, catalogButton);
        ActionBarActivity searchActivity = (ActionBarActivity)
                catalogMonitor.waitForActivityWithTimeout(TIMEOUT_MS);
        assertNotNull("catalogMonitor is null.", catalogMonitor);
        assertEquals(1, catalogMonitor.getHits());
        assertEquals(eli.cabinetdatabase.Search.class, searchActivity.getClass());
        getInstrumentation().removeMonitor(catalogMonitor);
    }

    /*currently presses the last search button but just checks if search was started
    * might need to extend for more specific use
    */
    public void testToLastSearch () {
        Instrumentation.ActivityMonitor lastSearchMonitor =
                getInstrumentation().addMonitor(
                        eli.cabinetdatabase.Search.class.getName(), null, false);
        TouchUtils.clickView(this, lastSearchButton);
        ActionBarActivity searchActivity = (ActionBarActivity)
                lastSearchMonitor.waitForActivityWithTimeout(TIMEOUT_MS);
        assertNotNull("lastSearchMonitor is null.", lastSearchMonitor);
        assertEquals(1, lastSearchMonitor.getHits());
        assertEquals(eli.cabinetdatabase.Search.class, searchActivity.getClass());
        getInstrumentation().removeMonitor(lastSearchMonitor);
    }

    public void testToDistanceCalculator() {
        Instrumentation.ActivityMonitor distanceMonitor =
                getInstrumentation().addMonitor(
                        eli.cabinetdatabase.DistanceCalculator.class.getName(), null, false);
        TouchUtils.clickView(this, distanceCalcButton);
        FragmentActivity distanceActivity = (FragmentActivity)
                distanceMonitor.waitForActivityWithTimeout(TIMEOUT_MS);
        assertNotNull("distanceMonitor is null.", distanceMonitor);
        assertEquals(1, distanceMonitor.getHits());
        assertEquals(eli.cabinetdatabase.DistanceCalculator.class, distanceActivity.getClass());
        getInstrumentation().removeMonitor(distanceMonitor);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
