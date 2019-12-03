package com.example.appvendas.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.appvendas.Dao.ItemWithOrderDao;
import com.example.appvendas.Entity.ItemWithOrder;
import com.example.appvendas.Room.AppVendasRoomDatabase;

import java.util.List;

public class ItemWithOrderRepository {

    private ItemWithOrderDao itemWithOrderDao;

    public ItemWithOrderRepository(Application application) {
        AppVendasRoomDatabase db = AppVendasRoomDatabase.getDataBase(application);
        this.itemWithOrderDao = db.itemWithOrderDao();
    }

    public LiveData<List<ItemWithOrder>> getItemSummary() {
        return itemWithOrderDao.getItemSummary();
    }
}
