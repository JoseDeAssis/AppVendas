package com.example.appvendas.Activitity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appvendas.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class AppVendasProductDescription extends AppCompatActivity implements TextWatcher {

    private Toolbar productDescriptionToolbar;
    private TextInputEditText productDescriptionTxtView;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_vendas_product_description);

        String productDescription = getIntent().getStringExtra("productDescription");

        productDescriptionToolbar = findViewById(R.id.myToolbar);
        productDescriptionToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.app_vendas_back_icon));

        setSupportActionBar(productDescriptionToolbar);
        getSupportActionBar().setTitle("Editar descrição");

        productDescriptionTxtView = findViewById(R.id.productDescriptionTxtView);

        if (!productDescription.equals("Descrição") && !productDescription.equals("Insira uma descrição válida")) {
            productDescriptionTxtView.setText(productDescription);
        }

        productDescriptionTxtView.requestFocus();
        productDescriptionTxtView.addTextChangedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.app_vendas_confirm_menu, menu);
        menu.getItem(0).setEnabled(false);
        menu.getItem(0).setIcon(R.drawable.app_vendas_confirm_icon_disabled);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.confirmIcon:
                Intent intent = new Intent();
                intent.putExtra("productDescription", productDescriptionTxtView.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (!productDescriptionTxtView.getText().toString().trim().isEmpty()) {
            menu.getItem(0).setIcon(R.drawable.app_vendas_confirm_icon).setEnabled(true);
        } else {
            menu.getItem(0).setIcon(R.drawable.app_vendas_confirm_icon_disabled).setEnabled(false);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (!productDescriptionTxtView.getText().toString().trim().isEmpty()) {

            new MaterialAlertDialogBuilder(AppVendasProductDescription.this, R.style.Theme_MaterialComponents_Light_Dialog)
                    .setTitle("Descartar rascunho?")
                    .setMessage("Todas as mudanças não salvas serão perdidas")
                    .setPositiveButton("Descartar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
        } else {
            finish();
        }
        return true;
    }
}
