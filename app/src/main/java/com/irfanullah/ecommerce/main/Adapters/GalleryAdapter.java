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
import com.irfanullah.ecommerce.Libraries.RetroLib;
import com.irfanullah.ecommerce.Models.Category;
import com.irfanullah.ecommerce.Models.Gallery;
import com.irfanullah.ecommerce.R;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryView> {

    private Context context;
    private ArrayList<Gallery> gallery;
    GalleryImageClickListener galleryImageClickListener;

    public GalleryAdapter(Context context, ArrayList<Gallery> gallery) {
        this.context = context;
        this.gallery = gallery;
    }

    @NonNull
    @Override
    public GalleryView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.gallery_custom_row,viewGroup,false);
        return new GalleryView(view,context,galleryImageClickListener,this.gallery);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryView galleryView, int i) {
        Gallery gallery = this.gallery.get(i);
        galleryView.image_title.setText(gallery.getIMAGE_TITLE());

        if(!gallery.getIMAGE_NAME().isEmpty()){
            String image_url = RetroLib.ASSET_URL+"gallery/"+gallery.getIMAGE_NAME();
            Glib.loadImage(context,image_url).into(galleryView.image);
        }
    }

    @Override
    public int getItemCount() {
        return this.gallery.size();
    }

    public static class GalleryView extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView image_title;

        public GalleryView(@NonNull View itemView, Context context, final GalleryImageClickListener galleryImageClickListener, final ArrayList<Gallery> gallery) {
            super(itemView);
            image = itemView.findViewById(R.id.gallery_img);
            image_title = itemView.findViewById(R.id.image_title);

//            cat_image.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                   // categoryClickListener.onCategoryClickListener(getAdapterPosition(),gallery.get(getAdapterPosition()));
//                }
//            });

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    galleryImageClickListener.onImageClickListener(getAdapterPosition(),gallery.get(getAdapterPosition()));
                }
            });
        }
    }

    public void notifyAdapter(ArrayList<Gallery> gallery){
        this.gallery = gallery;
        notifyDataSetChanged();
    }

    public interface GalleryImageClickListener {
        void onImageClickListener(int position, Gallery gallery);
    }

    public void setOnImageClickListener(GalleryImageClickListener clickListener){
        this.galleryImageClickListener = clickListener;
    }
}
