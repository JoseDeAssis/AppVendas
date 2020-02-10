package com.example.appvendas.Activitity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.appvendas.Adapter.AppVendasTabAdapter;
import com.example.appvendas.Fragment.AppVendasFavoriteFragment;
import com.example.appvendas.Fragment.AppVendasHomeFragment;
import com.example.appvendas.Fragment.AppVendasHotProductsFragment;
import com.example.appvendas.Fragment.AppVendasUserFragment;
import com.example.appvendas.Helpers.Singleton.FirebaseSingleton;
import com.example.appvendas.Helpers.Singleton.PreferencesSingleton;
import com.example.appvendas.Model.ProductViewModel;
import com.example.appvendas.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class AppVendasMainActivity extends AppCompatActivity {

    private ProductViewModel appVendasProductViewModel;
    private FirebaseSingleton mFirebaseSingleton;
    private long mLastClickTime = 0;
    private static final int SHOPPING_CART_RESULT_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_vendas_main);

        mFirebaseSingleton = FirebaseSingleton.getInstance();

//        if(!preferencesSingleton.isLoggedIn()){
//            startActivity(new Intent(this, AppVendasLoginActivity.class));
//        } else {
//
//        }

//        FloatingActionButton appVendasFAB = findViewById(R.id.appVendasMainFab);

        Toolbar appVendasToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(appVendasToolbar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        initializeBottomNavigationFragment(new AppVendasHomeFragment());
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

//        appVendasProductViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
//
//        AppVendasTabAdapter appVendasTabAdapter = new AppVendasTabAdapter(getSupportFragmentManager());
//        ViewPager appVendasViewPager = findViewById(R.id.mainViewPager);
//        appVendasViewPager.setAdapter(appVendasTabAdapter);
//        appVendasViewPager.setCurrentItem(1);
//
//        TabLayout appVendasTabLayout = findViewById(R.id.mainTabLayout);
//        appVendasTabLayout.setupWithViewPager(appVendasViewPager, false);
//
//        appVendasFAB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
//                    return;
//                }
//                mLastClickTime = SystemClock.elapsedRealtime();
//
//                Intent intent = new Intent(AppVendasMainActivity.this, AppVendasShoppingCart.class);
//                intent.putExtra("shoppingCartListProducts", appVendasProductViewModel.getProductsList());
//                startActivityForResult(intent, SHOPPING_CART_RESULT_CODE);
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        PreferencesSingleton preferencesSingleton = PreferencesSingleton.getInstance(getApplication());
        if (!preferencesSingleton.isLoggedIn()) {
            Toast.makeText(this, "Seja bem vindo " + mFirebaseSingleton.getCurrentUserName() + "!", Toast.LENGTH_LONG).show();
            preferencesSingleton.userIsLogged();
        }
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

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.bottomNavigationHome:
                            selectedFragment = new AppVendasHomeFragment();
                            break;

                        case R.id.bottomNavigationFavorites:
                            selectedFragment = new AppVendasHotProductsFragment();
                            break;

                        case R.id.bottomNavigationUser:
                            selectedFragment = new AppVendasUserFragment();
                            break;
                    }

                    initializeBottomNavigationFragment(selectedFragment);
                    return true;
                }
            };

    private void initializeBottomNavigationFragment(Fragment selectedFragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentLayoutContainer, selectedFragment).commit();
    }
}
