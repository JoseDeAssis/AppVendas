package com.example.appvendas.Activitity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.example.appvendas.Adapter.AppVendasTabAdapter;
import com.example.appvendas.Entity.Product;
import com.example.appvendas.Model.ProductViewModel;
import com.example.appvendas.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AppVendasMainActivity extends AppCompatActivity {

    private ProductViewModel appVendasProductViewModel;
    private long mLastClickTime = 0;
    private static final int SHOPPING_CART_RESULT_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_vendas_main);

        FloatingActionButton appVendasFAB = findViewById(R.id.appVendasMainFab);

        Toolbar appVendasToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(appVendasToolbar);

        appVendasProductViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        AppVendasTabAdapter appVendasTabAdapter = new AppVendasTabAdapter(getSupportFragmentManager());
        ViewPager appVendasViewPager = findViewById(R.id.mainViewPager);
        appVendasViewPager.setAdapter(appVendasTabAdapter);
        appVendasViewPager.setCurrentItem(1);

        TabLayout appVendasTabLayout = findViewById(R.id.mainTabLayout);
        appVendasTabLayout.setupWithViewPager(appVendasViewPager, false);

        appVendasFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                Intent intent = new Intent(AppVendasMainActivity.this, AppVendasShoppingCart.class);
                intent.putExtra("shoppingCartListProducts", appVendasProductViewModel.getProductsList());
                startActivityForResult(intent, SHOPPING_CART_RESULT_CODE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_vendas_main_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.searchIcon);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(AppVendasMainActivity.this, AppVendasFilteredProductsActivity.class);
                intent.putExtra("filterWord", query);
                startActivity(intent);

                searchItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.addProducts) {
            Intent intent = new Intent(AppVendasMainActivity.this, AppVendasProductDetailsCrud.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == SHOPPING_CART_RESULT_CODE) {
            recreate();
        }
    }
}
