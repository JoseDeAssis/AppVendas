package com.example.appvendas.Activitity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import com.example.appvendas.Adapter.ProductOrderDetailRVAdapter;
import com.example.appvendas.Entity.ItemWithProducts;
import com.example.appvendas.Entity.OrderWithItemsAndProducts;
import com.example.appvendas.R;
import com.example.appvendas.Repository.OrderWithItemsAndProductsRepository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class AppVendasOrderDetailsActivity extends AppCompatActivity {

    private ProductOrderDetailRVAdapter productOrderDetailRVAdapter;
    private MaterialTextView orderDetailDateTxt, orderDetailTotalPriceTxt, orderDetailQtdProductsTxt;
    private MaterialCardView orderDetailBuyAgainCardView;
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_vendas_order_details);

        Toolbar toolbar = findViewById(R.id.myToolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.app_vendas_back_icon));
        setSupportActionBar(toolbar);

        orderDetailDateTxt = findViewById(R.id.orderDetailsDate);
        orderDetailTotalPriceTxt = findViewById(R.id.orderTotalPrice);
        orderDetailQtdProductsTxt = findViewById(R.id.orderDetailsQuantity);

        orderDetailBuyAgainCardView = findViewById(R.id.orderDetailBuyAgainCardView);
        RecyclerView orderDetailsRecyclerView = findViewById(R.id.orderProductDetailRecyclerView);
        productOrderDetailRVAdapter = new ProductOrderDetailRVAdapter(this);

        orderDetailsRecyclerView.setAdapter(productOrderDetailRVAdapter);
        orderDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        OrderWithItemsAndProductsRepository orderWithItemsAndProductsRepository = new OrderWithItemsAndProductsRepository(getApplication());
        orderWithItemsAndProductsRepository.getItemSummaryByOrderId(getIntent().getExtras().getLong("orderId")).observe(this, new Observer<OrderWithItemsAndProducts>() {
            @Override
            public void onChanged(OrderWithItemsAndProducts orderWithItemsAndProducts) {
                productOrderDetailRVAdapter.setItemWithProductsList(orderWithItemsAndProducts.getItemWithProductsList());
                initializeFields(orderWithItemsAndProducts);
            }
        });


    }

    public void initializeFields(OrderWithItemsAndProducts orderWithItemsAndProducts) {
        double totalShoppingCart = 0.0;
        int totalItems = 0;

        for (ItemWithProducts itemWithProducts : orderWithItemsAndProducts.getItemWithProductsList()) {
            totalShoppingCart += (itemWithProducts.getItem().getItemPrice() * itemWithProducts.getItem().getQuantity());
            totalItems += itemWithProducts.getItem().getQuantity();
        }

        DecimalFormat df = new DecimalFormat("#.00");
        String textTotalShoppingCart = "R$ " + df.format(totalShoppingCart);
        String textTotalItems = "(" + totalItems + " itens)";
        orderDetailTotalPriceTxt.setText(textTotalShoppingCart);
        orderDetailQtdProductsTxt.setText(textTotalItems);


        DateFormat dayFormat = new SimpleDateFormat("dd");
        DateFormat monthFormat = new SimpleDateFormat("MMM");
        DateFormat yearFormat = new SimpleDateFormat("yyyy");
        String orderDate = dayFormat.format(orderWithItemsAndProducts.getOrder().getOrder_date()) + " "
                + monthFormat.format(orderWithItemsAndProducts.getOrder().getOrder_date()) + " "
                + yearFormat.format(orderWithItemsAndProducts.getOrder().getOrder_date());

        orderDetailDateTxt.setText(orderDate);

        if (!productOrderDetailRVAdapter.isItemsAvailable()) {
            orderDetailBuyAgainCardView.setVisibility(View.GONE);
        } else {
            MaterialButton orderDetailBuyBtn = findViewById(R.id.orderDetailBuyAgainBtn);
            orderDetailBuyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();

                    Intent intent = new Intent(getApplicationContext(), AppVendasShoppingCart.class);
                    intent.putExtra("shoppingCartListProducts", productOrderDetailRVAdapter.getItemsId());

                    startActivity(intent);
                    finish();

                }
            });
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
