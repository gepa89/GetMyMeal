package py.com.gepalab.getmymeal.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import py.com.gepalab.getmymeal.Activities.UIRecipe;
import py.com.gepalab.getmymeal.Domain.Recipe;
import py.com.gepalab.getmymeal.MealAPI.MealAPI;
import py.com.gepalab.getmymeal.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardFragment extends Fragment {
    private static final String ARG_COUNT = "param1";
    private Integer counter;
    private String paramEntered;
    public CardFragment() {
        // Required empty public constructor
    }
    public static CardFragment newInstance(Integer counter, String param) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COUNT, counter);
        args.putString("meal",param);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Log.e("args::",getArguments().toString());
            counter = getArguments().getInt(ARG_COUNT);
            paramEntered = getArguments().getString("meal");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card, container, false);
    }
    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout lyDescription = view.findViewById(R.id.lyDescription);
        LinearLayout lytIngedients = view.findViewById(R.id.lytIngedients);
        LinearLayout lyMeasures = view.findViewById(R.id.lyMeasures);
        if(counter == 0) {
            lyDescription.setVisibility(View.VISIBLE);
            lytIngedients.setVisibility(View.GONE);
            lyMeasures.setVisibility(View.GONE);
        }else if(counter == 1) {
            lyDescription.setVisibility(View.GONE);
            lytIngedients.setVisibility(View.VISIBLE);
            lyMeasures.setVisibility(View.GONE);
        }else if(counter == 2) {
            lyDescription.setVisibility(View.GONE);
            lytIngedients.setVisibility(View.GONE);
            lyMeasures.setVisibility(View.VISIBLE);
        }
        TextView tvPrep = view.findViewById(R.id.tvPrep);
        MealAPI api = new MealAPI();
        api.getRecipeByID(new UIRecipe() {
            @Override
            public void processRecipe(List<Recipe> recipes) {
                ArrayList<String> recipeDescription = new ArrayList<>();
                ArrayList<String> mealImages = new ArrayList<>();
                ArrayList<String> mealImagesView = new ArrayList<>();
                for(Recipe recipe:recipes){
                    recipeDescription.add(recipe.strInstructions);
                    tvPrep.setText(recipe.strInstructions);

                }
            }
        }, paramEntered);
    }
}