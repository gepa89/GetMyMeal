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

    /**
     * List all the {@link Category} available in the menu.
     * and calls the UITask to perform the update
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
                    category.setIdCategory(jsonObject.getString("idCategory"));
                    category.setStrCategory(jsonObject.getString("strCategory"));
                    category.setStrCategoryThumb(jsonObject.getString("strCategoryThumb"));

                    category.setStrCategoryDescription(jsonObject.getString("strCategoryDescription"));

                    ret.add(category);

                }
                uiTask.processCategory(ret);
            } catch (JSONException e) {
                Log.e("MealAPI", e.getMessage());
            }
        }, error -> Log.e("MealAPI", error.toString())){
            @Override
            protected Map<String, String> getParams(){
                return new HashMap<>();
            }
        };
        AppController.getInstance().addToRequestQueue(stReq, tag_string_req);
    }




    public void listMealForParam(final String searchParam, final String param, UIMeal uiMeal){
        final ArrayList<Meal> ret = new ArrayList<>();
        Log.i("search Param", searchParam);
        Log.i("search ", param);
        String tag_string_req = "req_get_meal_by_param";
        String uri = String.format(URL_GET_MEAL+"?"+searchParam+"=%1$s",
                param);
        Log.i("search uri", uri);
        StringRequest stReq = new StringRequest(Request.Method.GET, uri, response -> {
            try{
                JSONObject jObj = new JSONObject(response);
                if(jObj.has("meals")){
                    jObj.get("meals");
                    JSONArray res =jObj.getJSONArray("meals");
                    for(int i = 0; i < res.length(); i++){
                        final Meal meal = new Meal();
                        final JSONObject jsonObject = res.getJSONObject(i);
                        meal.setIdMeal( jsonObject.getString("idMeal"));
                        meal.setStrMeal( jsonObject.getString("strMeal"));
                        meal.setStrMealImage(jsonObject.getString("strMealThumb"));
                        meal.setStrMealThumb(jsonObject.getString("strMealThumb"));
                        ret.add(meal);
                    }
                    uiMeal.processMeal(ret);
                }
            } catch (JSONException e) {
                Log.e("MealAPI", e.getMessage());

                uiMeal.processMeal(null);
                Log.e("error in query", e.toString());
            }
        }, error -> Log.e("response", error.toString())){
            @Override
            protected Map<String, String> getParams(){
                return new HashMap<>();
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
                    recipe.setIdMeal(jsonObject.getString("idMeal"));
                    recipe.setStrMeal(jsonObject.getString("strMeal"));
                    ret.add(recipe);
                }
                uiRecipe.processRecipes(ret);
            } catch (JSONException e) {
                Log.e("MealAPI", e.getMessage());
            }
        }, error -> Log.e("response", error.toString())){
            @Override
            protected Map<String, String> getParams(){
                return new HashMap<>();
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
                    recipe.setIdMeal(jsonObject.getString("idMeal"));
                    recipe.setStrMeal(jsonObject.getString("strMeal"));
                    recipe.setStrCategory(jsonObject.getString("strCategory"));
                    recipe.setStrInstructions(jsonObject.getString("strInstructions"));
                    recipe.setStrArea(jsonObject.getString("strArea"));
                    recipe.setStrCategory(jsonObject.getString("strCategory"));

                    recipe.setStrIngredient1(jsonObject.getString("strIngredient1"));
                    recipe.setStrIngredient2(jsonObject.getString("strIngredient2"));
                    recipe.setStrIngredient3(jsonObject.getString("strIngredient3"));
                    recipe.setStrIngredient4(jsonObject.getString("strIngredient4"));
                    recipe.setStrIngredient5(jsonObject.getString("strIngredient5"));
                    recipe.setStrIngredient6(jsonObject.getString("strIngredient6"));
                    recipe.setStrIngredient7(jsonObject.getString("strIngredient7"));
                    recipe.setStrIngredient8(jsonObject.getString("strIngredient8"));
                    recipe.setStrIngredient9(jsonObject.getString("strIngredient9"));

                    recipe.setStrMeasure1(jsonObject.getString("strMeasure1"));
                    recipe.setStrMeasure2(jsonObject.getString("strMeasure2"));
                    recipe.setStrMeasure3(jsonObject.getString("strMeasure3"));
                    recipe.setStrMeasure4(jsonObject.getString("strMeasure4"));
                    recipe.setStrMeasure5(jsonObject.getString("strMeasure5"));
                    recipe.setStrMeasure6(jsonObject.getString("strMeasure6"));
                    recipe.setStrMeasure7(jsonObject.getString("strMeasure7"));
                    recipe.setStrMeasure8(jsonObject.getString("strMeasure8"));
                    recipe.setStrMeasure9(jsonObject.getString("strMeasure9"));
                    
                    
                    ret.add(recipe);
                }
                uiRecipe.processRecipes(ret);
            } catch (JSONException e) {
                Log.e("MealAPI", e.getMessage());
            }
        }, error -> Log.e("response", error.toString())){
            @Override
            protected Map<String, String> getParams(){
                return new HashMap<>();
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

