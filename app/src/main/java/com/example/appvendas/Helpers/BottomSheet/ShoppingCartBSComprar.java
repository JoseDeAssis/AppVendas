package com.example.appvendas.Helpers.BottomSheet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.example.appvendas.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.card.MaterialCardView;

public class ShoppingCartBSComprar extends BottomSheetDialogFragment {

    private BottomSheetListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.shopping_cart_bottom_sheet_comprar, container, false);

        MaterialCardView gmailCardView = view.findViewById(R.id.gmailCardView);
        gmailCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onBuyBtnClicked("gmail");

                dismiss();
            }
        });

        MaterialCardView whatsappCardView = view.findViewById(R.id.whatsappCardView);
        whatsappCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onBuyBtnClicked("whatsapp");
                dismiss();
            }
        });

        return view;
    }

    public interface BottomSheetListener {
        void onBuyBtnClicked(String shareApp);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}
