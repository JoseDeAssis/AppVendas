package com.example.appvendas.Holder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appvendas.R;

public class ProductViewHolder extends RecyclerView.ViewHolder  {

    private final TextView textView;

    public ProductViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.recyclerViewMainText);
    }
}
