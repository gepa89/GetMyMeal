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
import py.com.gepalab.getmymeal.Activities.UIMeal;
import py.com.gepalab.getmymeal.Activities.UIRecipe;
import py.com.gepalab.getmymeal.Activities.UITask;
import py.com.gepalab.getmymeal.Controller.AppController;
import py.com.gepalab.getmymeal.Domain.Category;
import py.com.gepalab.getmymeal.Domain.Meal;
import py.com.gepalab.getmymeal.Domain.Recipe;

import static py.com.gepalab.getmymeal.Data.AppConfig.URL_GET_MEAL_CAT;
import static py.com.gepalab.getmymeal.Data.AppConfig.URL_GET_MEAL;
import static py.com.gepalab.getmymeal.Data.AppConfig.URL_SEARCH_MEAL;
import static py.com.gepalab.getmymeal.Data.AppConfig.URL_LOOKUP_MEAL;

/**
 * The Meal API wrapper
 *
 */
public class MealAPI {
    final String ID_SEARCH = "i";
    final String CATEGORY_FILTER = "c";
    final String INGREDIENT_FILTER = "i";
    final String NAME_FILTER = "a";
    final String NAME_SEARCH = "S";
    public MealAPI() {

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

                    category.strCategoryDescription = jsonObject.getString("strCategoryDescription");

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




    public void listMealForParam(final String searchParam, final String param, UIMeal uiMeal){
        final ArrayList<Meal> ret = new ArrayList<>();
        Log.e("search Param", searchParam);
        Log.e("search ", param);
        String tag_string_req = "req_get_meal_by_param";
        String uri = String.format(URL_GET_MEAL+"?"+searchParam+"=%1$s",
                param);
        Log.e("search uri", uri);
        StringRequest stReq = new StringRequest(Request.Method.GET, uri, response -> {
            Log.e("response raw",response);
            try{
                JSONObject jObj = new JSONObject(response);
                if(jObj.has("meals")){
                    if(jObj.get("meals") != null){
                        JSONArray res =jObj.getJSONArray("meals");
                        for(int i = 0; i < res.length(); i++){
                            final Meal meal = new Meal();
                            final JSONObject jsonObject = res.getJSONObject(i);
                            meal.idMeal = jsonObject.getString("idMeal");
                            meal.strMeal = jsonObject.getString("strMeal");
                            meal.strMealImage = jsonObject.getString("strMealThumb");
                            meal.strMealThumb = jsonObject.getString("strMealThumb")+"/preview";
                            // TODO complete implementation
                            ret.add(meal);
                        }
                        uiMeal.processMeal(ret);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();

                uiMeal.processMeal(null);
                Log.e("error in query", e.toString());
            }
        }, error -> Log.e("response", error.toString())){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stReq, tag_string_req);

    }
    public void listRecipeForParam(final String searchParam, final String param, UIRecipe uiRecipe){
        final ArrayList<Recipe> ret = new ArrayList<>();
        Log.e("search Param", searchParam);
        Log.e("search ", param);
        String tag_string_req = "req_get_meal_by_name";
        String uri = String.format(URL_SEARCH_MEAL+"?"+searchParam+"=%1$s",
                param);
        StringRequest stReq = new StringRequest(Request.Method.GET, uri, response -> {
            try{
                JSONObject jObj = new JSONObject(response);
                JSONArray res =jObj.getJSONArray("meals");
                for(int i = 0; i < res.length(); i++){
                    final Recipe recipe = new Recipe();
                    final JSONObject jsonObject = res.getJSONObject(i);
                    recipe.idMeal = jsonObject.getString("idMeal");
                    recipe.strMeal = jsonObject.getString("strMeal");
                    // TODO complete implementation
                    ret.add(recipe);
                }
                uiRecipe.processRecipe(ret);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("error", e.toString());
            }
        }, error -> Log.e("response", error.toString())){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stReq, tag_string_req);

    }
    public void listRecipeForID(final String searchParam, final String param, UIRecipe uiRecipe){
        final ArrayList<Recipe> ret = new ArrayList<>();
        Log.e("search Param", searchParam);
        Log.e("search ", param);
        String tag_string_req = "req_get_meal_by_id";
        String uri = String.format(URL_LOOKUP_MEAL+"?"+searchParam+"=%1$s",
                param);
        StringRequest stReq = new StringRequest(Request.Method.GET, uri, response -> {
            try{
                JSONObject jObj = new JSONObject(response);
                JSONArray res =jObj.getJSONArray("meals");
                for(int i = 0; i < res.length(); i++){
                    final Recipe recipe = new Recipe();
                    final JSONObject jsonObject = res.getJSONObject(i);
                    recipe.idMeal = jsonObject.getString("idMeal");
                    recipe.strMeal = jsonObject.getString("strMeal");
                    recipe.strCategory = jsonObject.getString("strCategory");
                    recipe.strInstructions = jsonObject.getString("strInstructions");
                    // TODO complete implementation
                    ret.add(recipe);
                }
                uiRecipe.processRecipe(ret);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("error", e.toString());
            }
        }, error -> Log.e("response", error.toString())){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stReq, tag_string_req);

    }
    public void listMealForCategory(UIMeal uiMeal, final String strCategory) {
        listMealForParam(CATEGORY_FILTER, strCategory, uiMeal);
    }
    public void getRecipeByID(UIRecipe uiRecipe, final String strRecipe) {
        listRecipeForID(ID_SEARCH, strRecipe, uiRecipe);
    }

    public void searchMeal(UIMeal uiMeal, final String strQuery, final String param) {
        if(param.equals("cat")){
            listMealForParam(CATEGORY_FILTER, strQuery, uiMeal);
        }else if (param.equals("nam")){
            listMealForParam(NAME_FILTER, strQuery, uiMeal);
        }else if (param.equals("ing")){
            listMealForParam(INGREDIENT_FILTER, strQuery, uiMeal);
        }
    }
}

