package eli.cabinetdatabase;

import android.app.Activity;
import android.net.Uri;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Zak on 3/29/2015.
 */
public class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] web;
    private final Uri[] imageId;

    public CustomList(Activity context,
                      String[] web, Uri[] imageId) {
        super(context, R.layout.list_row, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_row, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(web[position]);
        if (imageId.length != 0) {
            imageView.setImageURI(imageId[position]);
        }
        else
        {
            //No rows were found for their search
            txtTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        }
        return rowView;
    }
}
