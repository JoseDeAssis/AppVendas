package com.example.appvendas.Activitity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.appvendas.Helpers.Handler.KeyboardHandler;
import com.example.appvendas.Helpers.Interface.FirebaseEventListener;
import com.example.appvendas.Helpers.Mask;
import com.example.appvendas.Helpers.Singleton.EventSingleton;
import com.example.appvendas.Helpers.Singleton.FirebaseSingleton;
import com.example.appvendas.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialAutoCompleteTextView;

public class AppVendasSignUpActivity extends AppCompatActivity implements TextWatcher, View.OnFocusChangeListener {

    private TextInputEditText signUpEmailTxt, signUpPasswordTxt, signUpFullNameTxt, signUpCPFTxt,
            signUpBirthDateTxt, signUpPhoneNumberTxt, signUpChooseGenderTxt;
    private MaterialAutoCompleteTextView signUpGenderTxt, signUpReferToMeAsTxt;
    private TextInputLayout signUpEmailTxtLayout, signUpPasswordTxtLayout, signUpFullNameTxtLayout,
            signUpGenderTxtLayout, signUpReferToMeAsTxtLayout, signUpChooseGenderTxtLayout;
    private FirebaseSingleton mFirebaseSingleton;
    private MaterialCardView signUpChooseGenderCardView;
    private ProgressBar signUpProgressBar;

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
        signUpCPFTxt.addTextChangedListener(Mask.insert(Mask.CPF_MASK, signUpCPFTxt));
        signUpBirthDateTxt = findViewById(R.id.signUpBirthDateMaterialEditTxt);
        signUpBirthDateTxt.addTextChangedListener(Mask.insert(Mask.DATE_MASK, signUpBirthDateTxt));
        signUpPhoneNumberTxt = findViewById(R.id.signUpPhoneNumberMaterialEditTxt);
        signUpPhoneNumberTxt.addTextChangedListener(Mask.insert(Mask.CELULAR_MASK, signUpPhoneNumberTxt));
        signUpChooseGenderTxt = findViewById(R.id.signUpChooseGenderMaterialEditTxt);
        signUpChooseGenderTxt.addTextChangedListener(this);

        //AutoCompleteTextView
        signUpGenderTxt = findViewById(R.id.signUpGenderMaterialAutoComplete);
        signUpGenderTxt.setInputType(InputType.TYPE_NULL);
        ArrayAdapter<String> adapterGender = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.genderSpinner));

        signUpGenderTxt.setAdapter(adapterGender);
        signUpGenderTxt.addTextChangedListener(this);
        signUpGenderTxt.setOnFocusChangeListener(this);


        signUpReferToMeAsTxt = findViewById(R.id.signUpReferToMeAsMaterialAutoComplete);
        signUpReferToMeAsTxt.setInputType(InputType.TYPE_NULL);
        ArrayAdapter<String> adapterGenderReferToMe = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.referToMeAsGender));

        signUpReferToMeAsTxt.setAdapter(adapterGenderReferToMe);
        signUpReferToMeAsTxt.addTextChangedListener(this);
        signUpReferToMeAsTxt.setOnFocusChangeListener(this);

        //TextInputLayout
        signUpEmailTxtLayout = findViewById(R.id.signUpEmailMaterialTxtInput);
        signUpPasswordTxtLayout = findViewById(R.id.signUpPasswordMaterialTxtInput);
        signUpFullNameTxtLayout = findViewById(R.id.signUpFullNameMaterialTxtInput);
        signUpGenderTxtLayout = findViewById(R.id.signUpGenderTextLayout);
        signUpReferToMeAsTxtLayout = findViewById(R.id.signUpReferToMeAsMaterialTextLayout);
        signUpChooseGenderTxtLayout = findViewById(R.id.signUpChooseGenderMaterialTextLayout);

        //MaterialCardView
        signUpChooseGenderCardView = findViewById(R.id.signUpChooseGenderCardView);

        //Progressbar
        signUpProgressBar = findViewById(R.id.signUpProgressBar);

        //Firebase
        mFirebaseSingleton = FirebaseSingleton.getInstance();
        EventSingleton mEventSingleton = EventSingleton.getInstance();
        mEventSingleton.registerFirebaseEvent(new FirebaseEventListener() {
            @Override
            public void signUpDone(boolean isSuccessfull, String signUpException) {
                if (isSuccessfull) {
                    signUpProgressBar.setVisibility(View.GONE);
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(AppVendasSignUpActivity.this, signUpException, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void logInDone() {

            }

            @Override
            public void logInFailed() {

            }
        });

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

        if (signUpEmailTxt.getText() != null && signUpEmailTxt.getText().toString().trim().equals("")) {
            if (!signUpEmailTxtLayout.isErrorEnabled()) {
                signUpEmailTxtLayout.setErrorEnabled(true);
            }
            signUpEmailTxtLayout.setError("Insira um email válido");
            validate = false;
        }

        if (signUpPasswordTxt.getText() != null && signUpPasswordTxt.getText().toString().trim().equals("")) {
            if (!signUpPasswordTxtLayout.isErrorEnabled()) {
                signUpPasswordTxtLayout.setErrorEnabled(true);
            }
            signUpPasswordTxtLayout.setError("Insira uma senha válida");
            validate = false;
        }

        if (signUpFullNameTxt.getText() != null && signUpFullNameTxt.getText().toString().trim().equals("")) {
            if (!signUpFullNameTxtLayout.isErrorEnabled()) {
                signUpFullNameTxtLayout.setErrorEnabled(true);
            }
            signUpFullNameTxtLayout.setError("Insira um nome válido");
            validate = false;
        }

        if (signUpGenderTxt.getText() != null && signUpGenderTxt.getText().toString().trim().equals("")) {
            if (!signUpGenderTxtLayout.isErrorEnabled()) {
                signUpGenderTxtLayout.setErrorEnabled(true);
            }
            signUpGenderTxtLayout.setError("Selecione seu sexo");
            validate = false;
        }

        if (signUpChooseGenderCardView.getVisibility() == View.VISIBLE) {
            if (signUpReferToMeAsTxt.getText() != null && signUpReferToMeAsTxt.getText().toString().trim().equals("")) {
                if (!signUpReferToMeAsTxtLayout.isErrorEnabled()) {
                    signUpReferToMeAsTxtLayout.setErrorEnabled(true);
                }
                signUpReferToMeAsTxtLayout.setError("Selecione um pronome");
                validate = false;
            }
            if (signUpChooseGenderTxt.getText() != null && signUpChooseGenderTxt.getText().toString().trim().equals("")) {
                if (!signUpChooseGenderTxtLayout.isErrorEnabled()) {
                    signUpChooseGenderTxtLayout.setErrorEnabled(true);
                }
                signUpChooseGenderTxtLayout.setError("Indique sua identidade sexual");
                validate = false;
            }
        }

        return validate;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.confirmIcon) {
            if (validateFields()) {
                signUpProgressBar.setVisibility(View.VISIBLE);
                KeyboardHandler.hideKeyboard(this);
                if (getCurrentFocus() != null)
                    getCurrentFocus().clearFocus();

                mFirebaseSingleton.signUp(
                        signUpEmailTxt.getText() + "",
                        signUpPasswordTxt.getText() + "",
                        signUpFullNameTxt.getText() + "",
                        signUpGenderTxt.getText() + "",
                        signUpCPFTxt.getText() + "",
                        signUpPhoneNumberTxt.getText() + "",
                        signUpBirthDateTxt.getText() + ""
                );
            }
        }
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
        if (signUpEmailTxt.getText() != null
                && !signUpEmailTxt.getText().toString().trim().equals("")
                && signUpEmailTxtLayout.isErrorEnabled()) {
            signUpEmailTxtLayout.setErrorEnabled(false);
        } else if (signUpPasswordTxt.getText() != null
                && !signUpPasswordTxt.getText().toString().trim().equals("")
                && signUpPasswordTxtLayout.isErrorEnabled()) {
            signUpPasswordTxtLayout.setErrorEnabled(false);
        } else if (signUpFullNameTxt.getText() != null
                && !signUpFullNameTxt.getText().toString().trim().equals("")
                && signUpFullNameTxtLayout.isErrorEnabled()) {
            signUpFullNameTxtLayout.setErrorEnabled(false);
        } else if (!signUpGenderTxt.getText().toString().trim().equals("")
                && signUpGenderTxtLayout.isErrorEnabled()) {
            signUpGenderTxtLayout.setErrorEnabled(false);
        } else if (signUpChooseGenderTxt.getText() != null
                && !signUpChooseGenderTxt.getText().toString().trim().equals("")
                && signUpChooseGenderTxtLayout.isErrorEnabled()) {
            signUpChooseGenderTxtLayout.setErrorEnabled(false);
        } else if (!signUpReferToMeAsTxt.getText().toString().trim().equals("")
                && signUpReferToMeAsTxtLayout.isErrorEnabled()) {
            signUpReferToMeAsTxtLayout.setErrorEnabled(false);
        }

        if (signUpGenderTxt.getText() != null
                && signUpGenderTxt.getText().toString().equals(getResources().getString(R.string.customize_gender))) {
            signUpChooseGenderCardView.setVisibility(View.VISIBLE);
        } else if (signUpGenderTxt.getText() != null
                && !signUpGenderTxt.getText().toString().equals(getResources().getString(R.string.customize_gender))) {
            signUpChooseGenderCardView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (view.hasFocus()) {
            KeyboardHandler.hideKeyboard(AppVendasSignUpActivity.this);
            if (view.getId() == R.id.signUpGenderMaterialAutoComplete)
                signUpGenderTxt.showDropDown();
            else
                signUpReferToMeAsTxt.showDropDown();
        }
    }
}
