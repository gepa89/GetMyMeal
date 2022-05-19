package py.com.gepalab.getmymeal.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import py.com.gepalab.getmymeal.Adapters.ViewPagerAdapter;
import py.com.gepalab.getmymeal.R;

public class RecipeActivity extends AppCompatActivity {

    private ViewPager2 vpContainer;
    private TabLayout tlContentList;
    private TextView tvMealTitle;
    private ImageView ivPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Bundle mBundle = getIntent().getExtras();
        String actTitle = mBundle.getString("recipe");
        String recThumb = mBundle.getString("recThumb");

        tvMealTitle = findViewById(R.id.tvMealTitle);
        vpContainer = findViewById(R.id.vpContainer);
        tlContentList = findViewById(R.id.tlContentList);
        ivPreview = findViewById(R.id.ivPreview);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Bitmap imBitmap = null; // Maybe some default bitmap here from res?
                try {
                    /***
                     * DO background tasks
                     */
                    URL url = new URL(recThumb); // Fix variable names
                    imBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch(IOException e) {
                    System.out.println(e);
                }
                Bitmap finalImBitmap = imBitmap;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ivPreview.setImageBitmap(finalImBitmap);
                    }
                });
            }
        });

        tvMealTitle.setText(actTitle);
        vpContainer.setAdapter(createCardAdapter());
        new TabLayoutMediator(tlContentList, vpContainer,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                         if(position == 0){
                             tab.setText("Description");
                         }else if(position == 1){
                             tab.setText("Ingredients");
                         }else if(position == 2){
                             tab.setText("Measures");
                         }
                    }
                }).attach();
    }
    private ViewPagerAdapter createCardAdapter() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        return adapter;
    }
}
