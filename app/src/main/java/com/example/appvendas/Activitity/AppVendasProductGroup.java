package com.example.appvendas.Activitity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.appvendas.Adapter.ProductGroupListRVAdapter;
import com.example.appvendas.Adapter.ProductListRVAdapter;
import com.example.appvendas.Entity.Product;
import com.example.appvendas.Model.ProductGroupViewModel;
import com.example.appvendas.Model.ProductViewModel;
import com.example.appvendas.R;

import java.util.List;

public class AppVendasProductGroup extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView productGroupRecyclerView;
    private ProductGroupViewModel productGroupViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_vendas_product_group);

        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Selecione a categoria");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productGroupRecyclerView = findViewById(R.id.productGroupRecyclerView);
        final ProductGroupListRVAdapter adapter = new ProductGroupListRVAdapter(this);

        productGroupRecyclerView.setAdapter(adapter);
        productGroupRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        productGroupViewModel = ViewModelProviders.of(this).get(ProductGroupViewModel.class);
        adapter.setProductGroupList(productGroupViewModel.getAllProducts());

    }
}
