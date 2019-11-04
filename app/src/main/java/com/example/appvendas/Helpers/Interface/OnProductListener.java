package com.example.appvendas.Helpers.Interface;

import com.example.appvendas.Entity.Product;

public interface OnProductListener {
    void getProductDetails(Product product);
    void setProductChecked(Product product, boolean isChecked);
}
