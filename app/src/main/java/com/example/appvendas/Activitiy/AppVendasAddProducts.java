package com.example.appvendas.Activitiy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.appvendas.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

public class AppVendasAddProducts extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputEditText newProductTitle, newProductDescription, newProductPrice, newProductGroup;
    private MaterialCardView newProductCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_vendas_add_products);

        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Adicionar Produto");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        newProductTitle = findViewById(R.id.productTitleEdtTxt);
        newProductDescription = findViewById(R.id.productDescriptionEdtTxt);
        newProductPrice = findViewById(R.id.productPriceEdtTxt);
        newProductGroup = findViewById(R.id.productGroupEdtTxt);
        newProductCardView = findViewById(R.id.productImageCardView);
    }

}
