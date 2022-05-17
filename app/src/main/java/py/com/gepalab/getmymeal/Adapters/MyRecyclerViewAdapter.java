package py.com.gepalab.getmymeal.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import py.com.gepalab.getmymeal.MealAPI.MealAPI;
import py.com.gepalab.getmymeal.R;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<Bitmap> mViewImages;
    private List<String> mText;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, List<Bitmap> images, List<String> texts) {
        this.mInflater = LayoutInflater.from(context);
        this.mViewImages = images;
        this.mText = texts;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bitmap images = mViewImages.get(position);
        String texto = mText.get(position);
        holder.myView.setImageBitmap(images);
        holder.myTextView.setText(texto);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mText.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView myView;
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myView = itemView.findViewById(R.id.ivCategories);
            myTextView = itemView.findViewById(R.id.tvTextData);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mText.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);

    }
}