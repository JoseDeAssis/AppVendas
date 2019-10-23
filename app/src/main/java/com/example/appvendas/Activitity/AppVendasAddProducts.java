package com.example.appvendas.Activitity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appvendas.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppVendasAddProducts extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    private static final int PRODUCT_GROUP_RESPONSE_CODE = 1002;
    private static final int PRODUCT_DESCRIPTION_RESULT_CODE = 1003;
    private Toolbar toolbar;
    private TextInputEditText newProductTitle, newProductPrice;
    private MaterialTextView newProductDescriptionTxt, newProductGroupTxt;
    private MaterialCardView newProductImageCardView, newProductDescription, newProductGroup;
    private Uri uriImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_vendas_add_products);

        toolbar = findViewById(R.id.myToolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.app_vendas_back_icon));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Adicionar Produto");

        newProductTitle = findViewById(R.id.productTitleEdtTxt);
        newProductDescription = findViewById(R.id.productDescriptionCardView);
        newProductDescriptionTxt = findViewById(R.id.productDescriptionCardViewTxt);
        newProductPrice = findViewById(R.id.productPriceEdtTxt);
        newProductGroup = findViewById(R.id.productGroupCardView);
        newProductGroupTxt = findViewById(R.id.productGroupCardViewTxt);
        newProductImageCardView = findViewById(R.id.productImageCardView);
        newProductImageCardView.setPreventCornerOverlap(false);

        newProductDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppVendasAddProducts.this, AppVendasProductDescription.class);
                intent.putExtra("productDescription", newProductDescriptionTxt.getText().toString());
                startActivityForResult(intent, PRODUCT_DESCRIPTION_RESULT_CODE);
            }
        });

        newProductGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppVendasAddProducts.this, AppVendasProductGroup.class);
                intent.putExtra("groupSelected", newProductGroupTxt.getText().toString());

                startActivityForResult(intent, PRODUCT_GROUP_RESPONSE_CODE);
            }
        });

        newProductImageCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 0);
                    } else {
                        openCamera();
                    }
                } else {
                    openCamera();
                }
            }
        });
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
                new MaterialAlertDialogBuilder(this, R.style.Theme_MaterialComponents_Light_Dialog)
                        .setTitle("Salvar produto?")
                        .setMessage("Ao salvar o produto ele aparecerá em alguma das tabs da tela principal")
                        .setPositiveButton("Accept", /* listener = */ new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(AppVendasAddProducts.this, "Show de bola", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(AppVendasAddProducts.this, "Mó paia man", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
//                finish();
                break;

            case android.R.id.home:

                new MaterialAlertDialogBuilder(this, R.style.Theme_MaterialComponents_Light_Dialog)
                        .setTitle("Descartar rascunho?")
                        .setMessage("Todas as mudanças não salvas serão perdidas")
                        .setPositiveButton("Descartar", /* listener = */ new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(AppVendasAddProducts.this, "Show de bola", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(AppVendasAddProducts.this, "Mó paia man", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
//                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case IMAGE_CAPTURE_CODE:
                    ImageView image = new ImageView(newProductImageCardView.getContext());
                    image.setImageURI(uriImage);
                    image.setMaxWidth(newProductImageCardView.getWidth());
                    image.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    newProductImageCardView.addView(image);
                    break;

                case PRODUCT_GROUP_RESPONSE_CODE:
                    newProductGroupTxt.setText(data.getStringExtra("groupName"));
                    newProductGroupTxt.setTextColor(Color.BLACK);
                    break;

                case PRODUCT_DESCRIPTION_RESULT_CODE:
                    break;
            }
        }
    }

    private  void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the camera");
        uriImage = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriImage);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
