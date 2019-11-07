package com.example.appvendas.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.appvendas.Entity.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShoppingCartViewModel extends AndroidViewModel {

    private List<Product> shoppingCartList;
    private Integer[] productsQuantities;
    private Double totalPrice;

    public ShoppingCartViewModel(@NonNull Application application) {
        super(application);
        shoppingCartList = new ArrayList<>();
        totalPrice = 0.0;
    }

    public Integer[] initializeQuantities(int size){
        if (productsQuantities == null){
            this.productsQuantities = new Integer[size];
            Arrays.fill(this.productsQuantities, 1);
        }
        return productsQuantities;
    }

    public Integer[] getProductsQuantities() {
        return productsQuantities;
    }

    public void setProductsQuantity(Integer position, Integer count) {
        this.productsQuantities[position] = count;
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
