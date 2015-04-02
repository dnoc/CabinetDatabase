package eli.cabinetdatabase;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Search extends ActionBarActivity {

    private static EditText modelNumInput, designInput,widthInput,heightInput,depthInput = null;
    private static ListView materialList, typeList = null;

    protected static String testTag = "Testing";

    private static String MODELNUMKEY = "model_number";
    private static String DESIGNKEY = "design_file";
    private static String WIDTHKEY = "width";
    private static String HEIGHTKEY = "height";
    private static String DEPTHKEY  = "depth";
    private static String MATERIALKEY = "material";
    private static String TYPEKEY = "type";

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
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(testTag,"Entered onResume()");
    }

    public void onSubmit(View view)
    {
        boolean validQuery = true;

        ArrayList<String> colVals = new ArrayList<String>();
        ArrayList<String> cols = new ArrayList<String>();

        //Get user selected data from page
        String modelNum = modelNumInput.getText().toString();
        if (!modelNum.equals("") && modelNum.trim().length() != 0)
        {
            colVals.add(modelNum);
            cols.add(MODELNUMKEY + "=?");
        }
        String design = designInput.getText().toString();
        if  (!design.equals("") &&  design.trim().length() != 0)
        {
            colVals.add(design);
            cols.add(DESIGNKEY + "=?");
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
            cols.add(WIDTHKEY + "=?");
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
            cols.add(HEIGHTKEY + "=?");
        }

        int depth = convertToInt(depthInput.getText().toString());
        if (depth < 0) {
            Toast.makeText(this, "Please enter a valid depth", Toast.LENGTH_SHORT).show();
            validQuery = false;
        }
        else if(depth != 0)
        {
            colVals.add(Integer.toString(depth));
            cols.add(DEPTHKEY + "=?");
        }

        //Check for selected material values
        int selectedCount = 0;
        for (int i = 0; i < materialList.getAdapter().getCount(); i++)
        {
            if (materialList.isItemChecked(i))
            {
                colVals.add(materialList.getItemAtPosition(i).toString());
                selectedCount++;
            }
        }

        boolean first = true;
        while (selectedCount > 0)
        {
            if (selectedCount == 1)
            {
                cols.add(MATERIALKEY + "=? )");
            }
            else
            {
                if (first)
                {
                    cols.add("(" + MATERIALKEY + "=? OR ");
                    first = false;
                }
                else {
                    cols.add(MATERIALKEY + "=? OR ");
                }
            }
            selectedCount--;
        }

        //Check for selected type values
        selectedCount = 0;
        for (int i = 0; i < typeList.getAdapter().getCount(); i++)
        {
            if (typeList.isItemChecked(i))
            {
                colVals.add(typeList.getItemAtPosition(i).toString());
                selectedCount++;
            }
        }

        first = true;
        while (selectedCount > 0)
        {
            if (selectedCount == 1)
            {
                cols.add(TYPEKEY + "=? ");
            }
            else
            {
                if (first)
                {
                    cols.add("" + TYPEKEY + "=? OR ");
                    first = false;
                }
                else {
                    cols.add(TYPEKEY + "=? OR ");
                }
            }
            selectedCount--;
        }

        if (validQuery) {
            try{
                //Make ASync task to query database
                //    new AsyncTask<Void, Void, Void>() {
                //        @Override
                //        protected Void doInBackground(Void... params) {
                Intent in = new Intent(getApplicationContext(), CabinetResults.class);
                in.putStringArrayListExtra("Selection", cols);
                in.putStringArrayListExtra("SelectionArgs", colVals);
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

        if (!modelNumInput.getText().toString().equals("")) {
            outState.putString(MODELNUMKEY, modelNumInput.getText().toString());
        }
        if (!designInput.getText().toString().equals("")) {
            outState.putString(DESIGNKEY, designInput.getText().toString());
        }
        if (!widthInput.getText().toString().equals("")) {
            outState.putString(WIDTHKEY, widthInput.getText().toString());
        }
        if (!heightInput.getText().toString().equals("")) {
            outState.putString(HEIGHTKEY, heightInput.getText().toString());
        }
        if (!depthInput.getText().toString().equals("")) {
            outState.putString(DEPTHKEY, depthInput.getText().toString());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            String temp;
            temp = savedInstanceState.getString(MODELNUMKEY);
            if (temp != null) {
                modelNumInput.setText(temp);
            }
            temp = savedInstanceState.getString(DESIGNKEY);
            if (temp != null) {
                designInput.setText(temp);
            }
            temp = savedInstanceState.getString(WIDTHKEY);
            if (temp != null) {
                widthInput.setText(temp);
            }
            temp = savedInstanceState.getString(HEIGHTKEY);
            if (temp != null) {
                heightInput.setText(temp);
            }
            temp = savedInstanceState.getString(DEPTHKEY);
            if (temp != null) {
                depthInput.setText(temp);
            }
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

            setRetainInstance(true);

            //set Views
            modelNumInput = (EditText) rootView.findViewById(R.id.modelNumText);

            designInput = (EditText) rootView.findViewById(R.id.designText);

            widthInput = (EditText) rootView.findViewById(R.id.widthText);

            heightInput = (EditText) rootView.findViewById(R.id.heightText);

            depthInput = (EditText) rootView.findViewById(R.id.depthText);

            materialList = (ListView) rootView.findViewById(R.id.materialListView);
            String[] materials = new String[]{"Steel","Wood"};
            ArrayAdapter<String> materialAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_multiple_choice,materials);

            materialList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            materialList.setAdapter(materialAdapter);

            typeList = (ListView) rootView.findViewById(R.id.typeListView);
            String[] types = new String[]{"Sitting","ADA","Standing","Wall","Full"};
            ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_multiple_choice,types);

            typeList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            typeList.setAdapter(typeAdapter);

            return rootView;
        }
    }



}
