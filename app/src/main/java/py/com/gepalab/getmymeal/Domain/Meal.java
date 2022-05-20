package py.com.gepalab.getmymeal.Domain;

import android.graphics.Bitmap;

public class Meal {
    private String strMeal;
    private String strMealThumb;
    private String idMeal;
    private Bitmap bitmap;
    private String strMealImage;

    public String getStrMeal() {
        return strMeal;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }

    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(String idMeal) {
        this.idMeal = idMeal;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getStrMealImage() {
        return strMealImage;
    }

    public void setStrMealImage(String strMealImage) {
        this.strMealImage = strMealImage;
    }
}
