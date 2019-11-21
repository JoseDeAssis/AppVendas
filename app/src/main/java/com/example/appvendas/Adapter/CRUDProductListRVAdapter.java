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
import androidx.recyclerview.widget.RecyclerView;

import com.example.appvendas.Entity.Product;
import com.example.appvendas.Helpers.Handler.ImageHandler;
import com.example.appvendas.Helpers.Interface.OnProductDeleteListener;
import com.example.appvendas.Helpers.Interface.OnProductDetailsListener;
import com.example.appvendas.Helpers.Interface.OnProductEditListener;
import com.example.appvendas.Helpers.Interface.OnProductIsCheckedListener;
import com.example.appvendas.R;
import com.google.android.material.card.MaterialCardView;

import java.io.IOException;
import java.util.List;

public class CRUDProductListRVAdapter extends RecyclerView.Adapter<CRUDProductListRVAdapter.CRUDProductListRVViewHolder> {

    private final LayoutInflater mInflater;
    private List<Product> productList;
    private ImageHandler imageHandler;
    private OnProductEditListener onProductEditListener;
    private OnProductDeleteListener onProductDeleteListener;

    public class CRUDProductListRVViewHolder extends RecyclerView.ViewHolder {


        private final TextView primaryTextView, priceTextView, groupTextView;
        private final MaterialCardView itemCardView, crudRVProductCheckedCardView;
        private final ImageView imageView;
        private OnProductEditListener onProductEditListener;
        private OnProductDeleteListener onProductDeleteListener;

        private CRUDProductListRVViewHolder(View itemView,
                                            final OnProductEditListener onProductEditListener,
                                            final OnProductDeleteListener onProductDeleteListener) {
            super(itemView);
            primaryTextView = itemView.findViewById(R.id.recyclerViewCRUDPrimaryTxtView);
            priceTextView = itemView.findViewById(R.id.recyclerViewCRUDPriceTxtView);
            groupTextView = itemView.findViewById(R.id.recyclerViewCRUDGroupTxtView);
            itemCardView = itemView.findViewById(R.id.itemRecyclerViewCRUDCardView);
            crudRVProductCheckedCardView = itemView.findViewById(R.id.crudRVProductCheckedCardView);
            imageView = itemView.findViewById(R.id.recyclerViewCRUDImg);

            this.onProductEditListener = onProductEditListener;
            this.onProductDeleteListener = onProductDeleteListener;

            itemCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onProductEditListener.editProduct(productList.get(getAdapterPosition()));
                }
            });

            itemCardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return onProductDeleteListener.deleteProduct(productList.get(getAdapterPosition()));
                }
            });
        }

    }

    public CRUDProductListRVAdapter(Context context, OnProductEditListener onProductEditListener, OnProductDeleteListener onProductDeleteListener) {
        mInflater = LayoutInflater.from(context);
        this.onProductEditListener = onProductEditListener;
        this.onProductDeleteListener = onProductDeleteListener;
    }

    @Override
    public CRUDProductListRVAdapter.CRUDProductListRVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.app_vendas_rv_crud_product_list_item, parent, false);
        imageHandler = new ImageHandler(itemView.getContext());

        return new CRUDProductListRVAdapter.CRUDProductListRVViewHolder(itemView, onProductEditListener, onProductDeleteListener);
    }

    @Override
    public void onBindViewHolder(CRUDProductListRVAdapter.CRUDProductListRVViewHolder holder, int position) {
        if (productList != null) {
            Product current = productList.get(position);
            Bitmap picture = null;

            holder.primaryTextView.setText(current.getProductName());
            holder.priceTextView.setText("R$ " + (String.format("%.2f", current.getProductPrice())));
            holder.groupTextView.setText(current.getProductGroup());

            try {
                picture = imageHandler.getProductPic(current.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (picture != null) {
                holder.imageView.setImageBitmap(picture);
            }
        } else {
            // Covers the case of data not being ready yet.
            holder.primaryTextView.setText("Não há produtos registrados");
        }
    }

    public void setProducts(List<Product> products) {
        productList = products;
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
