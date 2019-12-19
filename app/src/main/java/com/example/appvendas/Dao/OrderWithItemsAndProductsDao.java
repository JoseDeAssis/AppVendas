package com.example.appvendas.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.appvendas.Entity.ItemWithProducts;
import com.example.appvendas.Entity.OrderWithItemsAndProducts;

import java.util.List;

@Dao
public interface OrderWithItemsAndProductsDao {

    @Transaction
    @Query("SELECT * FROM order_table")
    LiveData<List<OrderWithItemsAndProducts>> getAllOrderWithItems();

    @Transaction
    @Query("SELECT * FROM order_table WHERE order_id == :orderId")
    LiveData<OrderWithItemsAndProducts> getItemSummaryByOrderId(Long orderId);
}
