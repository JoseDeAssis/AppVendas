package com.example.appvendas.Activitity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.appvendas.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;

public class AppVendasProductDescription extends AppCompatActivity {

    private Toolbar productDescriptionToolbar;
    private MaterialTextView productDescriptionTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_vendas_product_description);

        productDescriptionToolbar = findViewById(R.id.myToolbar);
        productDescriptionToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.app_vendas_back_icon));
        setSupportActionBar(productDescriptionToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Editar descrição");

        productDescriptionTxtView = findViewById(R.id.productDescriptionTxtView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_vendas_confirm_menu, menu);
        menu.getItem(0).setEnabled(false);
        menu.getItem(0).setIcon(R.drawable.app_vendas_confirm_icon_disabled);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;

            case R.id.confirmIcon:
                new MaterialAlertDialogBuilder(this, R.style.Theme_MaterialComponents_Light_Dialog)
                        .setTitle("Salvar produto?")
                        .setMessage("Ao salvar o produto ele aparecerá em alguma das tabs da tela principal")
                        .setPositiveButton("Accept", /* listener = */ new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(AppVendasProductDescription.this, "Show de bola", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(AppVendasProductDescription.this, "Mó paia man", Toast.LENGTH_SHORT).show();
                            }
                        }).show();

        }

        return super.onOptionsItemSelected(item);
    }
}
