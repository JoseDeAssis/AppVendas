package com.example.appvendas.Helpers.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.example.appvendas.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ShoppingCartQuantityDialog extends AppCompatDialogFragment implements TextWatcher {

    private TextInputEditText quantityEditTxt;
    private TextInputLayout quantityInputLayout;
    private MaterialButton quantityPositiveBtn, quantityNegativeBtn;
    private shoppingCartQuantityDialogListener listener;

    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.shopping_cart_more_items_dialog, null);
        final Long productId = getArguments().getLong("productId");

        quantityEditTxt = view.findViewById(R.id.shoppingCartQuantityDialogTxtView);
        quantityEditTxt.addTextChangedListener(this);
        quantityInputLayout = view.findViewById(R.id.shoppingCartQuantityInputLayout);

        quantityPositiveBtn = view.findViewById(R.id.shoppingCartQuantityPositiveBtn);
        quantityPositiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(quantityEditTxt.getText().toString());
                if (validate(quantity)) {
                    listener.applyQuantity(quantity, productId);
                    getDialog().dismiss();
                } else {
                    if (quantity > 100) {
                        listener.applyQuantity(quantity, productId);
                        Intent intent = new Intent();
                    } else {
                        quantityInputLayout.setError("quantidade inválida!");
                        quantityEditTxt.setText("");
                        quantityEditTxt.requestFocus();
                        quantityInputLayout.setErrorEnabled(true);
                    }
                }
            }
        });

        quantityNegativeBtn = view.findViewById(R.id.shoppingCartQuantityNegativeBtn);
        quantityNegativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        builder.setView(view)
                .setTitle("Quantidade");

        return builder.create();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Theme_MaterialComponents_Light_Dialog);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (shoppingCartQuantityDialogListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validate(int quantity) {
        if(quantity <= 0 || quantity > 100) {
            return false;
        }
        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(!quantityEditTxt.getText().toString().trim().equals("")) {
            if(quantityInputLayout.isErrorEnabled())
                quantityInputLayout.setErrorEnabled(false);

            quantityPositiveBtn.setEnabled(true);
        } else if(quantityEditTxt.getText().toString().trim().equals("")) {
            quantityPositiveBtn.setEnabled(false);
        }
    }

    public interface shoppingCartQuantityDialogListener {
        void applyQuantity(int quantity, Long productId);
    }
}




