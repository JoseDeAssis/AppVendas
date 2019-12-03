package com.example.appvendas.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.appvendas.Dao.OrderDao;
import com.example.appvendas.Entity.Order;
import com.example.appvendas.Helpers.Singleton.EventSingleton;
import com.example.appvendas.Room.AppVendasRoomDatabase;

import java.util.List;

public class OrderRepository {

    private OrderDao orderDao;
    private LiveData<List<Order>> orderList;

    public OrderRepository(Application application) {
        AppVendasRoomDatabase db = AppVendasRoomDatabase.getDataBase(application);
        orderDao = db.orderDao();
        orderList = orderDao.getAllOrders();
    }

    public LiveData<List<Order>> getAllOrders() {
        return orderList;
    }

    public void insert(Order order) {
        new OrderRepository.insertAsyncTask(orderDao).execute(order);
    }

    public void deleteById(Long orderId) {
        new OrderRepository.deleteAsyncTask(orderDao).execute(orderId);
    }

    private static class insertAsyncTask extends AsyncTask<Order, Void, Long> {

        private OrderDao mAsyncTaskDao;

        insertAsyncTask(OrderDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Long doInBackground(Order... orders) {
            return mAsyncTaskDao.insertOrder(orders[0]);
        }

        @Override
        protected void onPostExecute(Long result) {
            super.onPostExecute(result);
            System.out.println(result);
            EventSingleton eventSingleton = EventSingleton.getInstance();
            eventSingleton.emitterDone(result);
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Long, Void, Integer> {

        private OrderDao mAsyncTaskDao;

        deleteAsyncTask(OrderDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Integer doInBackground(final Long... longs) {
            return mAsyncTaskDao.deleteById(longs[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }
    }
}
