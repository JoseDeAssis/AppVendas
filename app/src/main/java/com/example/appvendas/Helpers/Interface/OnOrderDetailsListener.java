package com.example.appvendas.Helpers.Interface;

import android.view.View;

import com.example.appvendas.Entity.ItemWithOrder;

public interface OnOrderDetailsListener {
    void getOrderOptionsDetails(ItemWithOrder itemWithOrder, View view);
}
