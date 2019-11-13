package com.example.appvendas.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.appvendas.Entity.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShoppingCartViewModel extends AndroidViewModel {

    private List<Product> shoppingCartList;
    private HashMap<Long, Integer> productsQuantities;
    private Double totalPrice;

    public ShoppingCartViewModel(@NonNull Application application) {
        super(application);
        shoppingCartList = new ArrayList<>();
        productsQuantities = new HashMap<>();
    }

    public HashMap<Long, Integer> initializeQuantities(){
        if (productsQuantities == null){
            for(Product product: shoppingCartList){
                productsQuantities.put(product.getId(), 1);
            }
        }
        return productsQuantities;
    }

    public void initializeTotal() {
        for(Product product: shoppingCartList) {

        }
    }

    public HashMap<Long, Integer> getProductsQuantities() {
        return productsQuantities;
    }

    public void setProductQuantity(Long id, Integer count) {
        this.productsQuantities.put(id, count);
    }

    public List<Product> getShoppingCartList() {
        return shoppingCartList;
    }

    public void setShoppingCartList(List<Product> shoppingCartList) {
        this.shoppingCartList = shoppingCartList;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void deleteProductFromShoppingCart(Product product) {
        this.shoppingCartList.remove(product);
    }
}
