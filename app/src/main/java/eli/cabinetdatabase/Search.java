package eli.cabinetdatabase;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Search extends ActionBarActivity {

    private static EditText modelNumInput, designInput,widthInput,heightInput,depthInput = null;
    private static ListView materialList, typeList = null;
    private static Button searchButton = null;

    private static String testTag = "Testing";
    private static String MODELNUMKEY = "ModelNumber";
    private static String DESIGNKEY = "Design";
    private static String WIDTHKEY = "Width";
    private static String HEIGHTKEY = "Height";
    private static String DEPTHKEY  = "Depth";
    private static String MATERIALKEY = "Material";
    private static String TYPEKEY = "Type";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }



        Log.d(testTag,"Entered onCreate()");


    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(testTag,"Entered onStart()");

        //set Views
        if (modelNumInput == null) {
            modelNumInput = (EditText) findViewById(R.id.modelNumText);
        }
        if(designInput == null) {
            designInput = (EditText) findViewById(R.id.designText);
        }
        if(widthInput == null) {
            widthInput = (EditText) findViewById(R.id.widthText);
        }
        if (heightInput == null) {
            heightInput = (EditText) findViewById(R.id.heightText);
        }
        if(depthInput == null) {
            depthInput = (EditText) findViewById(R.id.depthText);
        }
        if (materialList == null) {
            materialList = (ListView) findViewById(R.id.materialListView);
            String[] materials = new String[]{"Steel","Wood"};
            ArrayAdapter<String> materialAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,materials);

            materialList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            materialList.setAdapter(materialAdapter);
        }
        if (typeList == null) {
            typeList = (ListView) findViewById(R.id.typeListView);
            String[] types = new String[]{"Sitting","ADA","Standing","Wall","Full"};
            ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,types);

            typeList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            typeList.setAdapter(typeAdapter);
        }
        if (searchButton == null) {
            searchButton = (Button) findViewById(R.id.search_button);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(testTag,"Entered onResume()");
    }

    public void onSubmit(View view)
    {
        boolean validQuery = true;
        //Create database object

        //Start SELECT statement
        //DB = new DBHelper(getBaseContext());
        //SQLiteDatabase db = DB.getReadableDatabase();

        ArrayList<String> colVals = new ArrayList<String>();
        ArrayList<String> cols = new ArrayList<String>();

        //Get user selected data from page
        String modelNum = modelNumInput.getText().toString();
        if (!modelNum.equals("") && modelNum.trim().length() != 0)
        {
            colVals.add(modelNum);

            //Add Column name i.e.
            // cols.add(DB.cabinet_table_modelnum_Column + "=?");
        }
        String design = designInput.getText().toString();
        if  (!design.equals("") &&  design.trim().length() != 0)
        {
            colVals.add(design);

            //Add Column name i.e.
            // cols.add(DB.cabinet_table_modelnum_Column + "=?");
        }

        int width = convertToInt(widthInput.getText().toString());
        if (width < 0)
        {
            Toast.makeText(this, "Please enter a valid width", Toast.LENGTH_SHORT).show();
            validQuery = false;
        }
        else if (width != 0)
        {
            colVals.add(Integer.toString(width));

            //Add Column name i.e.
            // cols.add(DB.cabinet_table_modelnum_Column + "=?");
        }

        int height = convertToInt(heightInput.getText().toString());
        if (height < 0)
        {
            Toast.makeText(this, "Please enter a valid height", Toast.LENGTH_SHORT).show();
            validQuery = false;
        }
        else if (height != 0)
        {
            colVals.add(Integer.toString(height));

            //Add Column name i.e.
            // cols.add(DB.cabinet_table_modelnum_Column + "=?");
        }

        int depth = convertToInt(depthInput.getText().toString());
        if (depth < 0) {
            Toast.makeText(this, "Please enter a valid depth", Toast.LENGTH_SHORT).show();
            validQuery = false;
        }
        else if(depth != 0)
        {
            colVals.add(Integer.toString(depth));

            //Add Column name i.e.
            // cols.add(DB.cabinet_table_modelnum_Column + "=?");
        }
        //Check for selected values of ListViews here!!!

        if (validQuery) {
            //String[] selection = (String[]) cols.toArray();
            //String[] selectionArgs = (String[]) colVals.toArray();

            try{
                //Better way to do this??
                //Cursor cursor = db.query(DBHelper.DATABASE_TABLE_NAME, columns, selection, selectionArgs, null, null, null);

                //Save cursor in bundle or other package visible var and open cabinet results page
                Intent in = new Intent(getApplicationContext(), CabinetResults.class);
                //in.putExtra("CursorObj", cursor as serializable object)
                startActivity(in);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private int convertToInt(String value)
    {
        int retVal = 0;
        try
        {
            retVal = Integer.parseInt(value);
            return retVal;
        }
        catch (Exception e)
        {
            return  retVal;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(testTag,"Entered onPause()");
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (modelNumInput.getText().toString() != "") {
            outState.putString(MODELNUMKEY, modelNumInput.getText().toString());
        }
        if (designInput.getText().toString() != "") {
            outState.putString(DESIGNKEY, designInput.getText().toString());
        }
        if (widthInput.getText().toString() != "") {
            outState.putString(WIDTHKEY, widthInput.getText().toString());
        }
        if (heightInput.getText().toString() != "") {
            outState.putString(HEIGHTKEY, heightInput.getText().toString());
        }
        if (depthInput.getText().toString() != "") {
            outState.putString(DEPTHKEY, depthInput.getText().toString());
        }
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        String temp;
        temp= savedInstanceState.getString(MODELNUMKEY);
        if (temp != null) {
            modelNumInput.setText(temp);
        }
        temp= savedInstanceState.getString(DESIGNKEY);
        if (temp != null) {
            designInput.setText(temp);
        }
        temp= savedInstanceState.getString(WIDTHKEY);
        if (temp != null) {
            widthInput.setText(temp);
        }
        temp= savedInstanceState.getString(HEIGHTKEY);
        if (temp != null) {
            heightInput.setText(temp);
        }
        temp= savedInstanceState.getString(DEPTHKEY);
        if (temp != null) {
            depthInput.setText(temp);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(testTag,"Entered onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(testTag,"Entered onDestroy()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.d(testTag,"Entered onRestart()");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_search, container, false);
            return rootView;
        }
    }



}
