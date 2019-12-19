package com.example.appvendas.Activitity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.appvendas.Adapter.ProductListRVAdapter;
import com.example.appvendas.Entity.Product;
import com.example.appvendas.Helpers.Interface.OnProductDetailsListener;
import com.example.appvendas.Helpers.Interface.OnProductIsCheckedListener;
import com.example.appvendas.Model.ProductViewModel;
import com.example.appvendas.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AppVendasFilteredProductsActivity extends AppCompatActivity implements OnProductDetailsListener, OnProductIsCheckedListener {

    private ProductViewModel appVendasProductViewModel;
    private long mLastClickTime = 0;
    private static final int SHOPPING_CART_RESULT_CODE = 1000;
    private static final int PRODUCT_DETAIL_RESULT_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_vendas_filtered_products);

        Toolbar toolbar = findViewById(R.id.myToolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.app_vendas_back_icon));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getExtras().getString("filterWord"));

        appVendasProductViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        RecyclerView appVendasProdutosRecyclerView = findViewById(R.id.productsFilteredRecyclerView);
        final ProductListRVAdapter adapter = new ProductListRVAdapter(this, this, this);

        appVendasProdutosRecyclerView.setAdapter(adapter);
        appVendasProdutosRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        String filter = getIntent().getExtras().getString("filterWord").trim() + "%";

        appVendasProductViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        appVendasProductViewModel.getProductFiltered(filter).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                adapter.setProducts(products);
            }
        });

        FloatingActionButton appVendasFAB = findViewById(R.id.appVendasProductsFilteredCrudFAB);
        appVendasFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                Intent intent = new Intent(AppVendasFilteredProductsActivity.this, AppVendasShoppingCart.class);
                intent.putExtra("shoppingCartListProducts", appVendasProductViewModel.getProductsList());
                startActivityForResult(intent, SHOPPING_CART_RESULT_CODE);
            }
        });
    }

    @Override
    public void getProductDetails(Product product) {
        Intent intent = new Intent(this, AppVendasProductDetailsActivity.class);
        intent.putExtra("productId", product.getId());
        intent.putExtra("parentName", this.getClass().toString());

        startActivityForResult(intent, PRODUCT_DETAIL_RESULT_CODE);
    }

    @Override
    public void setProductChecked(Product product, boolean isChecked) {
        appVendasProductViewModel.addProductToShoppingCart(product, isChecked);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_vendas_search_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.searchIconSearchMenu);
        searchItem.setTitle(getIntent().getExtras().getString("filterWord"));

        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(AppVendasFilteredProductsActivity.this, AppVendasFilteredProductsActivity.class);
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

}
