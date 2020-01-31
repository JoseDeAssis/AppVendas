package com.example.appvendas.Activitity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.appvendas.Helpers.Singleton.FirebaseSingleton;
import com.example.appvendas.Helpers.Interface.FirebaseEventListener;
import com.example.appvendas.Helpers.Singleton.EventSingleton;
import com.example.appvendas.Helpers.Singleton.PreferencesSingleton;
import com.example.appvendas.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AppVendasLoginActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private static final int SIGN_UP_CODE = 1000;

    private TextInputEditText emailLoginTextView, passwordLoginTextView;
    private MaterialButton loginBtn;
    private FirebaseSingleton firebaseSingleton;
    private ProgressBar loginProgressBar;
    private PreferencesSingleton preferencesSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        preferencesSingleton = PreferencesSingleton.getInstance(getApplication());

        emailLoginTextView = findViewById(R.id.logInEmailMateriaEditTxt);
        emailLoginTextView.addTextChangedListener(this);
        passwordLoginTextView = findViewById(R.id.logInPassWordMateriaEditTxt);
        passwordLoginTextView.addTextChangedListener(this);
        loginBtn = findViewById(R.id.logInButton);
        MaterialButton signUpBtn = findViewById(R.id.loginSignUnButton);
        loginProgressBar = findViewById(R.id.logInProgressBar);

        loginBtn.setOnClickListener(this);
        signUpBtn.setOnClickListener(this);

        firebaseSingleton = FirebaseSingleton.getInstance();
        firebaseSingleton.attatchFirebaseAuthStateListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseSingleton.initializeAuthStateListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        firebaseSingleton.dettatchDatabaseValueEventListener();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logInButton:
                loginProgressBar.setVisibility(View.VISIBLE);
                firebaseSingleton.signIn(emailLoginTextView.getText().toString(), passwordLoginTextView.getText().toString());
                EventSingleton eventSingleton = EventSingleton.getInstance();
                eventSingleton.registerFirebaseEvent(new FirebaseEventListener() {
                    @Override
                    public void done(boolean isSuccessfull) {
                        if(isSuccessfull) {
                            signUp();
                        } else {
                            Toast.makeText(AppVendasLoginActivity.this, "email ou senha errado", Toast.LENGTH_SHORT).show();
                            loginProgressBar.setVisibility(View.GONE);
                        }
                    }
                });

                break;

            case R.id.loginSignUnButton:
                Intent intent = new Intent(this, AppVendasSignUpActivity.class);
                startActivityForResult(intent, SIGN_UP_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == SIGN_UP_CODE) {
            signUp();
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
        if(!emailLoginTextView.getText().toString().trim().equals("")
            && !passwordLoginTextView.getText().toString().trim().equals("")) {
            loginBtn.setEnabled(true);
        } else if(emailLoginTextView.getText().toString().trim().equals("")
                    || passwordLoginTextView.getText().toString().trim().equals("")) {
            loginBtn.setEnabled(false);
        }
    }

    private void signUp() {
//        preferencesSingleton.storeUserData(firebaseSingleton.getCurrentUser());
        Intent intent = new Intent(this, AppVendasMainActivity.class);
        startActivity(intent);
        finish();
    }

}
