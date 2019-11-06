package com.example.appvendas.Activitity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.appvendas.Adapter.AppVendasTabAdapter;
import com.example.appvendas.Entity.Product;
import com.example.appvendas.Model.ProductViewModel;
import com.example.appvendas.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class AppVendasMainActivity extends AppCompatActivity {

    private Toolbar appVendasToolbar;
    private ViewPager appVendasViewPager;
    private TabLayout appVendasTabLayout;
    private AppVendasTabAdapter appVendasTabAdapter;
    private FloatingActionButton appVendasFAB;
    private ProductViewModel appVendasProductViewModel;
    private static final int SHOPPING_CART_RESULT_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_vendas_main);

        appVendasFAB = findViewById(R.id.appVendasMainFab);

        appVendasToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(appVendasToolbar);

        appVendasProductViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        appVendasTabAdapter = new AppVendasTabAdapter(getSupportFragmentManager());
        appVendasViewPager = findViewById(R.id.mainViewPager);
        appVendasViewPager.setAdapter(appVendasTabAdapter);
        appVendasViewPager.setCurrentItem(1);

        appVendasTabLayout = findViewById(R.id.mainTabLayout);
        appVendasTabLayout.setupWithViewPager(appVendasViewPager, false);

        appVendasFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppVendasMainActivity.this, AppVendasShoppingCart.class);
                intent.putExtra("shoppingCartList", appVendasProductViewModel.getShoppingCartList());
                startActivityForResult(intent, SHOPPING_CART_RESULT_CODE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_vendas_main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.searchIcon:
                break;

            case R.id.addProducts:
                Intent intent = new Intent(AppVendasMainActivity.this, AppVendasProductDetailsCrud.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_OK) {
            switch (resultCode) {
                case SHOPPING_CART_RESULT_CODE:
                    break;
            }
        }
    }
}
