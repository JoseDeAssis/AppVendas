package com.example.appvendas.Activitity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appvendas.Entity.Product;
import com.example.appvendas.Helpers.Handler.ImageHandler;
import com.example.appvendas.Helpers.Interface.EventListener;
import com.example.appvendas.Helpers.Singleton.EventSingleton;
import com.example.appvendas.Model.ProductViewModel;
import com.example.appvendas.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.io.IOException;
public class AppVendasAddProducts extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    private static final int PRODUCT_GROUP_RESPONSE_CODE = 1002;
    private static final int PRODUCT_DESCRIPTION_RESULT_CODE = 1003;
    private Toolbar toolbar;
    private TextInputEditText newProductTitle, newProductCode, newProductPrice;
    private MaterialTextView newProductDescriptionTxt, newProductGroupTxt;
    private MaterialCardView newProductImageCardView, newProductDescription, newProductGroup, newProductHot;
    private Bitmap photo = null;
    private TextInputLayout productTitleTxtInputLayout, productCodeTxtInputLayout, productPriceTxtInputLayout;
    private SwitchMaterial newProductHotSwitch;
    private ProductViewModel appVendasProdutosCrudViewModel;
    private ImageHandler imageHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_vendas_add_products);

        //ImageHandler
        imageHandler = new ImageHandler(getApplicationContext());

        //Toolbar
        toolbar = findViewById(R.id.myToolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.app_vendas_back_icon));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Adicionar Produto");

        //Text input layout
        productTitleTxtInputLayout = findViewById(R.id.productTitleTxtInputLayout);
        productCodeTxtInputLayout = findViewById(R.id.productCodeTxtInputLayout);
        productPriceTxtInputLayout = findViewById(R.id.productPriceTxtInputLayout);

        //Edit Text input
        newProductTitle = findViewById(R.id.productTitleEdtTxt);
        newProductTitle.addTextChangedListener(this);

        newProductCode = findViewById(R.id.productCodeEdtTxt);
        newProductCode.addTextChangedListener(this);

        newProductPrice = findViewById(R.id.productPriceEdtTxt);
        newProductPrice.addTextChangedListener(this);

        //Card view
        newProductDescription = findViewById(R.id.productDescriptionCardView);
        newProductDescription.setOnClickListener(this);

        newProductGroup = findViewById(R.id.productGroupCardView);
        newProductGroup.setOnClickListener(this);

        newProductHot = findViewById(R.id.productHotCardView);
        newProductHot.setOnClickListener(this);

        newProductImageCardView = findViewById(R.id.productImageCardView);
        newProductImageCardView.setOnClickListener(this);
        newProductImageCardView.setPreventCornerOverlap(false);

        //Text view
        newProductDescriptionTxt = findViewById(R.id.productDescriptionCardViewTxt);
        newProductGroupTxt = findViewById(R.id.productGroupCardViewTxt);

        //Switch
        newProductHotSwitch = findViewById(R.id.productHotSwitch);

        //ViewModel
        appVendasProdutosCrudViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_vendas_confirm_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.confirmIcon:
                if (validadeFields()) {
                    new MaterialAlertDialogBuilder(this, R.style.Theme_MaterialComponents_Light_Dialog)
                            .setTitle("Salvar produto?")
                            .setMessage("Ao salvar o produto ele aparecerá em alguma das tabs da tela principal")
                            .setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent();
                                    try {
                                        Product newProduct = new Product();
                                        newProduct.setProductName(newProductTitle.getText().toString());
                                        newProduct.setProductCode(newProductCode.getText().toString());
                                        newProduct.setProductDescrition(newProductDescriptionTxt.getText().toString());
                                        newProduct.setProductGroup(newProductGroupTxt.getText().toString());
                                        newProduct.setProductPrice(Double.parseDouble(newProductPrice.getText().toString()));
                                        newProduct.setOnSaleProduct(newProductHotSwitch.isChecked() ? 1 : 0);

                                        appVendasProdutosCrudViewModel.insert(newProduct);

                                        if (photo != null) {
                                            createDirectoryAndSaveFile();
                                        }
                                        setResult(RESULT_OK, intent);
                                    } catch(Exception e) {
                                        intent.putExtra("erro", e.getMessage());
                                        setResult(RESULT_CANCELED, intent);
                                    }

                                    finish();
                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case IMAGE_CAPTURE_CODE:
                    ImageView image = new ImageView(newProductImageCardView.getContext());
                    photo = (Bitmap) data.getExtras().get("data");
                    image.setImageBitmap(photo);
                    image.setMaxWidth(newProductImageCardView.getWidth());
                    image.setMaxHeight(newProductImageCardView.getHeight());
                    image.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    newProductImageCardView.addView(image);
                    break;

                case PRODUCT_GROUP_RESPONSE_CODE:
                    if (data != null) {
                        newProductGroupTxt.setText(data.getStringExtra("groupName"));
                        newProductGroupTxt.setTextColor(Color.BLACK);
                    }
                    break;

                case PRODUCT_DESCRIPTION_RESULT_CODE:
                    if (data != null) {
                        newProductDescriptionTxt.setText(data.getStringExtra("productDescription"));
                        newProductDescriptionTxt.setTextColor(Color.BLACK);
                    }
                    break;

                default:
                    break;
            }
        }
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public boolean validadeFields() {

        boolean validate = true;

        if (newProductTitle.getText().toString().trim().equals("")) {
            if (!productTitleTxtInputLayout.isErrorEnabled()) {
                productTitleTxtInputLayout.setErrorEnabled(true);
            }
            productTitleTxtInputLayout.setError("Insira um título válido");
        }

        if (newProductCode.getText().toString().trim().equals("")) {
            if (!productCodeTxtInputLayout.isErrorEnabled()) {
                productCodeTxtInputLayout.setErrorEnabled(true);
            }
            productCodeTxtInputLayout.setError("Insira um código válido");
        }

        if (newProductPrice.getText().toString().trim().equals("")) {
            if (!productPriceTxtInputLayout.isErrorEnabled()) {
                productPriceTxtInputLayout.setErrorEnabled(true);
            }
            productPriceTxtInputLayout.setError("Insira um valor válido");
        }

        if (newProductGroupTxt.getText().equals("Categoria*")) {
            newProductGroupTxt.setText("Selecione uma categoria válida");
            newProductGroupTxt.setTextColor(Color.parseColor("#b71c1c"));
        }

        if (newProductGroupTxt.getText().equals("Selecione uma categoria válida") ||
                newProductPrice.getText().toString().equals("") ||
                newProductCode.getText().toString().equals("") ||
                newProductTitle.getText().toString().equals("")) {
            validate = false;
        }

        return validate;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.productDescriptionCardView:
                Intent productDescriptionIntent = new Intent(AppVendasAddProducts.this, AppVendasProductDescription.class);
                productDescriptionIntent.putExtra("productDescription", newProductDescriptionTxt.getText().toString());
                startActivityForResult(productDescriptionIntent, PRODUCT_DESCRIPTION_RESULT_CODE);

                break;

            case R.id.productGroupCardView:
                Intent productGroupIntent = new Intent(AppVendasAddProducts.this, AppVendasProductGroup.class);
                productGroupIntent.putExtra("groupSelected", newProductGroupTxt.getText().toString());
                startActivityForResult(productGroupIntent, PRODUCT_GROUP_RESPONSE_CODE);

                break;

            case R.id.productHotCardView:
                if (newProductHotSwitch.isChecked()) {
                    newProductHotSwitch.setChecked(false);
                } else {
                    newProductHotSwitch.setChecked(true);
                }

                break;

            case R.id.productImageCardView:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permission = {Manifest.permission.CAMERA/*, Manifest.permission.WRITE_EXTERNAL_STORAGE*/};
                        requestPermissions(permission, PERMISSION_CODE);
                    }
                }

                break;
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!newProductTitle.getText().toString().trim().equals("") &&
                productTitleTxtInputLayout.isErrorEnabled()) {
            productTitleTxtInputLayout.setErrorEnabled(false);
        }

        if (!newProductCode.getText().toString().trim().equals("") &&
                productCodeTxtInputLayout.isErrorEnabled()) {
            productCodeTxtInputLayout.setErrorEnabled(false);
        }

        if (!newProductPrice.getText().toString().trim().equals("") &&
                productPriceTxtInputLayout.isErrorEnabled()) {
            productPriceTxtInputLayout.setErrorEnabled(false);
        }
    }

    private void createDirectoryAndSaveFile() {

        if (photo != null) {
            EventSingleton eventSingleton = EventSingleton.getInstance();
            eventSingleton.registerEvent(new EventListener() {
                @Override
                public void done(Long id) {
                    try {
                        imageHandler.savePicture(photo, id.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public boolean onSupportNavigateUp() {

        if(newProductTitle.getText().toString().trim().equals("")
            && newProductCode.getText().toString().trim().equals("")
            && newProductDescriptionTxt.getText().toString().equals("Descrição")
            && newProductPrice.getText().toString().trim().equals("")
            && newProductGroupTxt.getText().toString().equals("Categoria*")
            && !newProductHotSwitch.isChecked()
            && photo == null) {
            setResult(RESULT_CANCELED);
            finish();
        } else {
            new MaterialAlertDialogBuilder(this, R.style.Theme_MaterialComponents_Light_Dialog)
                    .setTitle("Descartar rascunho?")
                    .setMessage("Todas as mudanças não salvas serão perdidas")
                    .setPositiveButton("Descartar", /* listener = */ new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setResult(RESULT_CANCELED);
                            finish();
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        }

        return true;
    }
}
