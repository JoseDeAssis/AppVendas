package com.example.appvendas.Helpers.Interface;

import android.view.View;

import com.example.appvendas.Entity.Product;

public interface OnShoppingCartListener {
    void deleteItem(Product product);
    void modifyQuantity(Long productId, View view);
    void modifyTotalPrice();
}
