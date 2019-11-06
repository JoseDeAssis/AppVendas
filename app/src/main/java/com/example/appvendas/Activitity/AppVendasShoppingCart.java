package com.example.appvendas.Activitity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.appvendas.Adapter.ShoppingCartRVAdapter;
import com.example.appvendas.Entity.Product;
import com.example.appvendas.Helpers.Interface.OnProductDetailsListener;
import com.example.appvendas.Helpers.Interface.OnShoppingCartDeleteItemListener;
import com.example.appvendas.Model.ShoppingCartViewModel;
import com.example.appvendas.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppVendasShoppingCart extends AppCompatActivity implements OnProductDetailsListener, OnShoppingCartDeleteItemListener {

    private Toolbar carrinhoToolbar;
    private RecyclerView shoppingCartRecyclerView;
    private ShoppingCartViewModel shoppingCartViewModel;
    private ShoppingCartRVAdapter adapter;
    private static final int PRODUCT_DETAIL_RESULT_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_vendas_shopping_cart);

        carrinhoToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(carrinhoToolbar);
        getSupportActionBar().setTitle("carrinho");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        HashMap<Long, Product> shoppingCartList = (HashMap<Long, Product>) getIntent().getSerializableExtra("shoppingCartList");

        shoppingCartRecyclerView = findViewById(R.id.shoppingCartRecyclerView);
        adapter = new ShoppingCartRVAdapter(this, this, this);

        shoppingCartRecyclerView.setAdapter(adapter);
        shoppingCartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        shoppingCartViewModel = ViewModelProviders.of(this).get(ShoppingCartViewModel.class);
        adapter.initializeShoppingCartProducts(getShoppingCartProducts(shoppingCartList));
    }

    @Override
    public void getProductDetails(Product product) {
        Intent intent = new Intent(this, AppVendasProductDetail.class);
        intent.putExtra("productName", product.getProductName());
        intent.putExtra("productDescription", product.getProductDescrition());
        intent.putExtra("productId", product.getId());
        intent.putExtra("productPrice", product.getProductPrice());

        startActivityForResult(intent, PRODUCT_DETAIL_RESULT_CODE);
    }

    @Override
    public void deleteItem(Product product) {
        shoppingCartViewModel.deleteProductFromShoppingCart(product);
    }

    public List<Product> getShoppingCartProducts(HashMap<Long, Product> shoppingCartList) {
        List<Product> shoppingCartListReturn = new ArrayList<>();

        for(Map.Entry<Long, Product> map: shoppingCartList.entrySet()) {
            shoppingCartListReturn.add(map.getValue());
        }

        return shoppingCartListReturn;
    }
}
