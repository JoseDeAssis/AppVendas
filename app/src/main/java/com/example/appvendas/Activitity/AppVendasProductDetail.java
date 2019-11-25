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

import org.w3c.dom.Text;

import java.io.IOException;

public class AppVendasProductDetail extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    private static final int PRODUCT_GROUP_RESPONSE_CODE = 1002;
    private static final int PRODUCT_DESCRIPTION_RESULT_CODE = 1003;
    private MaterialCardView detailProductCodeCardView, detailProductDescription, detailProductGroup, detailProductImageCardView;
    private Toolbar toolbar;
    private TextInputEditText detailProductTitle, detailProductCode, detailProductPrice;
    private Bitmap photo = null;
    private TextInputLayout detailProductTitleTxtInputLayout, detailProductCodeTxtInputLayout, detailProductPriceTxtInputLayout;
    private MaterialTextView detailProductDescriptionTxt, detailProductGroupTxt;
    private SwitchMaterial detailProductHotSwitch;
    private ProductViewModel appVendasProdutosCrudViewModel;
    private ImageHandler imageHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_vendas_add_products);

        //CardView
        detailProductCodeCardView = findViewById(R.id.productCodeCardView);
        detailProductCodeCardView.setVisibility(View.GONE);

        detailProductDescription = findViewById(R.id.productDescriptionCardView);
        detailProductDescription.setOnClickListener(this);

        detailProductGroup = findViewById(R.id.productGroupCardView);
        detailProductGroup.setOnClickListener(this);

        detailProductImageCardView = findViewById(R.id.productImageCardView);
        detailProductImageCardView.setOnClickListener(this);
        detailProductImageCardView.setPreventCornerOverlap(false);

        //ImageHandler
        imageHandler = new ImageHandler(getApplicationContext());

        //Toolbar
        toolbar = findViewById(R.id.myToolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.app_vendas_back_icon));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Alterar Produto");

        //Text input layout
        detailProductTitleTxtInputLayout = findViewById(R.id.productTitleTxtInputLayout);
        detailProductCodeTxtInputLayout = findViewById(R.id.productCodeTxtInputLayout);
        detailProductPriceTxtInputLayout = findViewById(R.id.productPriceTxtInputLayout);

        //Edit Text input
        detailProductTitle = findViewById(R.id.productTitleEdtTxt);
        detailProductTitle.addTextChangedListener(this);

        detailProductCode = findViewById(R.id.productCodeEdtTxt);
        detailProductCode.addTextChangedListener(this);

        detailProductPrice = findViewById(R.id.productPriceEdtTxt);
        detailProductPrice.addTextChangedListener(this);

        //Text view
        detailProductDescriptionTxt = findViewById(R.id.productDescriptionCardViewTxt);
        detailProductGroupTxt = findViewById(R.id.productGroupCardViewTxt);

        //Switch
        detailProductHotSwitch = findViewById(R.id.productHotSwitch);

        //ViewModel
        appVendasProdutosCrudViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        initializeFields();

    }

    private void initializeFields() {
        detailProductTitle.setText(getIntent().getExtras().getString("productName"));
        detailProductDescriptionTxt.setText(getIntent().getExtras().getString("productDescription"));
        detailProductDescriptionTxt.setTextColor(Color.BLACK);
        detailProductPrice.setText(getIntent().getExtras().getDouble("productPrice") + "");
        detailProductGroupTxt.setText(getIntent().getExtras().getString("productGroup"));
        detailProductGroupTxt.setTextColor(Color.BLACK);
        detailProductHotSwitch.setChecked(getIntent().getExtras().getInt("productOnSale") == 1 ? true : false);
        if(imageHandler.productPhotoExists(getIntent().getExtras().getLong("productId"))) {
            ImageView image = new ImageView(detailProductImageCardView.getContext());
            image.setImageBitmap(imageHandler.getProductPic(getIntent().getExtras().getLong("productId")));
            image.setMaxWidth(detailProductImageCardView.getWidth());
            image.setMaxHeight(detailProductImageCardView.getHeight());
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            detailProductImageCardView.addView(image);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_vendas_confirm_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.productDescriptionCardView:
                Intent productDescriptionIntent = new Intent(AppVendasProductDetail.this, AppVendasProductDescription.class);
                productDescriptionIntent.putExtra("productDescription", detailProductDescriptionTxt.getText().toString());
                startActivityForResult(productDescriptionIntent, PRODUCT_DESCRIPTION_RESULT_CODE);

                break;

            case R.id.productGroupCardView:
                Intent productGroupIntent = new Intent(AppVendasProductDetail.this, AppVendasProductGroup.class);
                productGroupIntent.putExtra("groupSelected", detailProductGroupTxt.getText().toString());
                startActivityForResult(productGroupIntent, PRODUCT_GROUP_RESPONSE_CODE);

                break;

            case R.id.productHotCardView:
                if (detailProductHotSwitch.isChecked()) {
                    detailProductHotSwitch.setChecked(false);
                } else {
                    detailProductHotSwitch.setChecked(true);
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
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (!detailProductTitle.getText().toString().trim().equals("") &&
                detailProductTitleTxtInputLayout.isErrorEnabled()) {
            detailProductTitleTxtInputLayout.setErrorEnabled(false);
        }

        if (!detailProductCode.getText().toString().trim().equals("") &&
                detailProductCodeTxtInputLayout.isErrorEnabled()) {
            detailProductCodeTxtInputLayout.setErrorEnabled(false);
        }

        if (!detailProductPrice.getText().toString().trim().equals("") &&
                detailProductPriceTxtInputLayout.isErrorEnabled()) {
            detailProductPriceTxtInputLayout.setErrorEnabled(false);
        }
    }

    private void createDirectoryAndSaveFile(Long id) {

        if (id != null) {
            try {
                imageHandler.savePicture(photo, id.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case IMAGE_CAPTURE_CODE:
                    ImageView image = new ImageView(detailProductImageCardView.getContext());
                    photo = (Bitmap) data.getExtras().get("data");
                    image.setImageBitmap(photo);
                    image.setMaxWidth(detailProductImageCardView.getWidth());
                    image.setMaxHeight(detailProductImageCardView.getHeight());
                    image.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    detailProductImageCardView.addView(image);
                    break;

                case PRODUCT_GROUP_RESPONSE_CODE:
                    if (data != null) {
                        detailProductGroupTxt.setText(data.getStringExtra("groupName"));
                        detailProductGroupTxt.setTextColor(Color.BLACK);
                    }
                    break;

                case PRODUCT_DESCRIPTION_RESULT_CODE:
                    if (data != null) {
                        detailProductDescriptionTxt.setText(data.getStringExtra("productDescription"));
                        detailProductDescriptionTxt.setTextColor(Color.BLACK);
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

        if (detailProductTitle.getText().toString().trim().equals("")) {
            if (!detailProductTitleTxtInputLayout.isErrorEnabled()) {
                detailProductTitleTxtInputLayout.setErrorEnabled(true);
            }
            detailProductTitleTxtInputLayout.setError("Insira um título válido");
        }

        if (detailProductPrice.getText().toString().trim().equals("")) {
            if (!detailProductPriceTxtInputLayout.isErrorEnabled()) {
                detailProductPriceTxtInputLayout.setErrorEnabled(true);
            }
            detailProductPriceTxtInputLayout.setError("Insira um valor válido");
        }

        if (detailProductGroupTxt.getText().equals("Categoria*")) {
            detailProductGroupTxt.setText("Selecione uma categoria válida");
            detailProductGroupTxt.setTextColor(Color.parseColor("#b71c1c"));
        }

        if (detailProductGroupTxt.getText().equals("Selecione uma categoria válida") ||
                detailProductPrice.getText().toString().equals("") ||
                detailProductTitle.getText().toString().equals("")) {
            validate = false;
        }

        return validate;
    }

    @Override
    public boolean onSupportNavigateUp() {

        if (detailProductTitle.getText().toString().trim().equals(getIntent().getExtras().getString("productName"))
                && detailProductDescriptionTxt.getText().toString().equals(getIntent().getExtras().getString("productDescription"))
                && (Double.parseDouble(detailProductPrice.getText().toString()) == getIntent().getExtras().getDouble("productPrice"))
                && detailProductGroupTxt.getText().toString().equals(getIntent().getExtras().getString("productGroup"))
                && (detailProductHotSwitch.isChecked() == (getIntent().getExtras().getInt("productOnSale") == 1 ? true : false))
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.confirmIcon:
                if (validadeFields()) {
                    new MaterialAlertDialogBuilder(this, R.style.Theme_MaterialComponents_Light_Dialog)
                            .setTitle("Salvar produto?")
                            .setMessage("Ao salvar o produto ele aparecerá em alguma das tabs da tela principal")
                            .setPositiveButton("Atualizar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent();
                                    try {
                                        Product updatedProduct = new Product();
                                        updatedProduct.setId(getIntent().getExtras().getLong("productId"));
                                        updatedProduct.setProductName(detailProductTitle.getText().toString());
                                        updatedProduct.setProductCode(detailProductCode.getText().toString());
                                        updatedProduct.setProductDescrition(detailProductDescriptionTxt.getText().toString());
                                        updatedProduct.setProductGroup(detailProductGroupTxt.getText().toString());
                                        updatedProduct.setProductPrice(Double.parseDouble(detailProductPrice.getText().toString()));
                                        updatedProduct.setOnSaleProduct(detailProductHotSwitch.isChecked() ? 1 : 0);

                                        appVendasProdutosCrudViewModel.update(updatedProduct);

                                        if (photo != null) {
                                            createDirectoryAndSaveFile(updatedProduct.getId());
                                        }
                                        setResult(RESULT_OK, intent);
                                    } catch (Exception e) {
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
}
