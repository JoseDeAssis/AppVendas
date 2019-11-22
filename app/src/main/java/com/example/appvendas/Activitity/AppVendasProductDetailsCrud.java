package com.example.appvendas.Activitity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.appvendas.Adapter.CRUDProductListRVAdapter;
import com.example.appvendas.Adapter.ProductListRVAdapter;
import com.example.appvendas.Entity.Product;
import com.example.appvendas.Helpers.Handler.ImageHandler;
import com.example.appvendas.Helpers.Interface.OnProductDeleteListener;
import com.example.appvendas.Helpers.Interface.OnProductDetailsListener;
import com.example.appvendas.Helpers.Interface.OnProductEditListener;
import com.example.appvendas.Helpers.Singleton.EventSingleton;
import com.example.appvendas.Helpers.Interface.EventListener;
import com.example.appvendas.Model.ProductViewModel;
import com.example.appvendas.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;

public class AppVendasProductDetailsCrud extends AppCompatActivity implements OnProductDeleteListener, OnProductEditListener {

    private RecyclerView appVendasProdutosCrudRecyclerView;
    private ProductViewModel appVendasProdutosCrudViewModel;
    private Toolbar toolbar;
    private ActionMode mActionMode;
    private FloatingActionButton addProductFAB;
    private Bitmap picture;
    private ImageHandler imageHandler;
    private static final int ADD_PRODUCT_RESULT_CODE = 1000;
    private static final int EDIT_PRODUCT_RESULT_CODE = 1001;
    private static final int DELETE_PRODUCT_RESULT_CODE = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_vendas_product_crud);

        getSupportActionBar().setTitle("Produtos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.app_vendas_back_icon);

        imageHandler = new ImageHandler(getApplicationContext());

        addProductFAB = findViewById(R.id.appVendasProductCrudFAB);
        addProductFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppVendasProductDetailsCrud.this, AppVendasAddProducts.class);
                startActivityForResult(intent, ADD_PRODUCT_RESULT_CODE);
            }
        });

        appVendasProdutosCrudRecyclerView = findViewById(R.id.productCrudRecyclerView);
        final CRUDProductListRVAdapter adapter = new CRUDProductListRVAdapter(this, this, this);

        appVendasProdutosCrudRecyclerView.setAdapter(adapter);
        appVendasProdutosCrudRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        appVendasProdutosCrudViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        appVendasProdutosCrudViewModel.getAllProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                adapter.setProducts(products);
            }
        });
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            actionMode.getMenuInflater().inflate(R.menu.app_vendas_contextual_actionbar, menu);

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            actionMode.setTitle("Excluir");

            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {

            switch (menuItem.getItemId()) {
                case R.id.delete_icon:
                    new MaterialAlertDialogBuilder(AppVendasProductDetailsCrud.this, R.style.Theme_MaterialComponents_Light_Dialog)
                            .setTitle("Deletar produtos?")
                            .setMessage("Ao deletar os produtos eles não poderão mais ser acessados. \nDeseja mesmo fazer isso?")
                            .setPositiveButton("Aceitar", /* listener = */ new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(AppVendasProductDetailsCrud.this, "Show de bola", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(AppVendasProductDetailsCrud.this, "Mó paia man", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case RESULT_OK:
                switch (requestCode) {
                    case ADD_PRODUCT_RESULT_CODE:
                        Toast.makeText(this, "Produto adicionado", Toast.LENGTH_SHORT).show();
                        break;

                    case EDIT_PRODUCT_RESULT_CODE:
                        Toast.makeText(this, "Produto alterado", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        break;
                }
                break;

            case RESULT_CANCELED:
                if(data != null && data.getStringExtra("erro") != null)
                    Toast.makeText(this, data.getStringExtra("erro"), Toast.LENGTH_SHORT).show();
            default:
                break;
        }
    }

    private void createDirectoryAndSaveFile(Long id) {

        if (id == null && picture != null) {
            EventSingleton eventSingleton = EventSingleton.getInstance();
            eventSingleton.registerEvent(new EventListener() {
                @Override
                public void done(Long id) {
                    try {
                        imageHandler.savePicture(picture, id.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else if (picture != null) {
            try {
                imageHandler.savePicture(picture, id.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean deleteProduct(Product product) {
        if(mActionMode != null) {
            return false;
        }

        mActionMode = startActionMode(mActionModeCallback);

        return true;
    }

    @Override
    public void editProduct(Product product) {
        Intent intent = new Intent(this, AppVendasProductDetail.class);
        intent.putExtra("productName", product.getProductName());
        intent.putExtra("productDescription", product.getProductDescrition());
        intent.putExtra("productId", product.getId());
        intent.putExtra("productPrice", product.getProductPrice());
        intent.putExtra("productGroup", product.getProductGroup());
        intent.putExtra("productOnSale", product.getOnSaleProduct());

        startActivityForResult(intent, EDIT_PRODUCT_RESULT_CODE);
    }

}
