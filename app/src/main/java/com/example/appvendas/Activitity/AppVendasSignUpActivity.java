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
import android.widget.Toast;

import com.example.appvendas.Entity.User;
import com.example.appvendas.Helpers.Mask;
import com.example.appvendas.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class AppVendasSignUpActivity extends AppCompatActivity implements View.OnTouchListener, TextWatcher {

    private TextInputEditText signUpEmailTxt, signUpPasswordTxt, signUpFullNameTxt, signUpCPFTxt, signUpBirthDateTxt, signUpPhoneNumberTxt;
    private AutoCompleteTextView signUpGenderTxt, signUpReferToMeAsTxt;
    private TextInputLayout signUpEmailTxtLayout, signUpPasswordTxtLayout, signUpFullNameTxtLayout, signUpGenderTxtLayout;
    private FirebaseAuth mFirebaseAuth;

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
        signUpGenderTxt.setOnTouchListener(this);
        signUpReferToMeAsTxt = findViewById(R.id.signUpReferToMeAsMaterialEditTxt);
        signUpReferToMeAsTxt.setOnTouchListener(this);

        //TextInputLayout
        signUpEmailTxtLayout = findViewById(R.id.signUpEmailMaterialTxtInput);
        signUpPasswordTxtLayout = findViewById(R.id.signUpPasswordMaterialTxtInput);
        signUpFullNameTxtLayout = findViewById(R.id.signUpFullNameMaterialTxtInput);
        signUpGenderTxtLayout = findViewById(R.id.signUpGenderMaterialTxtInput);

        //Firebase
        mFirebaseAuth = FirebaseAuth.getInstance();

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
                mFirebaseAuth.createUserWithEmailAndPassword(signUpEmailTxt.getText() + "", signUpPasswordTxt.getText() + "")
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    User user = new User(
                                            signUpFullNameTxt.getText() + "",
                                            signUpPasswordTxt.getText() + "",
                                            signUpEmailTxt.getText() + ""
                                    );

                                    if (!signUpCPFTxt.getText().toString().trim().equals("")) {
                                        user.setUserCPF(signUpCPFTxt.getText() + "");
                                    }

                                    if (!signUpBirthDateTxt.getText().toString().trim().equals("")) {
                                        user.setUserBirthday(signUpBirthDateTxt.getText() + "");
                                    }

                                    if (!signUpPhoneNumberTxt.getText().toString().trim().equals("")) {
                                        user.setUserPhoneNumber(signUpPhoneNumberTxt.getText() + "");
                                    }

                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()) {
                                                setResult(RESULT_OK);
                                                finish();
                                            } else {
                                                Toast.makeText(AppVendasSignUpActivity.this, task.getException() + "", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(AppVendasSignUpActivity.this, task.getException() + "", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
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
