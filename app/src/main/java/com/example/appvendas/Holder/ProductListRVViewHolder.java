package com.example.appvendas.Holder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appvendas.R;

public class ProductListRVViewHolder extends RecyclerView.ViewHolder  {

    private final TextView textView;
    private final ImageView imageView;
    private final CheckBox checkBox;

    public ProductListRVViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.recyclerViewMainText);
        imageView = itemView.findViewById(R.id.recyclerViewImg);
        checkBox = itemView.findViewById(R.id.recyclerViewCheckBox);
    }
}
