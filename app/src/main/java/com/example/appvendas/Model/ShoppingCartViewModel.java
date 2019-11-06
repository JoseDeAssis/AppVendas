package com.example.appvendas.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.appvendas.Entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartViewModel extends AndroidViewModel {

    private List<Product> shoppingCartList;
    private Double totalPrice;

    public ShoppingCartViewModel(@NonNull Application application) {
        super(application);
        shoppingCartList = new ArrayList<>();
        totalPrice = 0.0;
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
