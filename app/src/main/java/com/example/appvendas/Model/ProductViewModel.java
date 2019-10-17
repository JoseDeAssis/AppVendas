package com.example.appvendas.Model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.appvendas.Entity.Product;
import com.example.appvendas.Repository.ProductRepository;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository produtoRepository;
    private LiveData<List<Product>> productList;

    public ProductViewModel(Application application) {
        super(application);
        produtoRepository = new ProductRepository(application);
        productList = produtoRepository.getAllProducts();
    }

    public LiveData<List<Product>> getAllProducts() {
        return productList;
    }

    public void insert(Product produto) {
        produtoRepository.insert(produto);
    }
}
