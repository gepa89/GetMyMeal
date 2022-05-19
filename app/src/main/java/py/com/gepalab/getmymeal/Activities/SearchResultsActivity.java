package py.com.gepalab.getmymeal.Activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

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
            Log.e("search query", query);
            //use the query to search your data somehow
            MealAPI api = new MealAPI();
            api.searchMeal((UIMeal) meals -> {
                ArrayList<String> mealName = new ArrayList<>();
                ArrayList<String> mealImages = new ArrayList<>();
                ArrayList<String> mealImagesView = new ArrayList<>();
                Log.e("meals size", String.valueOf(meals));
                if(String.valueOf(meals) != "null"){
                    for (Meal meal : meals) {
                        //Log.e("load by ingredient", meal.strMeal);
                        mealName.add(meal.strMeal);
                        mealImages.add(meal.strMealThumb);
                        mealImagesView.add(meal.strMealImage);
                    }
                    MyListAdapter listAdapter = new MyListAdapter(SearchResultsActivity.this, mealName, mealImages);
                    //Log.e("Meals", mealName.toString());
                    listAdapter.notifyDataSetChanged();
                    lvSearchResult.setAdapter(listAdapter);

                    lvSearchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view1, int position1, long id) {
                            Bundle mBundle = new Bundle();
                            //Log.e("parent",parent.getAdapter().getItem(position).toString());
                            String recipe = String.valueOf(parent.getItemAtPosition(position1));
                            String imgUri = mealImagesView.get(position1);
                            mBundle.putString("recipe", recipe);
                            mBundle.putString("recThumb", imgUri);
                            //Toast.makeText(SearchResultsActivity.this, recipe, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SearchResultsActivity.this, RecipeActivity.class);
                            intent.putExtras(mBundle);
                            startActivity(intent);
                        }
                    });
                }else{
                    Toast.makeText(SearchResultsActivity.this, "No Results", Toast.LENGTH_LONG).show();
                    finish();
                }

            }, query, "ing");
        }
    }
}