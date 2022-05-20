package py.com.gepalab.getmymeal.Adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import py.com.gepalab.getmymeal.Activities.MainActivity;
import py.com.gepalab.getmymeal.R;

public class MyListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> maintitle;
    private final List<String> imgid;

    public MyListAdapter(Activity context, List<String> maintitle, List<String> imgid) {
        super(context, R.layout.list_item, maintitle);

        this.context=context;
        this.maintitle=maintitle;
        this.imgid=imgid;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_item, parent,false);

        TextView titleText = rowView.findViewById(R.id.title);
        ImageView imageView = rowView.findViewById(R.id.icon);

        titleText.setText(maintitle.get(position));

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Bitmap imBitmap = null; // Maybe some default bitmap here from res?
                try {
                    /*
                     * in the background
                     */
                    URL url = new URL(imgid.get(position)); // Fix variable names
                    imBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch(IOException e) {
                    Log.e("MealAPI", e.getMessage());
                }
                Bitmap finalImBitmap = imBitmap;
                handler.post(new Runnable() {
                    // back to the UI
                    @Override
                    public void run() {
                        imageView.setImageBitmap(finalImBitmap);
                    }
                });
            }
        });
        return rowView;

    };
}
