package com.example.appvendas.Activitity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.appvendas.Entity.Product;
import com.example.appvendas.Helpers.BottomSheet.ShoppingCartBSComprar;
import com.example.appvendas.Helpers.Handler.ImageHandler;
import com.example.appvendas.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.HashMap;

public class AppVendasProductDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private MaterialCardView productAvailableBuyCardView, productUnavailableBuyCardView;
    private ImageView productDetailsImg;
    private MaterialTextView productDetailsNameTxt, productDetailsPriceTxt, productDetailsDescriptionTxt;
    private MaterialButton productDetailsBuyBtn, productDetailsNotifyBtn;
    private ImageHandler imageHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_vendas_product_detail);

        toolbar = findViewById(R.id.myToolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.app_vendas_back_icon));
        setSupportActionBar(toolbar);

        productUnavailableBuyCardView = findViewById(R.id.productUnavailableBuyCardView);
        productAvailableBuyCardView = findViewById(R.id.productAvailableBuyCardView);

        productDetailsNameTxt = findViewById(R.id.productDetailsNameTxt);
        productDetailsPriceTxt = findViewById(R.id.productDetailsPriceTxt);
        productDetailsDescriptionTxt = findViewById(R.id.productDetailsDescriptionTxt);

        productDetailsImg = findViewById(R.id.productDetailsImg);

        productDetailsBuyBtn = findViewById(R.id.productDetailsBuyBtn);
        productDetailsBuyBtn.setOnClickListener(this);
        productDetailsNotifyBtn = findViewById(R.id.productDetailsUnavailableButton);
        productDetailsNotifyBtn.setOnClickListener(this);

        initializeFields();
    }

    private void initializeFields() {
        productDetailsNameTxt.setText(getIntent().getExtras().getString("productName"));
        productDetailsDescriptionTxt.setText(getIntent().getExtras().getString("productDescription"));

        imageHandler = new ImageHandler(getApplicationContext());
        Bitmap photo = imageHandler.getPhoto(getIntent().getExtras().getLong("productId"));

        productDetailsImg.setImageBitmap(photo);

        if(getIntent().getExtras().getInt("isProductAvailable") == 0) {
            productUnavailableBuyCardView.setVisibility(View.VISIBLE);
            productAvailableBuyCardView.setVisibility(View.GONE);
        } else {
            productDetailsPriceTxt.setText("R$ " + (String.format("%.2f", getIntent().getExtras().getDouble("productPrice"))));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_vendas_product_details_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.shareIcon) {
            if(getIntent().getExtras().getInt("isProductAvailable") == 1) {
                ShoppingCartBSComprar shoppingCartBSComprar = new ShoppingCartBSComprar();
                shoppingCartBSComprar.show(getSupportFragmentManager(), "productDetailsActivity");
            } else {
                Toast.makeText(this, "Necessário escolher um produto disponível!", Toast.LENGTH_SHORT).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.productDetailsBuyBtn:
                if(getIntent().getExtras().getString("parentName").equals(AppVendasShoppingCart.class.toString())) {
                    finish();
                } else {
//                    HashMap<Long, Product> shoppingCartItem = new HashMap<>();
//                    shoppingCartItem.put(product)
                }
                break;

            case R.id.productDetailsUnavailableButton:
                break;
        }
    }
}
