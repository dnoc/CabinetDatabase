package eli.cabinetdatabase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONArray;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    protected void onStart(){
        super.onStart();

        Button catalogButton = (Button) findViewById(R.id.catalog_button);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(rootView.getContext());
            String storedSql = settings.getString("savedSql", "");
            if (storedSql.length() == 0)
            {
                Button loadSearch = (Button) rootView.findViewById(R.id.lastSearchButton);
                loadSearch.setVisibility(View.INVISIBLE);
                loadSearch.setClickable(false);
            }
            return rootView;
        }
    }

    public void toCatalog(View v){
        startActivity(new Intent(getApplicationContext(),Search.class));
    }

    public void loadLastSearch(View v)
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(v.getContext());
        try{
            JSONArray temp = new JSONArray(settings.getString("savedSql", "[]"));
            String selectCols = temp.getString(0);
            ArrayList<String> selection;
            if (!selectCols.equals("[]")) {
                selectCols = selectCols.substring(1, selectCols.length() - 1);

                //Remove parentheses if the statement is wrapped in them
                if (selectCols.charAt(0) == '(')
                    selectCols = selectCols.substring(1, selectCols.length() - 1);

                if (selectCols.contains(",")) {
                    String[] cols = selectCols.split(",");
                    selection = new ArrayList<String>();
                    for (int i = 0; i < cols.length; i++) {
                        selection.add(cols[i]);
                    }
                }
                else
                {
                    selection = new ArrayList<String>();
                    selection.add(selectCols);
                }
            }
            else
            {
                selection = null;
            }
            ArrayList<String> selectionArgs;
            if (!temp.getString(1).equals("[]"))
            {
                String str = temp.getString(1);
                //Remove enclosing brackets
                str = str.substring(1, str.length() - 1);
                if (str.contains(","))
                {
                    String[] strings = str.split(",");
                    selectionArgs = new ArrayList<String>();
                    for (int i = 0; i < strings.length; i++)
                    {
                        selectionArgs.add(strings[i].trim());
                    }
                }
                else
                {
                    selectionArgs = new ArrayList<String>();
                    selectionArgs.add(str);
                }
            }
            else
            {
                selectionArgs = null;
            }


            Intent in = new Intent(getApplicationContext(), CabinetResults.class);
            in.putStringArrayListExtra("Selection", selection);
            in.putStringArrayListExtra("SelectionArgs", selectionArgs);

            if (temp.length() > 2) {
                String sqlMaterial = temp.getString(2);
                if (!sqlMaterial.equals("[]") && sqlMaterial.charAt(0) == '[') {
                    sqlMaterial = sqlMaterial.substring(1, sqlMaterial.length() - 1);
                }
                String str = temp.getString(3);
                ArrayList<String> materialVals;
                if (!str.equals("[]")) {
                    //Remove enclosing brackets
                    str = str.substring(1, str.length() - 1);
                    if (str.contains(",")) {
                        String[] strings = str.split(",");
                        materialVals = new ArrayList<String>();
                        for (int i = 0; i < strings.length; i++) {
                            materialVals.add(strings[i].trim());
                        }
                    }
                    else {
                        materialVals = new ArrayList<String>();
                        materialVals.add(str);
                    }

                    in.putExtra("sqlMaterial", sqlMaterial);
                    in.putStringArrayListExtra("materialVals", materialVals);
                }
                else
                {
                    materialVals = null;
                }
            }
            startActivity(in);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void toDistanceCalculator(View v)
    {
        startActivity(new Intent(getApplicationContext(),DistanceCalculator.class));
    }
}
