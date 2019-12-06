package com.example.appvendas.Model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.appvendas.Entity.Product;
import com.example.appvendas.Repository.ProductRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository productRepository;
    private LiveData<List<Product>> productList, hotProductList, allProductList, availableProductList;
    private HashMap<Long, Product> shoppingCartList;
    private ArrayList<Long> productsList = new ArrayList<>();

    public ProductViewModel(Application application) {
        super(application);
        productRepository = new ProductRepository(application);
        allProductList = productRepository.getAllProducts();
        productList = productRepository.getProducts();
        hotProductList = productRepository.getAllHotProducts();
        availableProductList = productRepository.getAvailableProducts();
        shoppingCartList = new HashMap<>();
    }

    public LiveData<List<Product>> getAllProducts() {
        return allProductList;
    }

    public LiveData<List<Product>> getProducts() {
        return productList;
    }

    public LiveData<List<Product>> getAvailableProducts() {
        return availableProductList;
    }

    public LiveData<List<Product>> getAllHotProducts() {
        return hotProductList;
    }

    public HashMap<Long, Product> getShoppingCartList() {
        return shoppingCartList;
    }

    public ArrayList<Long> getProductsList() {
        return productsList;
    }

    public void addProductToShoppingCart(Product product, boolean isChecked) {
        if(isChecked) {
            shoppingCartList.put(product.getId(), product);
            productsList.add(product.getId());
        } else {
            shoppingCartList.remove(product.getId());
            productsList.remove(product.getId());
        }
    }

    public void insert(Product product) {
        productRepository.insert(product);
    }

    public void update(Product product) {
        productRepository.update(product);
    }

    public void delete(Product product) {
        productRepository.delete(product);
    }

    public void toggleProductAvailability(Product product) {
        if(product.getOnAvailableProduct() == 0)
            product.setOnAvailableProduct(1);
        else
            product.setOnAvailableProduct(0);

        productRepository.update(product);
    }

}
