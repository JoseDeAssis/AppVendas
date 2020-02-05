package com.example.appvendas.Activitity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.appvendas.Helpers.Handler.KeyboardHandler;
import com.example.appvendas.Helpers.Interface.FirebaseEventListener;
import com.example.appvendas.Helpers.Mask;
import com.example.appvendas.Helpers.Singleton.EventSingleton;
import com.example.appvendas.Helpers.Singleton.FirebaseSingleton;
import com.example.appvendas.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Wrapper;

public class AppVendasSignUpActivity extends AppCompatActivity implements View.OnTouchListener, TextWatcher {

    private TextInputEditText signUpEmailTxt, signUpPasswordTxt, signUpFullNameTxt, signUpCPFTxt, signUpBirthDateTxt, signUpPhoneNumberTxt;
    private AutoCompleteTextView signUpGenderTxt, signUpReferToMeAsTxt;
    private TextInputLayout signUpEmailTxtLayout, signUpPasswordTxtLayout, signUpFullNameTxtLayout, signUpGenderTxtLayout;
    private FirebaseSingleton mFirebaseSingleton;
    private long mLastClickTime = 0;

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

        //AutoCompleteTextView
        signUpGenderTxt = findViewById(R.id.signUpGenderMaterialEditTxt);
//        signUpGenderTxt.setOnTouchListener(this);
        signUpReferToMeAsTxt = findViewById(R.id.signUpReferToMeAsMaterialEditTxt);
        signUpReferToMeAsTxt.setOnTouchListener(this);


        //TextInputLayout
        signUpEmailTxtLayout = findViewById(R.id.signUpEmailMaterialTxtInput);
        signUpPasswordTxtLayout = findViewById(R.id.signUpPasswordMaterialTxtInput);
        signUpFullNameTxtLayout = findViewById(R.id.signUpFullNameMaterialTxtInput);
        signUpGenderTxtLayout = findViewById(R.id.signUpGenderMaterialTxtInput);

        //Firebase
        mFirebaseSingleton = FirebaseSingleton.getInstance();
        EventSingleton mEventSingleton = EventSingleton.getInstance();
        mEventSingleton.registerFirebaseEvent(new FirebaseEventListener() {
            @Override
            public void signUpDone(boolean isSuccessfull, String signUpException) {
                if(isSuccessfull) {
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
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.getId() == R.id.signUpGenderMaterialEditTxt || view.getId() == R.id.signUpGenderMaterialTxtInput) {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return false;
            }
            mLastClickTime = SystemClock.elapsedRealtime();

            /*Context wrapper = new ContextThemeWrapper(this, R.style.app_vendas_popup_menu_style);
            PopupMenu popupMenu = new PopupMenu(wrapper, view);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.maleGender:
                            signUpGenderTxt.setText(R.string.male_gender);
                            return true;
                        case R.id.femaleGender:
                            signUpGenderTxt.setText(R.string.female_gender);
                            return true;
                        case R.id.customizeGender:
                            signUpGenderTxt.setText(R.string.customize_gender);
                            return true;
                        case R.id.notDeclaredGender:
                            signUpGenderTxt.setText(R.string.not_declared_gender);
                            return true;
                    }
                    return false;
                }
            });
            popupMenu.getMenuInflater().inflate(R.menu.app_vendas_gender_menu, popupMenu.getMenu());
            popupMenu.show();*/
        }
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

        return validate;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.confirmIcon) {
            if (validateFields()) {
                KeyboardHandler.hideKeyboard(this);
                if(getCurrentFocus() != null)
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
        }
    }
}
