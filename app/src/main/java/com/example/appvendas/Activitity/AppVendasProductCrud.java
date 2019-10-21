package com.example.appvendas.Activitity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.appvendas.Adapter.ProductListRVAdapter;
import com.example.appvendas.Entity.Product;
import com.example.appvendas.Model.ProductViewModel;
import com.example.appvendas.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class AppVendasProductCrud extends AppCompatActivity {

    private RecyclerView appVendasProdutosCrudRecyclerView;
    private ProductViewModel appVendasProdutosCrudViewModel;
    private Toolbar toolbar;
    private ActionMode mActionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_vendas_product_crud);

        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Produtos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appVendasProdutosCrudRecyclerView = findViewById(R.id.produtosTabRecyclerView);
        final ProductListRVAdapter adapter = new ProductListRVAdapter(this);

        appVendasProdutosCrudRecyclerView.setAdapter(adapter);
        appVendasProdutosCrudRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        appVendasProdutosCrudViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        appVendasProdutosCrudViewModel.getAllProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                adapter.setProducts(products);
            }
        });

        appVendasProdutosCrudRecyclerView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(mActionMode != null) {
                    return false;
                }
                return true;
            }
        });
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            actionMode.getMenuInflater().inflate(R.menu.app_vendas_contextual_actionbar, menu);
            actionMode.setTitle("Excluir");

            return false;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {

            switch (menuItem.getItemId()) {
                case R.id.delete_icon:
                    new MaterialAlertDialogBuilder(AppVendasProductCrud.this, R.style.Theme_MaterialComponents_Light_Dialog)
                            .setTitle("Salvar produto?")
                            .setMessage("Ao salvar o produto ele aparecerá em alguma das tabs da tela principal")
                            .setPositiveButton("Accept", /* listener = */ new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(AppVendasProductCrud.this, "Show de bola", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(AppVendasProductCrud.this, "Mó paia man", Toast.LENGTH_SHORT).show();
                                }
                            }).show();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            mActionMode = null;
        }
    };
}
