package com.example.appvendas.Helpers.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.appvendas.R;
import com.google.android.material.textfield.TextInputEditText;

public class ShoppingCartQuantityDialog extends AppCompatDialogFragment {

    private TextInputEditText quantityEditTxt;
    private shoppingCartQuantityDialogListener listener;

    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.shopping_cart_more_items_dialog, null);
        quantityEditTxt = view.findViewById(R.id.shoppingCartQuantityDialogTxtView);
        final Long productId = getArguments().getLong("productId");

        builder.setView(view)
                .setTitle("Quantidade")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Alterar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int quantity = Integer.parseInt(quantityEditTxt.getText().toString());
                        listener.applyQuantity(quantity, productId);
                    }
                });

        return builder.create();
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

    public interface shoppingCartQuantityDialogListener {
        void applyQuantity(int quantity, Long productId);
    }
}




