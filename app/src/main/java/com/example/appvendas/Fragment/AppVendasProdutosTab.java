package com.example.appvendas.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appvendas.Activitity.AppVendasProductDetail;
import com.example.appvendas.Adapter.ProductListRVAdapter;
import com.example.appvendas.Entity.Product;
import com.example.appvendas.Helpers.Interface.OnProductDetailsListener;
import com.example.appvendas.Helpers.Interface.OnProductIsCheckedListener;
import com.example.appvendas.Model.ProductViewModel;
import com.example.appvendas.R;

import java.util.List;

public class AppVendasProdutosTab extends Fragment implements OnProductDetailsListener, OnProductIsCheckedListener {

    private RecyclerView appVendasProdutosRecyclerView;
    private ProductViewModel appVendasProdutosViewModel;
    private static final int PRODUCT_DETAIL_RESULT_CODE = 1000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_produtos_tab, container, false);

        appVendasProdutosRecyclerView = view.findViewById(R.id.produtosTabRecyclerView);
        final ProductListRVAdapter adapter = new ProductListRVAdapter(getContext(), this, this);

        appVendasProdutosRecyclerView.setAdapter(adapter);
        appVendasProdutosRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        appVendasProdutosViewModel = ViewModelProviders.of(getActivity()).get(ProductViewModel.class);
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
        Intent intent = new Intent(getContext(), AppVendasProductDetail.class);
        intent.putExtra("productName", product.getProductName());
        intent.putExtra("productDescription", product.getProductDescrition());
        intent.putExtra("productId", product.getId());
        intent.putExtra("productPrice", product.getProductPrice());
        intent.putExtra("productGroup", product.getProductGroup());
        intent.putExtra("productOnSale", product.getOnSaleProduct());

        startActivityForResult(intent, PRODUCT_DETAIL_RESULT_CODE);
    }

    @Override
    public void setProductChecked(Product product, boolean isChecked) {
        appVendasProdutosViewModel.addProductToShoppingCart(product, isChecked);
    }
}
