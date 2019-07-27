package com.irfanullah.ecommerce.main.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.irfanullah.ecommerce.Libraries.Glib;
import com.irfanullah.ecommerce.Models.Category;
import com.irfanullah.ecommerce.R;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryView> {

    private Context context;
    private ArrayList<Category> categories;
    CategoryClickListener categoryClickListener;

    public CategoriesAdapter(Context context, ArrayList<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.categories_custom_row,viewGroup,false);
        return new CategoryView(view,context,categoryClickListener,this.categories);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryView categoryView, int i) {
        Category cat = categories.get(i);
        categoryView.cat_title.setText(cat.getCAT_TITLE());

        if(!cat.getCAT_IMAGE().isEmpty()){
            Glib.loadImage(context,cat.getCAT_IMAGE()).into(categoryView.cat_image);
        }
    }

    @Override
    public int getItemCount() {
        return this.categories.size();
    }

    public static class CategoryView extends RecyclerView.ViewHolder {
        private ImageView cat_image;
        private TextView cat_title;

        public CategoryView(@NonNull View itemView, Context context, final CategoryClickListener categoryClickListener, final ArrayList<Category> categories) {
            super(itemView);
            cat_image = itemView.findViewById(R.id.gallery_img);
            cat_title = itemView.findViewById(R.id.image_title);

            cat_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    categoryClickListener.onCategoryClickListener(getAdapterPosition(),categories.get(getAdapterPosition()));
                }
            });
        }
    }

    public void notifyAdapter(ArrayList<Category> categories){
        this.categories = categories;
        notifyDataSetChanged();
    }

    public interface CategoryClickListener {
        void onCategoryClickListener(int position,Category cat);
    }

    public void setOnCategoryClickListener(CategoryClickListener clickListener){
        this.categoryClickListener = clickListener;
    }
}
