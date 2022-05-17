package py.com.gepalab.getmymeal.Adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import py.com.gepalab.getmymeal.Activities.MainActivity;
import py.com.gepalab.getmymeal.R;

public class MyListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> maintitle;
    private final List<Bitmap> imgid;

    public MyListAdapter(Activity context, List<String> maintitle, List<Bitmap> imgid) {
        super(context, R.layout.list_item, maintitle);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.maintitle=maintitle;
        //this.subtitle="";
        this.imgid=imgid;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_item, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);

        titleText.setText(maintitle.get(position));
        imageView.setImageBitmap(imgid.get(position));
        //subtitleText.setText(subtitle[position]);

        return rowView;

    };
}