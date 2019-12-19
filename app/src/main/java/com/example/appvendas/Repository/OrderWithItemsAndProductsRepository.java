package com.example.appvendas.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.appvendas.Dao.OrderWithItemsAndProductsDao;
import com.example.appvendas.Entity.ItemWithProducts;
import com.example.appvendas.Entity.OrderWithItemsAndProducts;
import com.example.appvendas.Room.AppVendasRoomDatabase;

import java.util.List;

public class OrderWithItemsAndProductsRepository {

    private OrderWithItemsAndProductsDao orderWithItemsAndProductsDao;

    public OrderWithItemsAndProductsRepository(Application application) {
        AppVendasRoomDatabase db = AppVendasRoomDatabase.getDataBase(application);
        this.orderWithItemsAndProductsDao = db.orderWithItemsAndProductsDao();
    }

    public LiveData<List<OrderWithItemsAndProducts>> getOrderItems(){
        return orderWithItemsAndProductsDao.getAllOrderWithItems();
    }

    public LiveData<OrderWithItemsAndProducts> getItemSummaryByOrderId(Long orderId) {
        return orderWithItemsAndProductsDao.getItemSummaryByOrderId(orderId);
    }
}
