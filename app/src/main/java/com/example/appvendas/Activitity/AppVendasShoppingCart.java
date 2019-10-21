package com.example.appvendas.Activitity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.appvendas.R;

public class AppVendasShoppingCart extends AppCompatActivity {

    private Toolbar carrinhoToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_vendas_shopping_cart);

        carrinhoToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(carrinhoToolbar);
        getSupportActionBar().setTitle("carrinho");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
