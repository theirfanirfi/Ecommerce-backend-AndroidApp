package com.irfanullah.ecommerce.order.Logic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.irfanullah.ecommerce.Libraries.Glib;
import com.irfanullah.ecommerce.Models.Order;
import com.irfanullah.ecommerce.Models.Product;
import com.irfanullah.ecommerce.R;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductView> {
    private static ArrayList<Order> products;
    private static Context context;
    ProductClickListener productClickListener;
    public ProductsAdapter(ArrayList<Order> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_products_custom_row,viewGroup,false);
        return new ProductView(view,productClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductView productView, int i) {
        Order product = products.get(i);

        productView.product_name.setText(product.getPRODUCT_NAME());
        productView.quantityPrice.setText(product.getQUANTITY_ORDERED()+" * $"+product.getPRODUCT_PRICE());

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
        private TextView product_name,quantityPrice;

        public ProductView(@NonNull View itemView, final ProductClickListener productClickListener) {
            super(itemView);
            product_image = itemView.findViewById(R.id.gallery_img);
            product_name = itemView.findViewById(R.id.product_name);
            quantityPrice = itemView.findViewById(R.id.productPrice);

//            product_name.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    productClickListener.onProductClicked(getAdapterPosition(),products.get(getAdapterPosition()));
//                }
//            });
//
//            product_image.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    productClickListener.onProductClicked(getAdapterPosition(),products.get(getAdapterPosition()));
//                }
//            });

        }
    }

    public void notifyAdapter(ArrayList<Order> pds){
        products = pds;
        notifyDataSetChanged();
    }

    public void setOnProductClickListern(ProductClickListener productClickListener){
        this.productClickListener = productClickListener;
    }

    public interface ProductClickListener {
        void onProductClicked(int position, Product product);
        void onProductLongClicked(int position, Product product);
    }
}
