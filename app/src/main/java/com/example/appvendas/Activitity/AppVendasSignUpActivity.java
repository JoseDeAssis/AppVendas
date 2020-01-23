package com.example.appvendas.Activitity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;

import com.example.appvendas.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AppVendasSignUpActivity extends AppCompatActivity implements View.OnTouchListener, TextWatcher {

    private TextInputEditText signUpEmailTxt, signUpPasswordTxt, signUpFullNameTxt, signUpCPFTxt, signUpBirthDateTxt, signUpPhoneNumberTxt;
    private AutoCompleteTextView signUpGenderTxt, signUpReferToMeAsTxt;
    private TextInputLayout signUpEmailTxtLayout, signUpPasswordTxtLayout, signUpFullNameTxtLayout, signUpGenderTxtLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.myToolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.app_vendas_back_icon));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cadastro");

        //TextInputEditText
        signUpEmailTxt = findViewById(R.id.signUpEmailMaterialEditTxt);
        signUpEmailTxt.addTextChangedListener(this);
        signUpPasswordTxt = findViewById(R.id.signUpPasswordMaterialEditTxt);
        signUpPasswordTxt.addTextChangedListener(this);
        signUpFullNameTxt = findViewById(R.id.signUpFullNameMaterialEditTxt);
        signUpFullNameTxt.addTextChangedListener(this);

        signUpCPFTxt = findViewById(R.id.signUpCPFMaterialEditTxt);
        signUpBirthDateTxt = findViewById(R.id.signUpBirthDateMaterialEditTxt);
        signUpPhoneNumberTxt = findViewById(R.id.signUpPhoneNumberMaterialEditTxt);

        //AutoCompleteTextView
        signUpGenderTxt = findViewById(R.id.signUpGenderMaterialEditTxt);
        signUpGenderTxt.setOnTouchListener(this);
        signUpReferToMeAsTxt = findViewById(R.id.signUpReferToMeAsMaterialEditTxt);
        signUpReferToMeAsTxt.setOnTouchListener(this);

        //TextInputLayout
        signUpEmailTxtLayout = findViewById(R.id.signUpEmailMaterialTxtInput);
        signUpPasswordTxtLayout = findViewById(R.id.signUpPasswordMaterialTxtInput);
        signUpFullNameTxtLayout = findViewById(R.id.signUpFullNameMaterialTxtInput);
        signUpGenderTxtLayout = findViewById(R.id.signUpGenderMaterialTxtInput);

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_vendas_confirm_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    public boolean validateFields() {
        boolean validate = true;

        if(signUpEmailTxt.getText() != null && signUpEmailTxt.getText().toString().trim().equals("")) {
            if(!signUpEmailTxtLayout.isErrorEnabled()) {
                signUpEmailTxtLayout.setErrorEnabled(true);
            }
            signUpEmailTxtLayout.setError("Insira um email válido");
            validate = false;
        }

        if(signUpPasswordTxt.getText() != null && signUpPasswordTxt.getText().toString().trim().equals("")) {
            if(!signUpPasswordTxtLayout.isErrorEnabled()) {
                signUpPasswordTxtLayout.setErrorEnabled(true);
            }
            signUpPasswordTxtLayout.setError("Insira uma senha válida");
            validate = false;
        }

        if(signUpFullNameTxt.getText() != null && signUpFullNameTxt.getText().toString().trim().equals("")) {
            if(!signUpFullNameTxtLayout.isErrorEnabled()){
                signUpFullNameTxtLayout.setErrorEnabled(true);
            }
            signUpFullNameTxtLayout.setError("Insira um nome válido");
            validate = false;
        }

        return validate;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        validateFields();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(signUpEmailTxt.getText() != null
                && !signUpEmailTxt.getText().toString().trim().equals("")
                && signUpEmailTxtLayout.isErrorEnabled()) {
            signUpEmailTxtLayout.setErrorEnabled(false);
        } else if(signUpPasswordTxt.getText() != null
                && !signUpPasswordTxt.getText().toString().trim().equals("")
                && signUpPasswordTxtLayout.isErrorEnabled()) {
            signUpPasswordTxtLayout.setErrorEnabled(false);
        } else if(signUpFullNameTxt.getText() != null
                && !signUpFullNameTxt.getText().toString().trim().equals("")
                && signUpFullNameTxtLayout.isErrorEnabled()) {
            signUpFullNameTxtLayout.setErrorEnabled(false);
        }
    }
}
