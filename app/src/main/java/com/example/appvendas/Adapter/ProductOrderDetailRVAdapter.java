package com.example.appvendas.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appvendas.Entity.ItemWithProducts;
import com.example.appvendas.Helpers.Handler.ImageHandler;
import com.example.appvendas.R;
import com.google.android.material.textview.MaterialTextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductOrderDetailRVAdapter extends RecyclerView.Adapter<ProductOrderDetailRVAdapter.ProductOrderDetailRVViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private ImageHandler imageHandler;
    private List<ItemWithProducts> itemWithProductsList;

    public class ProductOrderDetailRVViewHolder extends RecyclerView.ViewHolder {

        private final MaterialTextView productName;
        private final MaterialTextView productPrice;
        private final MaterialTextView productQuantity;
        private final ImageView productImage;

        public ProductOrderDetailRVViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.orderProductNameRVTxt);
            productPrice = itemView.findViewById(R.id.orderProductPriceRVTxt);
            productQuantity = itemView.findViewById(R.id.orderProductDetailQuantityTxt);
            productImage = itemView.findViewById(R.id.orderProductDetailImg);
        }
    }

    public ProductOrderDetailRVAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ProductOrderDetailRVAdapter.ProductOrderDetailRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mLayoutInflater.inflate(R.layout.app_vendas_rv_order_details, parent, false);
        imageHandler = new ImageHandler(view.getContext());

        return new ProductOrderDetailRVAdapter.ProductOrderDetailRVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductOrderDetailRVViewHolder holder, int position) {
        if(itemWithProductsList != null) {
            ItemWithProducts itemWithProducts = itemWithProductsList.get(position);

            holder.productName.setText(itemWithProducts.getProduct().getProductName());

            double totalPrice = itemWithProducts.getItem().getItemPrice();
            DecimalFormat df = new DecimalFormat("#.00");
            String textTotalPrice = "R$ " + df.format(totalPrice);
            holder.productPrice.setText(textTotalPrice);

            String totalItems = itemWithProducts.getItem().getQuantity() + "";
            holder.productQuantity.setText(totalItems);

            Bitmap photo = imageHandler.getPhoto(itemWithProducts.getItem().getProductId());

            if(photo != null) {
                holder.productImage.setImageBitmap(photo);
            }

        }
    }

    @Override
    public int getItemCount() {
        if(itemWithProductsList != null)
            return itemWithProductsList.size();
        else
            return 0;
    }

    public void setItemWithProductsList(List<ItemWithProducts> itemWithProducts) {
        itemWithProductsList = itemWithProducts;
        notifyDataSetChanged();
    }

    public boolean isItemsAvailable() {
        for(ItemWithProducts itemWithProduct : itemWithProductsList) {
            if(itemWithProduct.getProduct().getOnAvailableProduct() == 1)
                return true;
        }
        return false;
    }

    public ArrayList<Long> getItemsId() {
        ArrayList<Long> itemsId = new ArrayList<>();

        for(ItemWithProducts itemWithProducts : itemWithProductsList) {
            if(itemWithProducts.getProduct().getOnAvailableProduct() == 1)
                itemsId.add(itemWithProducts.getItem().getProductId());
        }

        return itemsId;
    }

}
