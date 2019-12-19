package com.example.appvendas.Activitity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.appvendas.Adapter.ProductGroupListRVAdapter;
import com.example.appvendas.Adapter.ProductListRVAdapter;
import com.example.appvendas.Entity.Product;
import com.example.appvendas.Entity.ProductGroup;
import com.example.appvendas.Model.ProductGroupViewModel;
import com.example.appvendas.Model.ProductViewModel;
import com.example.appvendas.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class AppVendasProductGroup extends AppCompatActivity {

    private ProductGroupViewModel productGroupViewModel;
    private ProductGroupListRVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_vendas_product_group);

        Toolbar toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.app_vendas_back_icon));
        getSupportActionBar().setTitle("Selecione a categoria");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    finish();
            }
        });

        String groupSelected = getIntent().getExtras().getString("groupSelected");

        RecyclerView productGroupRecyclerView = findViewById(R.id.productGroupRecyclerView);
        adapter = new ProductGroupListRVAdapter(this);

        productGroupRecyclerView.setAdapter(adapter);
        productGroupRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        productGroupViewModel = ViewModelProviders.of(this).get(ProductGroupViewModel.class);
        adapter.setProductGroupList(productGroupViewModel.getAllProducts());

        if(!groupSelected.equals("Categoria*") || !groupSelected.equals("Selecione uma categoria válida")) {
            for(int i = 0; i < productGroupViewModel.getAllProducts().length; i++) {
                if(productGroupViewModel.getAllProducts()[i].getGroupName().equals(groupSelected)){
                    productGroupViewModel.getAllProducts()[i].setSelected(true);
                    adapter.notifyItemChanged(i);
                }
            }
        }

        adapter.setOnItemClickListener(new ProductGroupListRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                selectItem(position);
            }
        });

    }

    public void selectItem(int position) {
        ProductGroup[] productGroupList = productGroupViewModel.getAllProducts();
        for (int i = 0; i < productGroupList.length; i++) {
            if(productGroupList[i].equals(productGroupList[position])){
                productGroupList[i].setSelected(true);
                adapter.notifyItemChanged(i);
                Intent intent = new Intent();
                intent.putExtra("groupName", productGroupList[i].getGroupName());
                setResult(RESULT_OK, intent);
                finish();

            } else if(productGroupList[i].isSelected()) {
                productGroupList[i].setSelected(false);
                adapter.notifyItemChanged(i);
            }
        }
    }

}
