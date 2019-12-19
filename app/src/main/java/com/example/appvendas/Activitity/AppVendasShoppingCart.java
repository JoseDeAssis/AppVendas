package com.example.appvendas.Activitity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;

import com.example.appvendas.Adapter.ShoppingCartRVAdapter;
import com.example.appvendas.Entity.Item;
import com.example.appvendas.Entity.Order;
import com.example.appvendas.Entity.Product;
import com.example.appvendas.Helpers.Dialog.ShoppingCartQuantityDialog;
import com.example.appvendas.Helpers.Interface.EventListener;
import com.example.appvendas.Helpers.Interface.OnProductDetailsListener;
import com.example.appvendas.Helpers.Interface.OnShoppingCartListener;
import com.example.appvendas.Helpers.Singleton.EventSingleton;
import com.example.appvendas.Model.ShoppingCartViewModel;
import com.example.appvendas.R;
import com.example.appvendas.Repository.ItemRepository;
import com.example.appvendas.Repository.OrderRepository;
import com.example.appvendas.Repository.ProductRepository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AppVendasShoppingCart extends AppCompatActivity implements OnProductDetailsListener,
        OnShoppingCartListener,
        ShoppingCartQuantityDialog.shoppingCartQuantityDialogListener {

    private ShoppingCartViewModel shoppingCartViewModel;
    private ShoppingCartRVAdapter shoppingCartAdapter;
    private HashMap<Long, Integer> productQuantities;
    private List<Item> itemList;
    private MaterialTextView shoppingCartTotal;
    private MaterialCardView shoppingCartCardView, shoppingCartEmptyCartCardView;
    private long mLastClickTime = 0;
    private static final int SHARE_RESULT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_vendas_shopping_cart);

        Toolbar toolbar = findViewById(R.id.myToolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.app_vendas_back_icon));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("carrinho");

        shoppingCartCardView = findViewById(R.id.shoppingCartCardView);
        shoppingCartEmptyCartCardView = findViewById(R.id.shoppingCartEmptyCartCardView);

        RecyclerView shoppingCartRecyclerView = findViewById(R.id.shoppingCartRecyclerView);
        shoppingCartAdapter = new ShoppingCartRVAdapter(this, this, this);

        shoppingCartRecyclerView.setAdapter(shoppingCartAdapter);
        shoppingCartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        shoppingCartViewModel = ViewModelProviders.of(this).get(ShoppingCartViewModel.class);

        ProductRepository productRepository = new ProductRepository(getApplication());

        if (getIntent().getExtras().getLong("productId", 0) != 0) {
            productRepository.getProduct(getIntent().getExtras().getLong("productId")).observe(this, new Observer<Product>() {
                @Override
                public void onChanged(Product product) {
                    List<Product> productList = new ArrayList<>();
                    productList.add(product);
                    initializeShoppingCart(productList);
                }
            });
        } else {
            productRepository.getProductsById((ArrayList<Long>) getIntent().getSerializableExtra("shoppingCartListProducts")).observe(this, new Observer<List<Product>>() {
                @Override
                public void onChanged(List<Product> products) {
                    initializeShoppingCart(products);
                }
            });
        }

        shoppingCartTotal = findViewById(R.id.shoppingCartTotalPriceTxt);

        MaterialButton shoppingCartBuyButton = findViewById(R.id.shoppingCartBuyBtn);
        shoppingCartBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                shareIntent(shoppingCartViewModel.getProductsDetails());
            }
        });

        MaterialButton shoppingCartHomeButton = findViewById(R.id.shoppingCartHomeButton);
        shoppingCartHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void initializeShoppingCart(List<Product> products) {
        shoppingCartViewModel.setShoppingCartList(products);
        shoppingCartAdapter.setShoppingCartProducts(products);

        productQuantities = shoppingCartViewModel.getProductsQuantities();
        if (productQuantities == null || productQuantities.size() == 0) {
            productQuantities = shoppingCartViewModel.initializeQuantities();
        }

        shoppingCartAdapter.setProductsQuantities(productQuantities);
        modifyTotalPrice();
        isShoppingCartEmpty();
    }

    public void isShoppingCartEmpty() {
        if (shoppingCartViewModel.getShoppingCartList().size() == 0 || shoppingCartViewModel.getShoppingCartList() == null) {
            shoppingCartEmptyCartCardView.setVisibility(View.VISIBLE);
            shoppingCartCardView.setVisibility(View.GONE);
        } else {
            shoppingCartCardView.setVisibility(View.VISIBLE);
            shoppingCartEmptyCartCardView.setVisibility(View.GONE);
        }
    }

    @Override
    public void getProductDetails(Product product) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        Intent intent = new Intent(this, AppVendasProductDetailsActivity.class);
        intent.putExtra("productId", product.getId());
        intent.putExtra("parentName", this.getClass().toString());

        startActivity(intent);
    }

    @Override
    public void deleteItem(Product product) {
        shoppingCartViewModel.deleteProductFromShoppingCart(product);
        shoppingCartAdapter.deleteProductFromShoppingCart(product);
        isShoppingCartEmpty();
    }

    @Override
    public void modifyQuantity(final Long productId, View view) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

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
        double totalShoppingCart = shoppingCartAdapter.getShoppingCartTotalPrice();
        DecimalFormat df = new DecimalFormat("#.00");
        String textTotalShoppingCart = "R$ " + df.format(totalShoppingCart);
        shoppingCartTotal.setText(textTotalShoppingCart);
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

    public void sendOrder() {
        Order order = createNewOrder();

        OrderRepository orderRepository = new OrderRepository(getApplication());
        orderRepository.insert(order);
        getOrder();

        final EventSingleton eventSingleton = EventSingleton.getInstance();

        eventSingleton.registerEvent(new EventListener() {
            @Override
            public void done(Long aLong) {
                ItemRepository itemRepository = new ItemRepository(getApplication());

                for (Item item : itemList) {
                    item.setOrderId(aLong);
                    itemRepository.insert(item);
                }

                setResult(RESULT_OK, new Intent());
                finish();
            }
        });
    }

    private void getOrder() {
        itemList = new ArrayList<>();
        for (Product product : shoppingCartViewModel.getShoppingCartList()) {
            Item item = new Item();
            item.setProductId(product.getId());
            item.setQuantity(productQuantities.get(product.getId()));
            item.setItemPrice(product.getProductPrice());
            itemList.add(item);
        }
    }

    public Order createNewOrder() {
        Order order = new Order();
        Date currentTime = Calendar.getInstance().getTime();
        order.setOrder_date(currentTime);

        return order;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void shareIntent(String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivityForResult(shareIntent, SHARE_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SHARE_RESULT) {
            sendOrder();
        }
    }
}