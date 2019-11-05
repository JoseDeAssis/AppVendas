package com.example.appvendas.Helpers.Interface;

import com.example.appvendas.Entity.Product;

public interface OnProductIsCheckedListener {
    void setProductChecked(Product product, boolean isChecked);
}
