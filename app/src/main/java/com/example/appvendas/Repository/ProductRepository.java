package com.example.appvendas.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.appvendas.Dao.ProductDao;
import com.example.appvendas.Entity.Product;
import com.example.appvendas.Room.ProductRoomDatabase;
import com.example.appvendas.Helpers.Singleton.EventSingleton;

import java.util.List;

public class ProductRepository {

    private ProductDao productDao;
    private LiveData<List<Product>> productList, hotProductList, allProductList;

    public ProductRepository(Application application){
        ProductRoomDatabase db = ProductRoomDatabase.getDataBase(application);
        productDao = db.produtoDao();
        allProductList = productDao.getAllAlphabetizedProducts();
        productList = productDao.getAlphabetizedProducts();
        hotProductList = productDao.getAlphabetizedHotProducts();
    }

    public LiveData<List<Product>> getAllProducts() {
        return allProductList;
    }

    public LiveData<List<Product>> getAllHotProducts() {
        return hotProductList;
    }

    public LiveData<List<Product>> getProducts() {
        return productList;
    }

    public void insert (Product word) {
        new insertAsyncTask(productDao).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<Product, Void, Long> {

        private ProductDao mAsyncTaskDao;

        insertAsyncTask(ProductDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Long doInBackground(final Product... params) {
            return mAsyncTaskDao.insertProduct(params[0]);
        }

        @Override
        protected void onPostExecute(Long result) {
            super.onPostExecute(result);
            EventSingleton eventSingleton = EventSingleton.getInstance();
            eventSingleton.emitterDone(result);
        }
    }
}
