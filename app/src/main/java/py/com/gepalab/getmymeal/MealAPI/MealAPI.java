package py.com.gepalab.getmymeal.MealAPI;

import android.app.Activity;
import android.content.pm.LabeledIntent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Function;

import py.com.gepalab.getmymeal.Activities.MainActivity;
import py.com.gepalab.getmymeal.Activities.UITask;
import py.com.gepalab.getmymeal.Controller.AppController;
import py.com.gepalab.getmymeal.Domain.Category;
import py.com.gepalab.getmymeal.Domain.Meal;

import static py.com.gepalab.getmymeal.Data.AppConfig.URL_GET_MEAL_CAT;
import static py.com.gepalab.getmymeal.Data.AppConfig.URL_GET_MEAL;


/**
 * The Meal API wrapper
 *
 */
public class MealAPI {
    final String CATEGORY_FILTER = "c";
    final String INGREDIENT_FILTER = "i";
    final String NAME_FILTER = "a";
    private final String url;



    public MealAPI(String url) {
        this.url = url;
    }

    /**
     * List all the {@link Category} available in the menu.
     *
     *
     * @return The {@link List} with all the available Categories.
     */
    public void listCategory(UITask uiTask){
        Log.e("MealAPI", "Trying to get categories");
        final ArrayList<Category> ret = new ArrayList<>();
        String tag_string_req = "req_get_meal_cat";
        StringRequest stReq = new StringRequest(Request.Method.GET, URL_GET_MEAL_CAT, response -> {
            try{
                Log.e("MealAPI", "Inside the Request method");
                JSONObject jObj = new JSONObject(response);
                JSONArray res =jObj.getJSONArray("categories");
                Log.e("MealAPI", "We got " + res.length());
                for(int i = 0; i < res.length(); i++){
                    final Category category = new Category();
                    final JSONObject jsonObject = res.getJSONObject(i);
                    category.idCategory = jsonObject.getString("idCategory");
                    category.strCategory = jsonObject.getString("strCategory");
                    category.strCategoryThumb = jsonObject.getString("strCategoryThumb");

                    // TODO complete implementation
                    ret.add(category);

                }
                uiTask.processCategory(ret);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.e("MealAPI", error.toString())){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stReq, tag_string_req);
    }

    /**
     * Search all the meals given its Category name
     *
     * @param strCategory The Category name for the meal
     *
     * @return A List of {@link Meal} matching the criteria.
     */
    public final List<Meal> listMealForCategory(final String strCategory){

        return listMealForParam(CATEGORY_FILTER, strCategory);
    }

    /**
     * Search all the meals given its ingredient name
     *
     * @param ingredient The Ingredient name for the meal
     *
     * @return A List of {@link Meal} matching the criteria.
     */
    public final List<Meal> listMealForIngredient(final String ingredient){

        return listMealForParam(INGREDIENT_FILTER, ingredient);
    }

    /**
     * Search all the meals given its ingredient name
     *
     * @param name The common name for the meal
     *
     * @return A List of {@link Meal} matching the criteria.
     */
    public final List<Meal> listMealForName(final String name){
        return listMealForParam(NAME_FILTER, name);
    }

    private List<Meal> listMealForParam(final String searchParam, final String param){
        final ArrayList<Meal> ret = new ArrayList<>();
        String tag_string_req = "req_get_meal_cat";
        StringRequest stReq = new StringRequest(Request.Method.GET, URL_GET_MEAL, response -> {
            try{
                JSONObject jObj = new JSONObject(response);
                JSONArray res =jObj.getJSONArray("meals");
                for(int i = 0; i < res.length(); i++){
                    final Meal meal = new Meal();
                    final JSONObject jsonObject = res.getJSONObject(i);
                    meal.idMeal = jsonObject.getString("idMeal");
                    meal.strMeal = jsonObject.getString("strMeal");
                    meal.strMealThumb = jsonObject.getString("strMealThumb");
                    // TODO complete implementation
                    ret.add(meal);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.e("response", error.toString())){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put(searchParam, param);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stReq, tag_string_req);
        return ret;
    }
}

