package eli.cabinetdatabase;

import android.content.Context;
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
        if (materialList == null) {
            materialList = (ListView) findViewById(R.id.materialListView);
            String[] materials = new String[]{"Steel","Wood"};
            ArrayAdapter<String> materialAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice,materials);

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

    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(testTag,"Entered onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(testTag,"Entered onPause()");
    }

    public void onSaveInstanceState(Bundle outState) {

        Log.d(testTag,"Entered saveInstance()");

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

        super.onSaveInstanceState(outState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(testTag,"Entered restoreInstance()");

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

            //set Views
            if (modelNumInput == null) {
                modelNumInput = (EditText) rootView.findViewById(R.id.modelNumText);
            }
            if(designInput == null) {
                designInput = (EditText) rootView.findViewById(R.id.designText);
            }
            if(widthInput == null) {
                widthInput = (EditText) rootView.findViewById(R.id.widthText);
            }
            if (heightInput == null) {
                heightInput = (EditText) rootView.findViewById(R.id.heightText);
            }
            if(depthInput == null) {
                depthInput = (EditText) rootView.findViewById(R.id.depthText);
            }

            if (searchButton == null) {
                searchButton = (Button) rootView.findViewById(R.id.search_button);
            }

            return rootView;
        }
    }



}
