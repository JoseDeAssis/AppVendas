package com.example.appvendas.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appvendas.Entity.Product;
import com.example.appvendas.Holder.ProductListRVViewHolder;
import com.example.appvendas.R;

import java.util.List;

public class ProductListRVAdapter extends RecyclerView.Adapter<ProductListRVAdapter.ProductListRVViewHolder> {

    private final LayoutInflater mInflater;
    private List<Product> productList; // Cached copy of words

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
    }

    @Override
    public ProductListRVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.app_vendas_rv_product_list_item, parent, false);
        return new ProductListRVViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductListRVViewHolder holder, int position) {
        if (productList != null) {
            Product current = productList.get(position);
            holder.textView.setText(current.getProductName());
            holder.checkBox.setActivated(current.isOnSaleProduct());
        } else {
            // Covers the case of data not being ready yet.
            holder.textView.setText("Não há produtos registrados");
        }
    }

    public void setWords(List<Product> words){
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
