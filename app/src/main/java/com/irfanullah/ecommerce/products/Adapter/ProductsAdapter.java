package com.irfanullah.ecommerce.products.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.irfanullah.ecommerce.Libraries.Glib;
import com.irfanullah.ecommerce.Models.Product;
import com.irfanullah.ecommerce.R;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductView> {
    private static ArrayList<Product> products;
    private static Context context;
    ProductClickListener productClickListener;
    public ProductsAdapter(ArrayList<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.products_custom_row,viewGroup,false);
        return new ProductView(view,productClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductView productView, int i) {
        Product product = products.get(i);

        productView.product_name.setText(product.getPRODUCT_NAME());
        productView.quantity.setText("Quan: "+product.getPRODUCT_QUANTITY()+" - Sold: "+product.getPRODUCT_SOLD());

        if(!product.getPRODUCT_IMAGE().isEmpty()){
            Glib.loadImage(context,product.getPRODUCT_IMAGE()).into(productView.product_image);
        }
    }

    @Override
    public int getItemCount() {
        return this.products.size();
    }

    public static class ProductView extends RecyclerView.ViewHolder {

        private ImageView product_image;
        private TextView product_name,quantity;

        public ProductView(@NonNull View itemView,ProductClickListener productClickListener) {
            super(itemView);
            product_image = itemView.findViewById(R.id.product_img);
            product_name = itemView.findViewById(R.id.product_name);
            quantity = itemView.findViewById(R.id.quantityAndSold);
        }
    }

    public void notifyAdapter(ArrayList<Product> pds){
        products = pds;
    }

    public void setOnProductClickListern(ProductClickListener productClickListener){
        this.productClickListener = productClickListener;
    }

    public interface ProductClickListener {
        void onProductClicked(int position,Product product);
    }
}
