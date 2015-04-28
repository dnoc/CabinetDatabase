package eli.cabinetdatabase;

import android.app.Instrumentation;
import android.support.v7.app.ActionBarActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.ListView;

/**
 * Created by Drew on 4/17/2015.
 */
public class SearchTest extends ActivityInstrumentationTestCase2<Search> {
    private Search activity;
    private static long TIMEOUT_MS = 12000;

    public SearchTest() {
        super(Search.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    //TODO run
    public void testMaterialSearch () {
        ListView materialList = (ListView) activity.findViewById(R.id.materialListView);
        materialList.setSelection(1);
        Instrumentation.ActivityMonitor resultsMonitor =
                getInstrumentation().addMonitor(
                        eli.cabinetdatabase.CabinetResults.class.getName(), null, false);
        TouchUtils.clickView(this, materialList);
        ActionBarActivity resultsActivity = (ActionBarActivity)
                resultsMonitor.waitForActivityWithTimeout(TIMEOUT_MS);
        assertNotNull("resultsMonitor is null.", resultsMonitor);
        assertEquals(1, resultsMonitor.getHits());
        assertEquals(eli.cabinetdatabase.CabinetResults.class, resultsActivity.getClass());
        getInstrumentation().removeMonitor(resultsMonitor);
    }

    //TODO
    public void testTypeSearch () {


    }

    //TODO
    public void testDimensionSearch () {


    }

    @Override
    protected void tearDown() throws Exception { super.tearDown(); }

}
