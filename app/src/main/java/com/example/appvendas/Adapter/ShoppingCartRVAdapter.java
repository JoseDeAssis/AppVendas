package com.example.appvendas.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appvendas.Entity.Product;
import com.example.appvendas.Helpers.Handler.ImageHandler;
import com.example.appvendas.Helpers.Interface.OnProductDetailsListener;
import com.example.appvendas.Helpers.Interface.OnShoppingCartListener;
import com.example.appvendas.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShoppingCartRVAdapter extends RecyclerView.Adapter<ShoppingCartRVAdapter.ShoppingCartRVViewHolder> {

    private final LayoutInflater mInflater;
    private List<Product> shoppingCartList;
    private HashMap<Long, Integer> productsQuantities;
    private Double shoppingCartTotalPrice;
    private ImageHandler imageHandler;
    private OnProductDetailsListener mOnProductDetailsListener;
    private OnShoppingCartListener mOnShoppingCartListener;

    public class ShoppingCartRVViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleTextView, priceTextView, quantityTextView;
        private final ImageView imageView;
        private final MaterialCardView materialProductCardView, materialQuantityCardView;
        private final MaterialButton deleteBtn;
        private OnProductDetailsListener onProductDetailsListener;
        private OnShoppingCartListener onShoppingCartListener;
        private OnShoppingCartListener mOnShoppingCartListener;

        public ShoppingCartRVViewHolder(@NonNull View itemView,
                                        final OnProductDetailsListener onProductDetailsListener,
                                        final OnShoppingCartListener onShoppingCartListener) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.shoppingCartRVTitleTxtView);
            priceTextView = itemView.findViewById(R.id.shoppingCartRVPriceTxtView);
            quantityTextView = itemView.findViewById(R.id.modifyQuantityTextView);
            imageView = itemView.findViewById(R.id.shoppingCartRVImgView);
            materialProductCardView = itemView.findViewById(R.id.shoppingCartProductCardView);
            materialQuantityCardView = itemView.findViewById(R.id.shoppingCartQuantityCardView);
            deleteBtn = itemView.findViewById(R.id.shoppingCartRVBtn);

            this.onProductDetailsListener = onProductDetailsListener;
            this.onShoppingCartListener = onShoppingCartListener;
            this.mOnShoppingCartListener = onShoppingCartListener;

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
    public ShoppingCartRVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.app_vendas_rv_shopping_cart, parent, false);
        imageHandler = new ImageHandler(itemView.getContext());

        return new ShoppingCartRVAdapter.ShoppingCartRVViewHolder(itemView, mOnProductDetailsListener, mOnShoppingCartListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingCartRVViewHolder holder, int position) {
        if (shoppingCartList != null) {
            Product current = shoppingCartList.get(position);
            RoundedBitmapDrawable picture = null;

            if(productsQuantities != null && productsQuantities.size() > 0) {
                holder.quantityTextView.setText(productsQuantities.get(current.getId()) + "un.");
                holder.priceTextView.setText("R$ " + (String.format("%.2f",
                        (current.getProductPrice() * productsQuantities.get(current.getId())))));
            } else {
                holder.quantityTextView.setText("1un.");
                holder.priceTextView.setText("R$ " + (String.format("%.2f", current.getProductPrice())));
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
        this.shoppingCartTotalPrice = 0.0;
        for(Product product: shoppingCartList) {
            if(productsQuantities != null &&productsQuantities.size() > 0)
                this.shoppingCartTotalPrice += (product.getProductPrice() * productsQuantities.get(product.getId()));
            else
                this.shoppingCartTotalPrice += product.getProductPrice();
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
        if(shoppingCartList.size() != 0)
            return shoppingCartList.size();
        else return 0;
    }

}
