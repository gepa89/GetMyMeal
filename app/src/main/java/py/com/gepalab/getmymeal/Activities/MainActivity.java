package py.com.gepalab.getmymeal.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileDescriptor;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import py.com.gepalab.getmymeal.Adapters.MyListAdapter;
import py.com.gepalab.getmymeal.Adapters.MyRecyclerViewAdapter;
import py.com.gepalab.getmymeal.Controller.AppController;
import py.com.gepalab.getmymeal.Data.AppConfig;
import py.com.gepalab.getmymeal.Domain.Category;
import py.com.gepalab.getmymeal.Domain.Meal;
import py.com.gepalab.getmymeal.MealAPI.MealAPI;
import py.com.gepalab.getmymeal.R;

import static py.com.gepalab.getmymeal.Data.AppConfig.*;

public class MainActivity extends AppCompatActivity  implements MyRecyclerViewAdapter.ItemClickListener{
    private MyRecyclerViewAdapter adapter;
    private TextView tvCatTitle;
    private ListView lvMeals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(":::::::", "onCreate");
        setContentView(R.layout.activity_main);
        MealAPI api = new MealAPI();
        loadCategories(api);


    }

    private void loadCategories(MealAPI api) {
        Log.e(":::::::", "Cargue sus categorias");
        api.listCategory(new UITask() {
            @Override
            public void processCategory(List<Category> categories) {
                ArrayList<String> categoryName = new ArrayList<>();;
                ArrayList<Bitmap> categoryImages = new ArrayList<>();;
                for (Category category:categories) {
                    Log.e(":::::::", "Estoy de star");
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    Handler handler = new Handler(Looper.getMainLooper());

                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                /***
                                 * DO background tasks
                                 */
                                URL url = new URL(category.strCategoryThumb);
                                Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                category.bitmap = image;
                                categoryName.add(category.strCategory);
                                categoryImages.add(category.bitmap);
                            } catch(IOException e) {
                                System.out.println(e);
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    /***
                                     * DO UI Operations
                                     * Setting up horizontal RecyclerView
                                     */
                                    // set up the RecyclerView
                                    RecyclerView recyclerView = findViewById(R.id.rv_categories);
                                    LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                                    recyclerView.setLayoutManager(horizontalLayoutManager);
                                    adapter = new MyRecyclerViewAdapter(MainActivity.this, categoryImages, categoryName);
                                    adapter.setClickListener(MainActivity.this);
                                    recyclerView.setAdapter(adapter);
                                }
                            });
                        }
                    });
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemClick(View view, int position) {
        tvCatTitle = findViewById(R.id.tvCatTitle);
        tvCatTitle.setText(adapter.getItem(position));
        lvMeals = findViewById(R.id.lvMeals);

        //Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on item position " + position, Toast.LENGTH_SHORT).show();
        MealAPI api = new MealAPI();
        api.listMealForCategory(new UIMeal() {
            @Override
            public void processMeal(List<Meal> meals) {
                ArrayList<String> mealName = new ArrayList<>();
                ArrayList<Bitmap> mealImages = new ArrayList<>();

                int cMeal = meals.toArray().length;
                int cActMeal = 0;
                for (Meal meal:meals) {

                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    Handler handler = new Handler(Looper.getMainLooper());
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                /***
                                 * DO background tasks
                                 */
                                URL url = new URL(meal.strMealThumb);
                                Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                meal.bitmap = image;
                                mealName.add(meal.strMeal);
                                mealImages.add(meal.bitmap);
                            } catch(IOException e) {
                                System.out.println(e);
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    /***
                                     * DO UI Operations
                                     * Setting UP Vertical ListView
                                     */

                                    int totProc = mealName.toArray().length;
                                    if(totProc == cMeal){
                                        MyListAdapter listAdapter=new MyListAdapter(MainActivity.this, mealName, mealImages);
                                        //Log.e("Meals", mealName.toString());
                                        listAdapter.notifyDataSetChanged();
                                        lvMeals.setAdapter(listAdapter);
                                    }
                                }
                            });
                        }
                    });
                    cActMeal++;
                }
            }
        }, adapter.getItem(position));
    }
}