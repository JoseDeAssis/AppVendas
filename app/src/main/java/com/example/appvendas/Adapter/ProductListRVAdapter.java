package com.example.appvendas.Adapter;

import android.content.Context;
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

import com.example.appvendas.Entity.Product;
import com.example.appvendas.Helpers.Handler.ImageHandler;
import com.example.appvendas.R;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ProductListRVAdapter extends RecyclerView.Adapter<ProductListRVAdapter.ProductListRVViewHolder> {

    private final LayoutInflater mInflater;
    private List<Product> productList; // Cached copy of words
    private ImageHandler imagesHandler;
    private Context context;

    public class ProductListRVViewHolder extends RecyclerView.ViewHolder  {

        private final TextView textView;
        private final ImageView imageView;
        private final CheckBox checkBox;

        private ProductListRVViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recyclerViewMainText);
            imageView = itemView.findViewById(R.id.recyclerViewImg);
            checkBox = itemView.findViewById(R.id.recyclerViewCheckBox);
        }
    }

    public ProductListRVAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public ProductListRVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.app_vendas_rv_product_list_item, parent, false);
        imagesHandler = new ImageHandler(itemView.getContext());

        return new ProductListRVViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductListRVViewHolder holder, int position) {
        if (productList != null) {
            Product current = productList.get(position);
            RoundedBitmapDrawable picture = null;

            holder.textView.setText(current.getProductName());

            try {
                picture = imagesHandler.getRoundPicture(current.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(picture != null) {
                holder.imageView.setImageDrawable(picture);
            } else {
                //ERRO AQUI!!!!!
                Bitmap src = BitmapFactory.decodeResource(context.getResources(), R.drawable.no_image_icon);
                RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(context.getResources(), src);
                dr.setCornerRadius(Math.max(src.getWidth(), src.getHeight()) / 2.0f);
                holder.imageView.setImageDrawable(dr);
            }
        } else {
            // Covers the case of data not being ready yet.
            holder.textView.setText("Não há produtos registrados");
        }
    }

    public void setProducts(List<Product> words){
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
