package py.com.gepalab.getmymeal.Activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import py.com.gepalab.getmymeal.MealAPI.MealAPI;
import py.com.gepalab.getmymeal.R;

public class RecipeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Bundle mBundle = getIntent().getExtras();
        Log.e("BUNDLE!!!!!!!!!!!!!", mBundle.getString("recipe"));
        MealAPI api = new MealAPI();
    }

}
