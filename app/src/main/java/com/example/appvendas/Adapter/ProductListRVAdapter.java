package com.example.appvendas.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appvendas.Activitity.AppVendasMainActivity;
import com.example.appvendas.Entity.Product;
import com.example.appvendas.Helpers.Handler.ImageHandler;
import com.example.appvendas.Helpers.Interface.OnProductDetailsListener;
import com.example.appvendas.Helpers.Interface.OnProductIsCheckedListener;
import com.example.appvendas.R;
import com.google.android.material.card.MaterialCardView;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

public class ProductListRVAdapter extends RecyclerView.Adapter<ProductListRVAdapter.ProductListRVViewHolder> {

    private final LayoutInflater mInflater;
    private List<Product> productList;
    private ImageHandler imageHandler;
    private OnProductDetailsListener mOnProductDetailsListener;
    private OnProductIsCheckedListener mOnProductIsCheckedListener;
    private long mLastClickTime = 0;

    public class ProductListRVViewHolder extends RecyclerView.ViewHolder {

        private final TextView primaryTextView, priceTextView;
        private final ImageView imageView;
        private final CheckBox checkBox;

        private ProductListRVViewHolder(View itemView,
                                        final OnProductDetailsListener onProductDetailsListener,
                                        final OnProductIsCheckedListener onProductIsCheckedListener) {
            super(itemView);
            MaterialCardView productListCardView = itemView.findViewById(R.id.productListCardView);
            primaryTextView = itemView.findViewById(R.id.recyclerViewPrimaryTxtView);
            priceTextView = itemView.findViewById(R.id.recyclerViewPriceTxtView);
            imageView = itemView.findViewById(R.id.recyclerViewImg);
            checkBox = itemView.findViewById(R.id.recyclerViewCheckBox);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Product product = productList.get(getAdapterPosition());
                    onProductIsCheckedListener.setProductChecked(product, checkBox.isChecked());
                }
            });

            productListCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();

                    onProductDetailsListener.getProductDetails(productList.get(getAdapterPosition()));
                }
            });
        }

    }

    public ProductListRVAdapter(Context context, OnProductDetailsListener mOnProductDetailsListener, OnProductIsCheckedListener mOnProductIsCheckedListener) {
        mInflater = LayoutInflater.from(context);
        this.mOnProductDetailsListener = mOnProductDetailsListener;
        this.mOnProductIsCheckedListener = mOnProductIsCheckedListener;
    }

    @Override
    public ProductListRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.app_vendas_rv_product_list_item, parent, false);
        imageHandler = new ImageHandler(itemView.getContext());

        return new ProductListRVViewHolder(itemView, mOnProductDetailsListener, mOnProductIsCheckedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListRVViewHolder holder, int position) {
        if (productList != null) {
            Product current = productList.get(position);
            RoundedBitmapDrawable picture = null;

            holder.primaryTextView.setText(current.getProductName());

            if (productList.get(position).getOnAvailableProduct() == 1) {
                double productPrice = current.getProductPrice();
                DecimalFormat df = new DecimalFormat("#.00");
                String priceText = "R$ " + df.format(productPrice);
                holder.priceTextView.setText(priceText);
            } else {
                holder.priceTextView.setText("Não disponível.");
                holder.priceTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                holder.checkBox.setVisibility(View.GONE);
            }

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
