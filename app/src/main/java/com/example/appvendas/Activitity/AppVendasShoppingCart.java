package com.example.appvendas.Activitity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.example.appvendas.Adapter.ShoppingCartRVAdapter;
import com.example.appvendas.Entity.Product;
import com.example.appvendas.Helpers.Interface.OnProductDetailsListener;
import com.example.appvendas.Helpers.Interface.OnShoppingCartListener;
import com.example.appvendas.Model.ShoppingCartViewModel;
import com.example.appvendas.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppVendasShoppingCart extends AppCompatActivity implements OnProductDetailsListener, OnShoppingCartListener {

    private Toolbar carrinhoToolbar;
    private RecyclerView shoppingCartRecyclerView;
    private ShoppingCartViewModel shoppingCartViewModel;
    private ShoppingCartRVAdapter shoppingCartAdapter;
    private HashMap<Long, Integer> productQuantities;
    private static final int PRODUCT_DETAIL_RESULT_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_vendas_shopping_cart);

        carrinhoToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(carrinhoToolbar);
        getSupportActionBar().setTitle("carrinho");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        HashMap<Long, Product> shoppingCartList = (HashMap<Long, Product>) getIntent().getSerializableExtra("shoppingCartList");

        shoppingCartRecyclerView = findViewById(R.id.shoppingCartRecyclerView);
        shoppingCartAdapter = new ShoppingCartRVAdapter(this, this, this);

        shoppingCartRecyclerView.setAdapter(shoppingCartAdapter);
        shoppingCartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        shoppingCartViewModel = ViewModelProviders.of(this).get(ShoppingCartViewModel.class);
        shoppingCartViewModel.setShoppingCartList(getShoppingCartProducts(shoppingCartList));
        shoppingCartAdapter.setShoppingCartProducts(getShoppingCartProducts(shoppingCartList));

        productQuantities = shoppingCartViewModel.getProductsQuantities();
        if(productQuantities == null) {
            productQuantities = shoppingCartViewModel.initializeQuantities(shoppingCartList.size());
        }

        shoppingCartAdapter.setProductsQuantities(productQuantities);
        shoppingCartAdapter.setShoppingCartProducts(mapToList(shoppingCartList));
    }

    @Override
    public void getProductDetails(Product product) {
        Intent intent = new Intent(this, AppVendasProductDetail.class);
        intent.putExtra("productName", product.getProductName());
        intent.putExtra("productDescription", product.getProductDescrition());
        intent.putExtra("productId", product.getId());
        intent.putExtra("productPrice", product.getProductPrice());

        startActivityForResult(intent, PRODUCT_DETAIL_RESULT_CODE);
    }

    @Override
    public void deleteItem(Product product) {
        shoppingCartViewModel.deleteProductFromShoppingCart(product);
    }

    public List<Product> getShoppingCartProducts(HashMap<Long, Product> shoppingCartList) {
        List<Product> shoppingCartListReturn = new ArrayList<>();

        for(Map.Entry<Long, Product> map: shoppingCartList.entrySet()) {
            shoppingCartListReturn.add(map.getValue());
        }

        return shoppingCartListReturn;
    }

    @Override
    public void modifyQuantity(final Long productId, View view) {
        PopupMenu popupMenu = new PopupMenu(this, view, Gravity.LEFT, R.attr.popupMenuStyle, R.style.Widget_AppCompat_Light_PopupMenu_Overflow);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.unity1:
                        shoppingCartAdapter.setProductQuantity(productId, 1);
                        shoppingCartAdapter.notifyDataSetChanged();
                        return true;
                    case R.id.unity2:
                        shoppingCartAdapter.setProductQuantity(productId, 2);
                        shoppingCartAdapter.notifyDataSetChanged();
                        return true;
                    case R.id.unity3:
                        shoppingCartAdapter.setProductQuantity(productId, 3);
                        shoppingCartAdapter.notifyDataSetChanged();
                        return true;
                    case R.id.unity4:
                        shoppingCartAdapter.setProductQuantity(productId, 4);
                        shoppingCartAdapter.notifyDataSetChanged();
                        return true;
                    case R.id.unity5:
                        shoppingCartAdapter.setProductQuantity(productId, 5);
                        shoppingCartAdapter.notifyDataSetChanged();
                        return true;
                    case R.id.unity6:
                        shoppingCartAdapter.setProductQuantity(productId, 6);
                        shoppingCartAdapter.notifyDataSetChanged();
                        return true;
                    case R.id.moreUnities:
                        shoppingCartAdapter.notifyDataSetChanged();
                        return true;
                }
                return false;
            }
        });
        popupMenu.getMenuInflater().inflate(R.menu.shopping_cart_quantity_menu, popupMenu.getMenu());
        popupMenu.show();
    }

    private List<Product> mapToList(HashMap<Long, Product> productsHashMap) {
        List<Product> returnList = new ArrayList<>();

        for(Map.Entry<Long, Product> map: productsHashMap.entrySet()) {
            returnList.add(map.getValue());
        }

        return returnList;
    }

}
