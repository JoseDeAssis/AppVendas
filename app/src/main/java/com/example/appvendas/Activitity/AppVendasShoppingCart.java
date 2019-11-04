package com.example.appvendas.Activitity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.appvendas.Adapter.ProductGroupListRVAdapter;
import com.example.appvendas.Model.ProductGroupViewModel;
import com.example.appvendas.R;

public class AppVendasShoppingCart extends AppCompatActivity {

    private Toolbar carrinhoToolbar;
    private RecyclerView productGroupRecyclerView;
    private ProductGroupViewModel productGroupViewModel;
    private ProductGroupListRVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_vendas_shopping_cart);

        carrinhoToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(carrinhoToolbar);
        getSupportActionBar().setTitle("carrinho");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productGroupRecyclerView = findViewById(R.id.productGroupRecyclerView);
        adapter = new ProductGroupListRVAdapter(this);

        productGroupRecyclerView.setAdapter(adapter);
        productGroupRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        productGroupViewModel = ViewModelProviders.of(this).get(ProductGroupViewModel.class);
        adapter.setProductGroupList(productGroupViewModel.getAllProducts());
    }
}
