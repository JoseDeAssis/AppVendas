package com.example.appvendas.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.appvendas.Dao.OrderWithItemsDao;
import com.example.appvendas.Entity.OrderWithItems;
import com.example.appvendas.Room.AppVendasRoomDatabase;

import java.util.List;

public class OrderWithItemsRepository {

    private OrderWithItemsDao orderWithItemsDao;

    public OrderWithItemsRepository(Application application) {
        AppVendasRoomDatabase db = AppVendasRoomDatabase.getDataBase(application);
        orderWithItemsDao = db.orderWithItemsDao();
    }

    public LiveData<List<OrderWithItems>> getOrderSummary(){
        return orderWithItemsDao.getOrderSummary();
    }

}
