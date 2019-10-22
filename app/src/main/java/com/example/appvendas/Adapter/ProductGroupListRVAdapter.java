package com.example.appvendas.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appvendas.Entity.ProductGroup;
import com.example.appvendas.R;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class ProductGroupListRVAdapter extends RecyclerView.Adapter<ProductGroupListRVAdapter.ProductGroupListRVViewHolder> {

    private final LayoutInflater mInflater;
    private ProductGroup[] productGroupList;

    public class ProductGroupListRVViewHolder extends RecyclerView.ViewHolder  {

        private final TextView textView;
        private final ImageView productGroupImageView, selectedImageView;
        private final MaterialCardView productGroupCardView;

        private ProductGroupListRVViewHolder(View itemView) {
            super(itemView);
            productGroupCardView = itemView.findViewById(R.id.productGroupRVCardView);
            textView = itemView.findViewById(R.id.productGroupRVTextView);
            productGroupImageView = itemView.findViewById(R.id.productGroupRVImgView);
            selectedImageView = itemView.findViewById(R.id.productGroupRVSelectedImgView);

            productGroupCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(ProductGroup productGroup: productGroupList) {
                        if(productGroup.getGroupName().equals(textView.getText().toString())) {
                            productGroup.setSelected(true);
                            selectedImageView.setVisibility(View.VISIBLE);
                        } else if(productGroup.isSelected()) {
                            productGroup.setSelected(false);
                            selectedImageView.setVisibility(View.GONE);
                        }
                    }
                }
            });
        }
    }

    public ProductGroupListRVAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ProductGroupListRVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.app_vendas_rv_product_group_list, parent, false);
        return new ProductGroupListRVAdapter.ProductGroupListRVViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductGroupListRVViewHolder holder, int position) {
        if (productGroupList != null) {
            ProductGroup current = productGroupList[position];
            holder.textView.setText(current.getGroupName());
            holder.productGroupImageView.setImageResource(current.getGroupImg());
            holder.selectedImageView.setVisibility(current.isSelected() ? View.VISIBLE : View.GONE);
        } else {
            // Covers the case of data not being ready yet.
            holder.textView.setText("Não há produtos registrados");
        }
    }

    public void setProductGroupList(ProductGroup[] productGroups){
        productGroupList = productGroups;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (productGroupList != null)
            return productGroupList.length;
        else return 0;
    }

}
