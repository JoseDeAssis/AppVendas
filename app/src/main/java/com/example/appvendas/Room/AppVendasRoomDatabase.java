package com.example.appvendas.Room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.appvendas.Dao.ItemDao;
import com.example.appvendas.Dao.ItemWithOrderDao;
import com.example.appvendas.Dao.OrderDao;
import com.example.appvendas.Dao.OrderWithItemsAndProductsDao;
import com.example.appvendas.Dao.OrderWithItemsDao;
import com.example.appvendas.Dao.ProductDao;
import com.example.appvendas.Entity.Item;
import com.example.appvendas.Entity.Order;
import com.example.appvendas.Entity.Product;

import java.util.Date;

@Database(entities = {Product.class, Order.class, Item.class}, version = 1, exportSchema = false)
@TypeConverters(AppVendasRoomDatabase.DateConverter.class)
public abstract class AppVendasRoomDatabase extends RoomDatabase {

    public abstract ProductDao produtoDao();
    public abstract OrderDao orderDao();
    public abstract ItemDao itemDao();
    public abstract OrderWithItemsDao orderWithItemsDao();
    public abstract OrderWithItemsAndProductsDao orderWithItemsAndProductsDao();
    public abstract ItemWithOrderDao itemWithOrderDao();
    public static volatile AppVendasRoomDatabase INSTANCE;


    public static synchronized AppVendasRoomDatabase getDataBase(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppVendasRoomDatabase.class, "product_database").addCallback(sRoomDatabaseCallBack).build();
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallBack = new RoomDatabase.Callback() {
        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
        }
    };

    public static class DateConverter {

        @TypeConverter
        public Date toDate(Long dateLong) {
            return dateLong == null ? null : new Date(dateLong);
        }

        @TypeConverter
        public  Long toLong(Date date) {
            return date == null ? null : date.getTime();
        }
    }

}
