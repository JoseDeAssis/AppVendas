package com.example.appvendas.Activitity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.appvendas.R;
import com.google.android.material.textview.MaterialTextView;

public class AppVendasProductDescription extends AppCompatActivity {

    private Toolbar productDescriptionToolbar;
    private MaterialTextView productDescriptionTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_vendas_product_description);

        productDescriptionToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(productDescriptionToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Editar descrição");

        productDescriptionTxtView = findViewById(R.id.productDescriptionTxtView);
    }
}
