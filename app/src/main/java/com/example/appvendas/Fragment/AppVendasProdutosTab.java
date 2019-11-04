package com.example.appvendas.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appvendas.Adapter.ProductListRVAdapter;
import com.example.appvendas.Entity.Product;
import com.example.appvendas.Helpers.Interface.OnProductListener;
import com.example.appvendas.Model.ProductViewModel;
import com.example.appvendas.R;

import java.util.List;

public class AppVendasProdutosTab extends Fragment implements OnProductListener {

    private RecyclerView appVendasProdutosRecyclerView;
    private ProductViewModel appVendasProdutosViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_produtos_tab, container, false);

        appVendasProdutosRecyclerView = view.findViewById(R.id.produtosTabRecyclerView);
        final ProductListRVAdapter adapter = new ProductListRVAdapter(getContext(), this);

        appVendasProdutosRecyclerView.setAdapter(adapter);
        appVendasProdutosRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        appVendasProdutosViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        appVendasProdutosViewModel.getProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                adapter.setProducts(products);
            }
        });

        return view;
    }

    @Override
    public void getProductDetails(Product product) {
        Intent intent = new Intent();
        intent.putExtra("productName", product.getProductName());
        intent.putExtra("productDescription", product.getProductDescrition());
        intent.putExtra("productId", product.getId());
        intent.putExtra("productPrice", product.getProductPrice());
    }

    @Override
    public void setProductChecked(Product product, boolean isChecked) {
        appVendasProdutosViewModel.addProductToShoppingCart(product, isChecked);
    }
}
