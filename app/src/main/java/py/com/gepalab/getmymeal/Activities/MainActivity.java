package py.com.gepalab.getmymeal.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
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
        //Log.e(":::::::", "onCreate");
        setContentView(R.layout.activity_main);
        MealAPI api = new MealAPI();
        loadCategories(api);
    }

    private void loadCategories(MealAPI api) {
        Log.e(":::::::", "Cargue sus categorias");
        api.listCategory(categories -> {
            ArrayList<String> categoryName = new ArrayList<>();;
            ArrayList<Bitmap> categoryImages = new ArrayList<>();;
            for (Category category:categories) {
                Log.e(":::::::", "Estoy de star");
                ExecutorService executor = Executors.newSingleThreadExecutor();
                Handler handler = new Handler(Looper.getMainLooper());

                executor.execute(() -> {
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
                    handler.post(() -> {
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
                    });
                });
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        final SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(MainActivity.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(getApplicationContext(), SearchResultsActivity.class)));
        searchView.setSubmitButtonEnabled(true);
        return true;
    }

    @Override
    public void onItemClick(View view, int position) {
        tvCatTitle = findViewById(R.id.tvCatTitle);
        tvCatTitle.setText(adapter.getItem(position));
        lvMeals = findViewById(R.id.lvMeals);

        //Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on item position " + position, Toast.LENGTH_SHORT).show();
        MealAPI api = new MealAPI();
        api.listMealForCategory(meals -> {
            ArrayList<String> mealName = new ArrayList<>();
            ArrayList<String> mealImages = new ArrayList<>();
            ArrayList<String> mealImagesView = new ArrayList<>();


            for (Meal meal : meals) {
                mealName.add(meal.strMeal);
                mealImages.add(meal.strMealThumb);
                mealImagesView.add(meal.strMealImage);

            }
            MyListAdapter listAdapter = new MyListAdapter(MainActivity.this, mealName, mealImages);
            //Log.e("Meals", mealName.toString());
            listAdapter.notifyDataSetChanged();
            lvMeals.setAdapter(listAdapter);
            lvMeals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view1, int position1, long id) {
                    Bundle mBundle = new Bundle();
                    //Log.e("parent",parent.getAdapter().getItem(position).toString());
                    String recipe = String.valueOf(parent.getItemAtPosition(position1));
                    String imgUri = mealImagesView.get(position1);
                    mBundle.putString("recipe", recipe);
                    mBundle.putString("recThumb", imgUri);
                    //Toast.makeText(MainActivity.this, recipe, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
                    intent.putExtras(mBundle);
                    startActivity(intent);

                }
            });

        }, adapter.getItem(position));
    }

}