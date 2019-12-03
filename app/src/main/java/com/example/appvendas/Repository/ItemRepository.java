package com.example.appvendas.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.appvendas.Dao.ItemDao;
import com.example.appvendas.Entity.Item;
import com.example.appvendas.Entity.Order;
import com.example.appvendas.Room.AppVendasRoomDatabase;

import java.util.ArrayList;
import java.util.List;

public class ItemRepository {

    private ItemDao itemDao;

    public ItemRepository(Application application) {
        AppVendasRoomDatabase db = AppVendasRoomDatabase.getDataBase(application);
        itemDao = db.itemDao();
    }

    public LiveData<List<Item>> getOrderItems(List<Order> orderList) {
        List<Long> listIds = new ArrayList<>();

        for(Order order: orderList) {
            listIds.add(order.getId());
        }

        return itemDao.getOrderItems(listIds);
    }

    public void insert(Item item) {
        new ItemRepository.InsertAsyncTask(itemDao).execute(item);
    }

    private static class InsertAsyncTask extends AsyncTask<Item, Void, Long> {
        private ItemDao itemDao;

        public InsertAsyncTask(ItemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Long doInBackground(Item... items) {
            return itemDao.insert(items[0]);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            System.out.println(aLong);
        }
    }
}
