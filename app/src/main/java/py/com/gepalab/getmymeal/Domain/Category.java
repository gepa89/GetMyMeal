package py.com.gepalab.getmymeal.Domain;

import android.graphics.Bitmap;

public class Category{
    private String idCategory;
    private String strCategory;
    private String strCategoryThumb;
    private String strCategoryImage;
    private String strCategoryDescription;
    private Bitmap bitmap;

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }

    public String getStrCategoryThumb() {
        return strCategoryThumb;
    }

    public void setStrCategoryThumb(String strCategoryThumb) {
        this.strCategoryThumb = strCategoryThumb;
    }

    public String getStrCategoryImage() {
        return strCategoryImage;
    }

    public void setStrCategoryImage(String strCategoryImage) {
        this.strCategoryImage = strCategoryImage;
    }

    public String getStrCategoryDescription() {
        return strCategoryDescription;
    }

    public void setStrCategoryDescription(String strCategoryDescription) {
        this.strCategoryDescription = strCategoryDescription;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}

