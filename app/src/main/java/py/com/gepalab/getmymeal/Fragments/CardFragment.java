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
        if(counter == 0) {
            lyDescription.setVisibility(View.VISIBLE);
            lytIngedients.setVisibility(View.GONE);
        }else if(counter == 1) {
            lyDescription.setVisibility(View.GONE);
            lytIngedients.setVisibility(View.VISIBLE);
        }
        TextView tvPrep = view.findViewById(R.id.tvPrep);
        TextView tvCategory = view.findViewById(R.id.tvCategory);
        TextView tvArea = view.findViewById(R.id.tvArea);

        TextView tvIng1 = view.findViewById(R.id.tvIng1);
        TextView tvIng2 = view.findViewById(R.id.tvIng2);
        TextView tvIng3 = view.findViewById(R.id.tvIng3);
        TextView tvIng4 = view.findViewById(R.id.tvIng4);
        TextView tvIng5 = view.findViewById(R.id.tvIng5);
        TextView tvIng6 = view.findViewById(R.id.tvIng6);
        TextView tvIng7 = view.findViewById(R.id.tvIng7);
        TextView tvIng8 = view.findViewById(R.id.tvIng8);
        TextView tvIng9 = view.findViewById(R.id.tvIng9);

        TextView tvMeas1 = view.findViewById(R.id.tvMeas1);
        TextView tvMeas2 = view.findViewById(R.id.tvMeas2);
        TextView tvMeas3 = view.findViewById(R.id.tvMeas3);
        TextView tvMeas4 = view.findViewById(R.id.tvMeas4);
        TextView tvMeas5 = view.findViewById(R.id.tvMeas5);
        TextView tvMeas6 = view.findViewById(R.id.tvMeas6);
        TextView tvMeas7 = view.findViewById(R.id.tvMeas7);
        TextView tvMeas8 = view.findViewById(R.id.tvMeas8);
        TextView tvMeas9 = view.findViewById(R.id.tvMeas9);
        MealAPI api = new MealAPI();
        api.getRecipeByID(new UIRecipe() {
            @Override
            public void processRecipes(List<Recipe> recipes) {
                List<String> recipeDescription = new ArrayList<>();
                for(Recipe recipe:recipes){
                    recipeDescription.add(recipe.getStrInstructions());
                    tvPrep.setText(recipe.getStrInstructions());
                    tvArea.setText(recipe.getStrArea());
                    tvCategory.setText(recipe.getStrCategory());

                    tvIng1.setText(recipe.getStrIngredient1());
                    tvIng2.setText(recipe.getStrIngredient2());
                    tvIng3.setText(recipe.getStrIngredient3());
                    tvIng4.setText(recipe.getStrIngredient4());
                    tvIng5.setText(recipe.getStrIngredient5());
                    tvIng6.setText(recipe.getStrIngredient6());
                    tvIng7.setText(recipe.getStrIngredient7());
                    tvIng8.setText(recipe.getStrIngredient8());
                    tvIng9.setText(recipe.getStrIngredient9());

                    tvMeas1.setText("\u2022 "+recipe.getStrMeasure1());
                    tvMeas2.setText("\u2022 "+recipe.getStrMeasure2());
                    tvMeas3.setText("\u2022 "+recipe.getStrMeasure3());
                    tvMeas4.setText("\u2022 "+recipe.getStrMeasure4());
                    tvMeas5.setText("\u2022 "+recipe.getStrMeasure5());
                    tvMeas6.setText("\u2022 "+recipe.getStrMeasure6());
                    tvMeas7.setText("\u2022 "+recipe.getStrMeasure7());
                    tvMeas8.setText("\u2022 "+recipe.getStrMeasure8());
                    tvMeas9.setText("\u2022 "+recipe.getStrMeasure9());

                }
            }

            @Override
            public void processSingleRecipe(Recipe recipe) {
                
            }
        }, paramEntered);
    }
}