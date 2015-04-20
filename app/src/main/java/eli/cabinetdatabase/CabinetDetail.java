package eli.cabinetdatabase;

import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;


public class CabinetDetail extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabinet_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cabinet_detail, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_cabinet_detail, container, false);

            Bundle extras = getActivity().getIntent().getExtras();
            String img = extras.getString("imgFile");

            Uri imgFile = Uri.parse(img);
            ImageView cabImg = (ImageView) rootView.findViewById(R.id.cabImg);
            cabImg.setImageURI(imgFile);

            int position = extras.getInt("position");
            Cabinet cabinet = CabinetResults.cabinets.get(position);

            String info = cabinet.getModelNum()+"\n\n";
            info += "Catalog: " + cabinet.getCatalogName() + "\n";
            info += "Material: " + cabinet.getMaterial() + "\n";
            info += "Type: " + cabinet.getType() + "\n";
            info += "Height: " + cabinet.getHeight() + "\n";
            info += "Depth: " + cabinet.getDepth() + "\n";
            info += "Width: " + cabinet.getWidth() + "\n";

            TextView cabInfo = (TextView) rootView.findViewById(R.id.cabInfo);
            cabInfo.setText(info);


            return rootView;
        }
    }
}
