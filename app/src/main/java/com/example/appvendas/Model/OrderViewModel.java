package com.example.appvendas.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.appvendas.Entity.Order;
import com.example.appvendas.Repository.OrderRepository;

import java.util.List;

public class OrderViewModel extends AndroidViewModel {

    private OrderRepository orderRepository;
    private LiveData<List<Order>> orderList;

    public OrderViewModel(Application application) {
        super(application);

        orderRepository = new OrderRepository(application);
        orderList = orderRepository.getAllOrders();
    }

    public LiveData<List<Order>> getAllOrders() {
        return orderList;
    }

    public void insert(Order order) {
        orderRepository.insert(order);
    }

    public void deleteById(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
