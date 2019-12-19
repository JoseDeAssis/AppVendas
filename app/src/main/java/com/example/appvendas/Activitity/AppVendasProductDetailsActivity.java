package com.example.appvendas.Activitity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;

import com.example.appvendas.Entity.Product;
import com.example.appvendas.Helpers.Handler.ImageHandler;
import com.example.appvendas.R;
import com.example.appvendas.Repository.ProductRepository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.text.DecimalFormat;

public class AppVendasProductDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialCardView productAvailableBuyCardView, productUnavailableBuyCardView;
    private ImageView productDetailsImg;
    private MaterialTextView productDetailsNameTxt, productDetailsPriceTxt, productDetailsDescriptionTxt;
    private String textProductPrice;
    private Product thisProduct;
    private long mLastClickTime = 0;
    private static final int SHOPPING_CART_RESULT_OK = 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_vendas_product_detail);

        Toolbar toolbar = findViewById(R.id.myToolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.app_vendas_back_icon));
        setSupportActionBar(toolbar);

        productUnavailableBuyCardView = findViewById(R.id.productUnavailableBuyCardView);
        productAvailableBuyCardView = findViewById(R.id.productAvailableBuyCardView);

        productDetailsNameTxt = findViewById(R.id.productDetailsNameTxt);
        productDetailsPriceTxt = findViewById(R.id.productDetailsPriceTxt);
        productDetailsDescriptionTxt = findViewById(R.id.productDetailsDescriptionTxt);

        productDetailsImg = findViewById(R.id.productDetailsImg);

        MaterialButton productDetailsBuyBtn = findViewById(R.id.productDetailsBuyBtn);
        productDetailsBuyBtn.setOnClickListener(this);

        MaterialButton productDetailsNotifyBtn = findViewById(R.id.productDetailsUnavailableButton);
        productDetailsNotifyBtn.setOnClickListener(this);

        ProductRepository productRepository = new ProductRepository(getApplication());
        productRepository.getProduct(getIntent().getExtras().getLong("productId")).observe(this, new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                thisProduct = product;
                initializeFields();
            }
        });
    }

    private void initializeFields() {
        productDetailsNameTxt.setText(thisProduct.getProductName());
        productDetailsDescriptionTxt.setText(thisProduct.getProductDescrition());

        ImageHandler imageHandler = new ImageHandler(getApplicationContext());
        Bitmap photo = imageHandler.getPhoto(thisProduct.getId());

        productDetailsImg.setImageBitmap(photo);

        if (thisProduct.getOnAvailableProduct() == 0) {
            productUnavailableBuyCardView.setVisibility(View.VISIBLE);
            productAvailableBuyCardView.setVisibility(View.GONE);
        } else {
            double productPrice = thisProduct.getProductPrice();
            DecimalFormat df = new DecimalFormat("#.00");
            textProductPrice = "R$ " + df.format(productPrice);
            productDetailsPriceTxt.setText(textProductPrice);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_vendas_product_details_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.shareIcon) {
            if (thisProduct.getOnAvailableProduct() == 1) {
                String text = thisProduct.getProductDescrition() + " por " + textProductPrice;
                shareIntent(text);
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
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        switch (view.getId()) {
            case R.id.productDetailsBuyBtn:
                if (getIntent().getExtras().getString("parentName").equals(AppVendasShoppingCart.class.toString())) {
                    finish();
                } else {
                    Intent intent = new Intent(this, AppVendasShoppingCart.class);
                    intent.putExtra("productId", thisProduct.getId());
                    startActivityForResult(intent, SHOPPING_CART_RESULT_OK);
                }
                break;

            case R.id.productDetailsUnavailableButton:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SHOPPING_CART_RESULT_OK) {
            finish();
        }
    }

    public void shareIntent(String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }
}
