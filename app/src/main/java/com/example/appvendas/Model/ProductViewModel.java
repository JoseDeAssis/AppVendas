package com.example.appvendas.Model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.appvendas.Entity.Product;
import com.example.appvendas.Repository.ProductRepository;

import java.util.HashMap;
import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository produtoRepository;
    private LiveData<List<Product>> productList, hotProductList, allProductList;
    private HashMap<Long, Product> shoppingCartList;

    public ProductViewModel(Application application) {
        super(application);
        produtoRepository = new ProductRepository(application);
        allProductList = produtoRepository.getAllProducts();
        productList = produtoRepository.getProducts();
        hotProductList = produtoRepository.getAllHotProducts();
        shoppingCartList = new HashMap<>();
    }

    public LiveData<List<Product>> getAllProducts() {
        return allProductList;
    }

    public LiveData<List<Product>> getProducts() {
        return productList;
    }

    public LiveData<List<Product>> getAllHotProducts() {
        return hotProductList;
    }

    public HashMap<Long, Product> getShoppingCartList() {
        return shoppingCartList;
    }

    public void addProductToShoppingCart(Product product, boolean isChecked) {
        if(isChecked)
            shoppingCartList.put(product.getId(), product);
        else
            shoppingCartList.remove(product.getId());
    }

    public void insert(Product produto) {
        produtoRepository.insert(produto);
    }
}
