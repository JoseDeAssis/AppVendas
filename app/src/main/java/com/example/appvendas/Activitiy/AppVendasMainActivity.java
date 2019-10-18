package com.example.appvendas.Activitiy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.appvendas.Adapter.AppVendasTabAdapter;
import com.example.appvendas.R;
import com.google.android.material.tabs.TabLayout;

public class AppVendasMainActivity extends AppCompatActivity {

    private Toolbar appVendasToolbar;
    private ViewPager appVendasViewPager;
    private TabLayout appVendasTabLayout;
    private AppVendasTabAdapter appVendasTabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_vendas_main);

        appVendasToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(appVendasToolbar);

        appVendasTabAdapter = new AppVendasTabAdapter(getSupportFragmentManager());
        appVendasViewPager = findViewById(R.id.mainViewPager);
        appVendasViewPager.setAdapter(appVendasTabAdapter);

        appVendasTabLayout = findViewById(R.id.mainTabLayout);
        appVendasTabLayout.setupWithViewPager(appVendasViewPager, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_vendas_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.searchIcon:
                break;

            case R.id.addProducts:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
