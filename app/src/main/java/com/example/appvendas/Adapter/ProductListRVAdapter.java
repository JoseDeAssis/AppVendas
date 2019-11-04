package com.example.appvendas.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appvendas.Activitity.AppVendasProductCrud;
import com.example.appvendas.Entity.Product;
import com.example.appvendas.Fragment.AppVendasDestaquesTab;
import com.example.appvendas.Helpers.Handler.ImageHandler;
import com.example.appvendas.Helpers.Interface.OnProductListener;
import com.example.appvendas.R;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ProductListRVAdapter extends RecyclerView.Adapter<ProductListRVAdapter.ProductListRVViewHolder> {

    private final LayoutInflater mInflater;
    private List<Product> productList; // Cached copy of words
    private ImageHandler imagesHandler;
    private Context context;
    private OnProductListener mOnProductListener;

    public class ProductListRVViewHolder extends RecyclerView.ViewHolder {


        private final TextView primaryTextView, priceTextView;
        private final ImageView imageView;
        private final CheckBox checkBox;
        private OnProductListener onProductListener;

        private ProductListRVViewHolder(View itemView, final OnProductListener onProductListener) {
            super(itemView);
            primaryTextView = itemView.findViewById(R.id.recyclerViewPrimaryTxtView);
            priceTextView = itemView.findViewById(R.id.recyclerViewPriceTxtView);
            imageView = itemView.findViewById(R.id.recyclerViewImg);
            checkBox = itemView.findViewById(R.id.recyclerViewCheckBox);

            this.onProductListener = onProductListener;

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Product product = productList.get(getAdapterPosition());
                    onProductListener.setProductChecked(product, checkBox.isChecked());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onProductListener.getProductDetails(productList.get(getAdapterPosition()));
                }
            });
        }

    }

    public ProductListRVAdapter(Context context, OnProductListener mOnProductListener) {
        mInflater = LayoutInflater.from(context);
        this.mOnProductListener = mOnProductListener;
        this.context = context;
    }

    @Override
    public ProductListRVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.app_vendas_rv_product_list_item, parent, false);
        imagesHandler = new ImageHandler(itemView.getContext());

        return new ProductListRVViewHolder(itemView, mOnProductListener);
    }

    @Override
    public void onBindViewHolder(ProductListRVViewHolder holder, int position) {
        if (productList != null) {
            Product current = productList.get(position);
            RoundedBitmapDrawable picture = null;

            holder.primaryTextView.setText(current.getProductName());
            holder.priceTextView.setText("R$ " + (String.format("%.2f", current.getProductPrice())));

            try {
                picture = imagesHandler.getRoundPicture(current.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (picture != null) {
                holder.imageView.setImageDrawable(picture);
                holder.imageView.setMaxWidth(5);
                holder.imageView.setMaxHeight(5);
            }
        } else {
            // Covers the case of data not being ready yet.
            holder.primaryTextView.setText("Não há produtos registrados");
        }
    }

    public void setProducts(List<Product> words) {
        productList = words;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (productList != null)
            return productList.size();
        else return 0;
    }
}
