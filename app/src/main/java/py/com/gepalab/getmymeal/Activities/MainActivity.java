package py.com.gepalab.getmymeal.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request.Method;
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

import py.com.gepalab.getmymeal.Controller.AppController;
import py.com.gepalab.getmymeal.Data.AppConfig;
import py.com.gepalab.getmymeal.Domain.Category;
import py.com.gepalab.getmymeal.MealAPI.MealAPI;
import py.com.gepalab.getmymeal.R;

import static py.com.gepalab.getmymeal.Data.AppConfig.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(":::::::", "onCreate");
        setContentView(R.layout.activity_main);
        loadCategories();


    }

    private void loadCategories() {
        Log.e(":::::::", "Cargue sus categorias");
        MealAPI api = new MealAPI("url");
        api.listCategory(new UITask() {
            @Override
            public void processCategory(List<Category> categories) {
                for (Category category:categories) {
                    Log.e(":::::::", "Estoy de star");
                    final ImageButton imageButton = new ImageButton(MainActivity.this);
                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.e("BOTON PAPA", "el boton ");
                        }
                    });
                    imageButton.setImageBitmap(category.bitmap);
                    imageButton.setPadding(5,5,5,5);
                    imageButton.setVisibility(imageButton.VISIBLE);
                    LinearLayout ll = (LinearLayout)findViewById(R.id.ll);
                    //LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    ll.addView(imageButton);
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }
}