package com.example.appvendas.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appvendas.Activitity.AppVendasMainActivity;
import com.example.appvendas.Entity.ItemWithOrder;
import com.example.appvendas.Entity.Order;
import com.example.appvendas.Entity.Product;
import com.example.appvendas.Helpers.Handler.ImageHandler;
import com.example.appvendas.Helpers.Interface.OnOrderDetailsListener;
import com.example.appvendas.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class OrderListRVAdapter extends RecyclerView.Adapter<OrderListRVAdapter.OrderListRVViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private OnOrderDetailsListener onOrderDetailsListener;
    private List<ItemWithOrder> itemsList;
    private ImageHandler imageHandler;
    private long mLastClickTime = 0;


    public class OrderListRVViewHolder extends RecyclerView.ViewHolder {

        private final MaterialTextView orderProductNameTextView, orderDateTextView;
        private final ImageView orderProductImg;

        private OrderListRVViewHolder(View itemView, final OnOrderDetailsListener onOrderDetailsListener) {
            super(itemView);

//            final MaterialCardView orderCardView = itemView.findViewById(R.id.orderProductCardView);
            final MaterialCardView moreOptionsOrderCardView = itemView.findViewById(R.id.moreOptionsOrderCardView);
            orderProductNameTextView = itemView.findViewById(R.id.orderProductNameTextView);
            orderDateTextView = itemView.findViewById(R.id.orderProductTextView);
            orderProductImg = itemView.findViewById(R.id.orderProductImg);

            moreOptionsOrderCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();

                    onOrderDetailsListener.getOrderOptionsDetails(itemsList.get(getAdapterPosition()), view);
                }
            });
        }
    }

    public OrderListRVAdapter(Context context, OnOrderDetailsListener onOrderDetailsListener) {
        mLayoutInflater = LayoutInflater.from(context);
        this.onOrderDetailsListener = onOrderDetailsListener;
    }

    @Override
    public OrderListRVAdapter.OrderListRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.app_vendas_rv_orders, parent, false);
        imageHandler = new ImageHandler(itemView.getContext());

        return new OrderListRVAdapter.OrderListRVViewHolder(itemView, onOrderDetailsListener);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListRVViewHolder holder, int position) {
        if (itemsList != null) {
            Product currentProduct = itemsList.get(position).getProduct();
            Order currentOrder = itemsList.get(position).getOrder();
            DateFormat dayFormat = new SimpleDateFormat("dd");
            DateFormat monthFormat = new SimpleDateFormat("MMM");
            DateFormat yearFormat = new SimpleDateFormat("yyyy");

            holder.orderProductNameTextView.setText(currentProduct.getProductName());

            String orderDate = "pedido em " +
                    dayFormat.format(currentOrder.getOrder_date()) + " de " +
                    monthFormat.format(currentOrder.getOrder_date()) + " de " +
                    yearFormat.format(currentOrder.getOrder_date());
            holder.orderDateTextView.setText(orderDate);

            Bitmap productPic = imageHandler.getProductPic(currentProduct.getId());

            if (productPic != null)
                holder.orderProductImg.setImageBitmap(productPic);
        }
    }

    public void setItemsList(List<ItemWithOrder> itemWithOrderList) {
        itemsList = itemWithOrderList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (itemsList != null)
            return itemsList.size();
        else
            return 0;
    }
}
