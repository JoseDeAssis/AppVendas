package com.example.appvendas.Adapter;

import android.content.Context;
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
import com.example.appvendas.Helpers.Interface.OnProductDetailsListener;
import com.example.appvendas.Helpers.Interface.OnProductIsCheckedListener;
import com.example.appvendas.R;

import java.io.IOException;
import java.util.List;

public class ProductListRVAdapter extends RecyclerView.Adapter<ProductListRVAdapter.ProductListRVViewHolder> {

    private final LayoutInflater mInflater;
    private List<Product> productList;
    private ImageHandler imageHandler;
    private Context context;
    private OnProductDetailsListener mOnProductDetailsListener;
    private OnProductIsCheckedListener mOnProductIsCheckedListener;

    public class ProductListRVViewHolder extends RecyclerView.ViewHolder {


        private final TextView primaryTextView, priceTextView;
        private final ImageView imageView;
        private final CheckBox checkBox;
        private OnProductDetailsListener onProductDetailsListener;
        private OnProductIsCheckedListener onProductIsCheckedListener;

        private ProductListRVViewHolder(View itemView,
                                        final OnProductDetailsListener onProductDetailsListener,
                                        final OnProductIsCheckedListener onProductIsCheckedListener) {
            super(itemView);
            primaryTextView = itemView.findViewById(R.id.recyclerViewPrimaryTxtView);
            priceTextView = itemView.findViewById(R.id.recyclerViewPriceTxtView);
            imageView = itemView.findViewById(R.id.recyclerViewImg);
            checkBox = itemView.findViewById(R.id.recyclerViewCheckBox);

            this.onProductIsCheckedListener = onProductIsCheckedListener;
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Product product = productList.get(getAdapterPosition());
                    onProductIsCheckedListener.setProductChecked(product, checkBox.isChecked());
                }
            });

            this.onProductDetailsListener = onProductDetailsListener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onProductDetailsListener.getProductDetails(productList.get(getAdapterPosition()));
                }
            });
        }

    }

    public ProductListRVAdapter(Context context, OnProductDetailsListener mOnProductDetailsListener, OnProductIsCheckedListener mOnProductIsCheckedListener) {
        mInflater = LayoutInflater.from(context);
        this.mOnProductDetailsListener = mOnProductDetailsListener;
        this.mOnProductIsCheckedListener = mOnProductIsCheckedListener;
        this.context = context;
    }

    @Override
    public ProductListRVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.app_vendas_rv_product_list_item, parent, false);
        imageHandler = new ImageHandler(itemView.getContext());

        return new ProductListRVViewHolder(itemView, mOnProductDetailsListener, mOnProductIsCheckedListener);
    }

    @Override
    public void onBindViewHolder(ProductListRVViewHolder holder, int position) {
        if (productList != null) {
            Product current = productList.get(position);
            RoundedBitmapDrawable picture = null;

            holder.primaryTextView.setText(current.getProductName());
            holder.priceTextView.setText("R$ " + (String.format("%.2f", current.getProductPrice())));

            try {
                picture = imageHandler.getRoundPicture(current.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (picture != null) {
                holder.imageView.setImageDrawable(picture);
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
