package py.com.gepalab.getmymeal.Activities;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import py.com.gepalab.getmymeal.Adapters.MyListAdapter;
import py.com.gepalab.getmymeal.Adapters.MyRecyclerViewAdapter;
import py.com.gepalab.getmymeal.Domain.Category;
import py.com.gepalab.getmymeal.Domain.Meal;
import py.com.gepalab.getmymeal.MealAPI.MealAPI;
import py.com.gepalab.getmymeal.R;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {
    public static final String MEAL_API = "MealAPI";
    private MyRecyclerViewAdapter adapter;
    private ListView lvMeals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MealAPI api = new MealAPI();
        loadCategories(api);
    }

    private void loadCategories(MealAPI api) {
        api.listCategory(categories -> {
            ArrayList<String> categoryName = new ArrayList<>();
            ;
            ArrayList<Bitmap> categoryImages = new ArrayList<>();
            ;
            for (Category category : categories) {
                ExecutorService executor = Executors.newSingleThreadExecutor();
                Handler handler = new Handler(Looper.getMainLooper());

                executor.execute(() -> {
                    try {
                        /*
                         * DO background tasks
                         */
                        URL url = new URL(category.getStrCategoryThumb());
                        Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        category.setBitmap(image);
                        categoryName.add(category.getStrCategory());
                        categoryImages.add(category.getBitmap());
                    } catch (IOException e) {
                        Log.e(MEAL_API, e.getMessage());
                    }
                    handler.post(() -> {
                        /*
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
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        final SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(MainActivity.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(getApplicationContext(), SearchResultsActivity.class)));
        searchView.setSubmitButtonEnabled(true);
        return true;
    }

    @Override
    public void onItemClick(View view, int position) {
        TextView tvCatTitle = findViewById(R.id.tvCatTitle);
        tvCatTitle.setText(adapter.getItem(position));
        lvMeals = findViewById(R.id.lvMeals);

        MealAPI api = new MealAPI();
        api.listMealForCategory(meals -> {
            ArrayList<String> mealID = new ArrayList<>();
            ArrayList<String> mealName = new ArrayList<>();
            ArrayList<String> mealImages = new ArrayList<>();
            ArrayList<String> mealImagesView = new ArrayList<>();


            for (Meal meal : meals) {
                mealID.add(meal.getIdMeal());
                mealName.add(meal.getStrMeal());
                mealImages.add(meal.getStrMealThumb());
                mealImagesView.add(meal.getStrMealImage());

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
                    String mealid = mealID.get(position1);

                    mBundle.putString("recipe", recipe);
                    mBundle.putString("recThumb", imgUri);
                    mBundle.putString("recipeID", mealid);
                    //Toast.makeText(MainActivity.this, recipe, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
                    intent.putExtras(mBundle);
                    startActivity(intent);

                }
            });

        }, adapter.getItem(position));
    }

}