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
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.appvendas.Adapter.ProductListRVAdapter;
import com.example.appvendas.Entity.Product;
import com.example.appvendas.Helpers.Handler.ImageHandler;
import com.example.appvendas.Helpers.Interface.OnProductDetailsListener;
import com.example.appvendas.Helpers.Singleton.EventSingleton;
import com.example.appvendas.Helpers.Interface.EventListener;
import com.example.appvendas.Model.ProductViewModel;
import com.example.appvendas.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;

public class AppVendasProductDetailsCrud extends AppCompatActivity implements OnProductDetailsListener {

    private RecyclerView appVendasProdutosCrudRecyclerView;
    private ProductViewModel appVendasProdutosCrudViewModel;
    private Toolbar toolbar;
    private ActionMode mActionMode;
    private FloatingActionButton addProductFAB;
    private Bitmap picture;
    private ImageHandler imageHandler;
    private static final int ADD_PRODUCT_RESULT_CODE = 1000;
    private static final int EDIT_PRODUCT_RESULT_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_vendas_product_crud);

        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Produtos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        final ProductListRVAdapter adapter = new ProductListRVAdapter(this, this);

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
                if (mActionMode != null) {
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
                    new MaterialAlertDialogBuilder(AppVendasProductDetailsCrud.this, R.style.Theme_MaterialComponents_Light_Dialog)
                            .setTitle("Salvar produto?")
                            .setMessage("Ao salvar o produto ele aparecerá em alguma das tabs da tela principal")
                            .setPositiveButton("Accept", /* listener = */ new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(AppVendasProductDetailsCrud.this, "Show de bola", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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

                    default:
                        break;
                }
                break;

            case RESULT_CANCELED:
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
    public void getProductDetails(Product product) {
        Intent intent = new Intent();
        intent.putExtra("productName", product.getProductName());
        intent.putExtra("productDescription", product.getProductDescrition());
        intent.putExtra("productId", product.getId());
        intent.putExtra("productPrice", product.getProductPrice());

        startActivityForResult(intent, EDIT_PRODUCT_RESULT_CODE);
    }

    @Override
    public void setProductChecked(Product product, boolean isChecked) {
        return;
    }
}
