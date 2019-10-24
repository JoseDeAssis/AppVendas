package com.example.appvendas.Activitity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.appvendas.Adapter.AppVendasTabAdapter;
import com.example.appvendas.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class AppVendasMainActivity extends AppCompatActivity {

    private Toolbar appVendasToolbar;
    private ViewPager appVendasViewPager;
    private TabLayout appVendasTabLayout;
    private AppVendasTabAdapter appVendasTabAdapter;
    private FloatingActionButton appVendasFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_vendas_main);

        appVendasFAB = findViewById(R.id.appVendasMainFab);

        appVendasToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(appVendasToolbar);

        appVendasTabAdapter = new AppVendasTabAdapter(getSupportFragmentManager());
        appVendasViewPager = findViewById(R.id.mainViewPager);
        appVendasViewPager.setAdapter(appVendasTabAdapter);
        appVendasViewPager.setCurrentItem(1);

        appVendasTabLayout = findViewById(R.id.mainTabLayout);
        appVendasTabLayout.setupWithViewPager(appVendasViewPager, false);

        appVendasFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppVendasMainActivity.this, AppVendasShoppingCart.class));
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
                Intent intent = new Intent(AppVendasMainActivity.this, AppVendasProductCrud.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
