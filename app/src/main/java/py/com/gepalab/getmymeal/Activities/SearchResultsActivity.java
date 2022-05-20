package py.com.gepalab.getmymeal.Activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import py.com.gepalab.getmymeal.Adapters.MyListAdapter;
import py.com.gepalab.getmymeal.Domain.Meal;
import py.com.gepalab.getmymeal.MealAPI.MealAPI;
import py.com.gepalab.getmymeal.R;

public class SearchResultsActivity extends Activity {
    private ListView lvSearchResult;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        lvSearchResult = findViewById(R.id.lvSearchResult);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.i("search query", query);
            //use the query to search your data somehow
            MealAPI api = new MealAPI();
            List<String> mealNameGlo = new ArrayList<>();
            List<String> mealImagesGlo = new ArrayList<>();
            List<String> mealImagesViewGlo = new ArrayList<>();
            List<String> mealIDGlo = new ArrayList<>();

            api.searchMeal((UIMeal) meals -> {
                Log.e("mealinIng", meals.toString());
                if(meals != null  && !meals.isEmpty()){
                    for (Meal meal : meals) {
                        if(!mealNameGlo.contains(meal.getStrMeal())){
                            mealNameGlo.add(meal.getStrMeal());
                            mealImagesGlo.add(meal.getStrMealThumb());
                            mealImagesViewGlo.add(meal.getStrMealImage());
                            mealIDGlo.add(meal.getIdMeal());
                        }
                    }
                    MyListAdapter listAdapter = new MyListAdapter(SearchResultsActivity.this, mealNameGlo, mealImagesGlo);

                    listAdapter.notifyDataSetChanged();
                    lvSearchResult.setAdapter(listAdapter);

                    lvSearchResult.setOnItemClickListener((parent, view1, position1, id) -> {
                        Bundle mBundle = new Bundle();
                        String recipe = String.valueOf(parent.getItemAtPosition(position1));
                        String imgUri = mealImagesViewGlo.get(position1);
                        String mealID = mealIDGlo.get(position1);
                        if(!mealID.equals("")){
                            mBundle.putString("recipe", recipe);
                            mBundle.putString("recThumb", imgUri);
                            mBundle.putString("recID", mealID);
                            mealNameGlo.remove("No Results");
                            mealImagesGlo.remove("");
                            Intent intent1 = new Intent(SearchResultsActivity.this, RecipeActivity.class);
                            intent1.putExtras(mBundle);
                            startActivity(intent1);
                        }
                    });
                }
            }, query, "ing");
            api.searchMeal((UIMeal) meals -> {
                Log.e("mealinCat", meals.toString());
                if(meals != null  && !meals.isEmpty()){
                    for (Meal meal : meals) {
                        if(!mealNameGlo.contains(meal.getStrMeal())){
                            mealNameGlo.add(meal.getStrMeal());
                            mealImagesGlo.add(meal.getStrMealThumb());
                            mealImagesViewGlo.add(meal.getStrMealImage());
                            mealIDGlo.add(meal.getIdMeal());
                            mealNameGlo.remove("No Results");
                            mealImagesGlo.remove("");
                        }
                    }
                    MyListAdapter listAdapter = new MyListAdapter(SearchResultsActivity.this, mealNameGlo, mealImagesGlo);

                    listAdapter.notifyDataSetChanged();
                    lvSearchResult.setAdapter(listAdapter);

                    lvSearchResult.setOnItemClickListener((parent, view1, position1, id) -> {
                        Bundle mBundle = new Bundle();
                        String recipe = String.valueOf(parent.getItemAtPosition(position1));
                        String imgUri = mealImagesViewGlo.get(position1);
                        String mealID = mealIDGlo.get(position1);
                        if(!mealID.equals("")){
                            mBundle.putString("recipe", recipe);
                            mBundle.putString("recThumb", imgUri);
                            mBundle.putString("recID", mealID);
                            Intent intent1 = new Intent(SearchResultsActivity.this, RecipeActivity.class);
                            intent1.putExtras(mBundle);
                            startActivity(intent1);
                        }
                    });
                }
            }, query, "cat");
            api.searchMeal((UIMeal) meals -> {
                Log.e("mealinCat", meals.toString());
                if(meals != null  && !meals.isEmpty()){
                    for (Meal meal : meals) {
                        if(!mealNameGlo.contains(meal.getStrMeal())){
                            mealNameGlo.add(meal.getStrMeal());
                            mealImagesGlo.add(meal.getStrMealThumb());
                            mealImagesViewGlo.add(meal.getStrMealImage());
                            mealIDGlo.add(meal.getIdMeal());
                            mealNameGlo.remove("No Results");
                            mealImagesGlo.remove("");
                        }
                    }
                    MyListAdapter listAdapter = new MyListAdapter(SearchResultsActivity.this, mealNameGlo, mealImagesGlo);

                    listAdapter.notifyDataSetChanged();
                    lvSearchResult.setAdapter(listAdapter);

                    lvSearchResult.setOnItemClickListener((parent, view1, position1, id) -> {
                        Bundle mBundle = new Bundle();
                        String recipe = String.valueOf(parent.getItemAtPosition(position1));
                        String imgUri = mealImagesViewGlo.get(position1);
                        String mealID = mealIDGlo.get(position1);
                        if(!mealID.equals("")){
                            mBundle.putString("recipe", recipe);
                            mBundle.putString("recThumb", imgUri);
                            mBundle.putString("recID", mealID);
                            Intent intent1 = new Intent(SearchResultsActivity.this, RecipeActivity.class);
                            intent1.putExtras(mBundle);
                            startActivity(intent1);
                        }
                    });
                }
            }, query, "are");
            api.searchMeal((UIMeal) meals -> {
                Log.e("mealinNam", meals.toString());
                if(meals != null  && !meals.isEmpty()){
                    for (Meal meal : meals) {
                        if(!mealNameGlo.contains(meal.getStrMeal())){
                            mealNameGlo.add(meal.getStrMeal());
                            mealImagesGlo.add(meal.getStrMealThumb());
                            mealImagesViewGlo.add(meal.getStrMealImage());
                            mealIDGlo.add(meal.getIdMeal());
                        }
                    }
                    MyListAdapter listAdapter = new MyListAdapter(SearchResultsActivity.this, mealNameGlo, mealImagesGlo);

                    listAdapter.notifyDataSetChanged();
                    lvSearchResult.setAdapter(listAdapter);

                    lvSearchResult.setOnItemClickListener((parent, view1, position1, id) -> {
                        Bundle mBundle = new Bundle();
                        String recipe = String.valueOf(parent.getItemAtPosition(position1));
                        String imgUri = mealImagesViewGlo.get(position1);
                        String mealID = mealIDGlo.get(position1);
                        if(!mealID.equals("")){
                            mBundle.putString("recipe", recipe);
                            mBundle.putString("recThumb", imgUri);
                            mBundle.putString("recID", mealID);
                            Intent intent1 = new Intent(SearchResultsActivity.this, RecipeActivity.class);
                            intent1.putExtras(mBundle);
                            startActivity(intent1);
                        }
                    });
                }
            }, query, "nam");

        }
    }


}