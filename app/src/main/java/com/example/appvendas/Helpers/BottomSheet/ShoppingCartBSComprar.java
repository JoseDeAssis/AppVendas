package com.example.appvendas.Helpers.BottomSheet;

import android.content.Context;
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
    private MaterialCardView gmailCardView, whatsappCardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.shopping_cart_bottom_sheet_comprar, container, false);

        gmailCardView = view.findViewById(R.id.gmailCardView);
        gmailCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        whatsappCardView = view.findViewById(R.id.whatsappCardView);
        whatsappCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return view;
    }

    public interface BottomSheetListener {
        void onBuyBtnClicked();
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//        try {
//            listener = (BottomSheetListener) context;
//        } catch (ClassCastException e) {
//            e.printStackTrace();
//        }
//    }
}
