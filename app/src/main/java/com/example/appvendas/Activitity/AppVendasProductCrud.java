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
import com.example.appvendas.Model.ProductViewModel;
import com.example.appvendas.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;

public class AppVendasProductCrud extends AppCompatActivity {

    private RecyclerView appVendasProdutosCrudRecyclerView;
    private ProductViewModel appVendasProdutosCrudViewModel;
    private Toolbar toolbar;
    private ActionMode mActionMode;
    private FloatingActionButton addProductFAB;
    static public String filePath = "MyFileStorage";
    private File myExternalFile;
    private static final int ADD_PRODUCT_RESULT_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_vendas_product_crud);

        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Produtos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addProductFAB = findViewById(R.id.appVendasProductCrudFAB);
        addProductFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppVendasProductCrud.this, AppVendasAddProducts.class);
                startActivityForResult(intent, ADD_PRODUCT_RESULT_CODE);
            }
        });

        appVendasProdutosCrudRecyclerView = findViewById(R.id.productCrudRecyclerView);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case RESULT_OK:
                switch (requestCode) {
                    case ADD_PRODUCT_RESULT_CODE:
                        Product newProduct = new Product();
                        newProduct.setProductName(data.getStringExtra("productName"));
                        newProduct.setProductDescrition(data.getStringExtra("productDescription"));
                        newProduct.setProductGroup(data.getStringExtra("productGroup"));
                        newProduct.setProductPrice(data.getDoubleExtra("productPrice", 0));

                        appVendasProdutosCrudViewModel.insert(newProduct);

//                        if(data.getSerializableExtra("productPhoto") != null) {
//                            HashMap<String, Bitmap> bitmapMap = (HashMap<String, Bitmap>) data.getSerializableExtra("productPhoto");
//                            createDirectoryAndSaveFile(bitmapMap.get("photoProduct"), Long.toString(produtos.getId()));
//                        }
                        break;

                    default:
                        break;
                }
                break;

            default:
                break;
        }
    }

    private void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {

        try {
            myExternalFile = new File(getExternalFilesDir(filePath), fileName);
            FileOutputStream out = new FileOutputStream(myExternalFile);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}