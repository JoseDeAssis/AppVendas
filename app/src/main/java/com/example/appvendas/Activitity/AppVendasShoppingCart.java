package com.example.appvendas.Activitity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;

import com.example.appvendas.Adapter.ShoppingCartRVAdapter;
import com.example.appvendas.Entity.Product;
import com.example.appvendas.Helpers.BottomSheet.ShoppingCartBSComprar;
import com.example.appvendas.Helpers.Dialog.ShoppingCartQuantityDialog;
import com.example.appvendas.Helpers.Interface.OnProductDetailsListener;
import com.example.appvendas.Helpers.Interface.OnShoppingCartListener;
import com.example.appvendas.Model.ShoppingCartViewModel;
import com.example.appvendas.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*TODO
    implementar banco histórico + bottom sheet de envio da compra
*/
public class AppVendasShoppingCart extends AppCompatActivity implements OnProductDetailsListener,
        OnShoppingCartListener,
        ShoppingCartQuantityDialog.shoppingCartQuantityDialogListener {

    private Toolbar carrinhoToolbar;
    private RecyclerView shoppingCartRecyclerView;
    private ShoppingCartViewModel shoppingCartViewModel;
    private ShoppingCartRVAdapter shoppingCartAdapter;
    private HashMap<Long, Integer> productQuantities;
    private MaterialButton shoppingCartBuyButton, shoppingCartHomeButton;
    private MaterialTextView shoppingCartTotal;
    private MaterialCardView shoppingCartCardView, shoppingCartEmptyCartCardView;
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

        shoppingCartCardView = findViewById(R.id.shoppingCartCardView);
        shoppingCartEmptyCartCardView = findViewById(R.id.shoppingCartEmptyCartCardView);

        shoppingCartRecyclerView = findViewById(R.id.shoppingCartRecyclerView);
        shoppingCartAdapter = new ShoppingCartRVAdapter(this, this, this);

        shoppingCartRecyclerView.setAdapter(shoppingCartAdapter);
        shoppingCartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        shoppingCartViewModel = ViewModelProviders.of(this).get(ShoppingCartViewModel.class);
        shoppingCartViewModel.setShoppingCartList(getShoppingCartProducts(shoppingCartList));
        shoppingCartAdapter.setShoppingCartProducts(getShoppingCartProducts(shoppingCartList));

        productQuantities = shoppingCartViewModel.getProductsQuantities();
        if(productQuantities == null || productQuantities.size() == 0) {
            productQuantities = shoppingCartViewModel.initializeQuantities();
        }

        shoppingCartAdapter.setProductsQuantities(productQuantities);
        shoppingCartAdapter.setShoppingCartProducts(mapToList(shoppingCartList));

        shoppingCartTotal = findViewById(R.id.shoppingCartTotalPriceTxt);
        this.modifyTotalPrice();

        shoppingCartBuyButton = findViewById(R.id.shoppingCartBuyBtn);
        shoppingCartBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShoppingCartBSComprar shoppingCartBSComprar = new ShoppingCartBSComprar();
                shoppingCartBSComprar.show(getSupportFragmentManager(), "shoppingCartBSComprar");
            }
        });

        shoppingCartHomeButton = findViewById(R.id.shoppingCartHomeButton);
        shoppingCartHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(shoppingCartList.size() == 0 || shoppingCartList == null) {
            shoppingCartEmptyCartCardView.setVisibility(View.VISIBLE);
            shoppingCartCardView.setVisibility(View.GONE);
        } else {
            shoppingCartCardView.setVisibility(View.VISIBLE);
            shoppingCartEmptyCartCardView.setVisibility(View.GONE);

        }
    }

    @Override
    public void getProductDetails(Product product) {
        Intent intent = new Intent(this, AppVendasProductDetail.class);
        intent.putExtra("productName", product.getProductName());
        intent.putExtra("productDescription", product.getProductDescrition());
        intent.putExtra("productId", product.getId());
        intent.putExtra("productPrice", product.getProductPrice());
        intent.putExtra("productGroup", product.getProductGroup());
        intent.putExtra("productOnSale", product.getOnSaleProduct());

        startActivityForResult(intent, PRODUCT_DETAIL_RESULT_CODE);
    }

    @Override
    public void deleteItem(Product product) {
        shoppingCartViewModel.deleteProductFromShoppingCart(product);
        shoppingCartAdapter.deleteProductFromShoppingCart(product);
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
        Context wrapper = new ContextThemeWrapper(this, R.style.app_vendas_popup_menu_style);
        PopupMenu popupMenu = new PopupMenu(wrapper, view);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.unity1:
                        shoppingCartAdapter.setProductQuantity(productId, 1);
                        return true;
                    case R.id.unity2:
                        shoppingCartAdapter.setProductQuantity(productId, 2);
                        return true;
                    case R.id.unity3:
                        shoppingCartAdapter.setProductQuantity(productId, 3);
                        return true;
                    case R.id.unity4:
                        shoppingCartAdapter.setProductQuantity(productId, 4);
                        return true;
                    case R.id.unity5:
                        shoppingCartAdapter.setProductQuantity(productId, 5);
                        return true;
                    case R.id.unity6:
                        shoppingCartAdapter.setProductQuantity(productId, 6);
                        return true;
                    case R.id.moreUnities:
                        openDialog(productId);
                        return true;
                }
                return false;
            }
        });
        popupMenu.getMenuInflater().inflate(R.menu.shopping_cart_quantity_menu, popupMenu.getMenu());
        popupMenu.show();
    }

    @Override
    public void modifyTotalPrice() {
        shoppingCartTotal.setText("R$ " + (String.format("%.2f", shoppingCartAdapter.getShoppingCartTotalPrice())));
    }

    private List<Product> mapToList(HashMap<Long, Product> productsHashMap) {
        List<Product> returnList = new ArrayList<>();

        for(Map.Entry<Long, Product> map: productsHashMap.entrySet()) {
            returnList.add(map.getValue());
        }

        return returnList;
    }

    private void openDialog(Long productId) {
        ShoppingCartQuantityDialog shoppingCartQuantityDialog = new ShoppingCartQuantityDialog();
        Bundle bundle = new Bundle();
        bundle.putLong("productId", productId);
        shoppingCartQuantityDialog.setArguments(bundle);
        shoppingCartQuantityDialog.show(getSupportFragmentManager(), "shopping cart dialog");
    }

    @Override
    public void applyQuantity(int quantity, Long productId) {
        shoppingCartAdapter.setProductQuantity(productId, quantity);
    }

}
