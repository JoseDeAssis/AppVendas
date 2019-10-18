package com.example.appvendas.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appvendas.Adapter.ProductListRVAdapter;
import com.example.appvendas.Entity.Product;
import com.example.appvendas.Model.ProductViewModel;
import com.example.appvendas.R;

import java.util.List;

public class AppVendasHistoricoTab extends Fragment {

    private RecyclerView appVendasProdutosRecyclerView;
    private ProductViewModel appVendasProdutosViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_produtos_tab, container, false);

        appVendasProdutosRecyclerView = view.findViewById(R.id.produtosTabRecyclerView);
        final ProductListRVAdapter adapter = new ProductListRVAdapter(getContext());

        appVendasProdutosRecyclerView.setAdapter(adapter);
        appVendasProdutosRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        appVendasProdutosViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        appVendasProdutosViewModel.getAllProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                adapter.setProducts(products);
            }
        });

        return view;
    }

}
