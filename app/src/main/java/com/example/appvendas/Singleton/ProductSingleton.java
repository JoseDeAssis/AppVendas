package com.example.appvendas.Singleton;

import com.example.appvendas.Interface.RoomFinished;

import java.util.EventListener;

public class ProductSingleton {

    public static volatile ProductSingleton instance;

    public ProductSingleton getInstance() {
        if(instance == null)
            instance = new ProductSingleton();
        return instance;
    }

    public void EventListener(RoomFinished roomFinished) {

    }
}
