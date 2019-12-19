package com.example.appvendas.Adapter;

import android.content.Context;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appvendas.Activitity.AppVendasMainActivity;
import com.example.appvendas.Entity.Product;
import com.example.appvendas.Helpers.Handler.ImageHandler;
import com.example.appvendas.Helpers.Interface.OnProductDetailsListener;
import com.example.appvendas.Helpers.Interface.OnShoppingCartListener;
import com.example.appvendas.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShoppingCartRVAdapter extends RecyclerView.Adapter<ShoppingCartRVAdapter.ShoppingCartRVViewHolder> {

    private final LayoutInflater mInflater;
    private List<Product> shoppingCartList;
    private HashMap<Long, Integer> productsQuantities;
    private ImageHandler imageHandler;
    private OnProductDetailsListener mOnProductDetailsListener;
    private OnShoppingCartListener mOnShoppingCartListener;

    public class ShoppingCartRVViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleTextView, priceTextView, quantityTextView;
        private final ImageView imageView;
        private long mLastClickTime = 0;

        private ShoppingCartRVViewHolder(@NonNull View itemView,
                                         final OnProductDetailsListener onProductDetailsListener,
                                         final OnShoppingCartListener onShoppingCartListener) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.shoppingCartRVTitleTxtView);
            priceTextView = itemView.findViewById(R.id.shoppingCartRVPriceTxtView);
            quantityTextView = itemView.findViewById(R.id.modifyQuantityTextView);
            imageView = itemView.findViewById(R.id.shoppingCartRVImgView);
            MaterialCardView materialProductCardView = itemView.findViewById(R.id.shoppingCartProductCardView);
            MaterialCardView materialQuantityCardView = itemView.findViewById(R.id.shoppingCartQuantityCardView);
            MaterialButton deleteBtn = itemView.findViewById(R.id.shoppingCartRVBtn);

            materialProductCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Product product = shoppingCartList.get(getAdapterPosition());
                    onProductDetailsListener.getProductDetails(product);
                }
            });

            materialQuantityCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Product product = shoppingCartList.get(getAdapterPosition());
                    onShoppingCartListener.modifyQuantity(product.getId(), view);
                }
            });

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Product product = shoppingCartList.get(getAdapterPosition());
                    onShoppingCartListener.deleteItem(product);
                }
            });

        }
    }

    public ShoppingCartRVAdapter(Context context, OnProductDetailsListener mOnProductDetailsListener, OnShoppingCartListener mOnShoppingCartListener) {
        mInflater = LayoutInflater.from(context);
        this.shoppingCartList = new ArrayList<>();
        this.productsQuantities = new HashMap<>();
        this.mOnProductDetailsListener = mOnProductDetailsListener;
        this.mOnShoppingCartListener = mOnShoppingCartListener;
    }

    @NonNull
    @Override
    public ShoppingCartRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.app_vendas_rv_shopping_cart, parent, false);
        imageHandler = new ImageHandler(itemView.getContext());

        return new ShoppingCartRVAdapter.ShoppingCartRVViewHolder(itemView, mOnProductDetailsListener, mOnShoppingCartListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingCartRVViewHolder holder, int position) {
        if (shoppingCartList != null) {
            Product current = shoppingCartList.get(position);
            RoundedBitmapDrawable picture = null;

            DecimalFormat df = new DecimalFormat("#.00");
            if (productsQuantities != null && productsQuantities.size() > 0) {
                String productQuantity = productsQuantities.get(current.getId()) + "un.";
                holder.quantityTextView.setText(productQuantity);

                double productPrice = current.getProductPrice() * productsQuantities.get(current.getId());
                String priceText = "R$ " + df.format(productPrice);
                holder.priceTextView.setText(priceText);
            } else {
                holder.quantityTextView.setText("1un.");

                double productPrice = current.getProductPrice();
                String priceText = "R$ " + df.format(productPrice);
                holder.priceTextView.setText(priceText);
            }

            holder.titleTextView.setText(current.getProductName());

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
            holder.titleTextView.setText("Não há produtos registrados");
        }
    }

    public void setShoppingCartProducts(List<Product> products) {
        shoppingCartList = products;
    }

    public void setProductsQuantities(HashMap<Long, Integer> productsQuantities) {
        this.productsQuantities = productsQuantities;
    }

    public void setProductQuantity(Long id, int count) {
        this.productsQuantities.put(id, count);
        mOnShoppingCartListener.modifyTotalPrice();
        notifyDataSetChanged();
    }

    public Double getShoppingCartTotalPrice() {
        double shoppingCartTotalPrice = 0.0;
        for (Product product : shoppingCartList) {
            if (productsQuantities != null && productsQuantities.size() > 0)
                shoppingCartTotalPrice += (product.getProductPrice() * productsQuantities.get(product.getId()));
            else
                shoppingCartTotalPrice += product.getProductPrice();
        }

        return shoppingCartTotalPrice;
    }

    public void deleteProductFromShoppingCart(Product product) {
        shoppingCartList.remove(product);
        productsQuantities.remove(product.getId());
        mOnShoppingCartListener.modifyTotalPrice();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (shoppingCartList.size() != 0)
            return shoppingCartList.size();
        else return 0;
    }

}
