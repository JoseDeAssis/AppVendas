package com.example.appvendas.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.appvendas.Entity.OrderWithItems;

import java.util.List;

@Dao
public interface OrderWithItemsDao {

    @Transaction
    @Query("SELECT * FROM order_table ORDER BY order_date DESC")
    LiveData<List<OrderWithItems>> getOrderSummary();

}
