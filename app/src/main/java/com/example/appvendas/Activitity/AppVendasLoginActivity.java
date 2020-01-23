package com.example.appvendas.Activitity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.appvendas.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AppVendasLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText emailLoginTextView, passwordLoginTextView;
    private MaterialButton loginBtn, signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailLoginTextView = findViewById(R.id.logInEmailMateriaEditTxt);
        passwordLoginTextView = findViewById(R.id.logInPassWordMateriaEditTxt);
        loginBtn = findViewById(R.id.logInButton);
        signUpBtn = findViewById(R.id.loginSignUnButton);

        loginBtn.setOnClickListener(this);
        signUpBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logInButton:
                break;

            case R.id.loginSignUnButton:
                Intent intent = new Intent(this, AppVendasSignUpActivity.class);
                startActivity(intent);
                break;
        }
    }
}
