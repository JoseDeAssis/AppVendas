package com.example.appvendas.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appvendas.Entity.Order;

import java.util.List;

@Dao
public interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insertOrder(Order order);

    @Delete
    int deleteOrder(Order order);

    @Update
    int updateOrder(Order order);

    @Query("DELETE FROM order_table WHERE order_id == :id")
    int deleteById(long id);

    @Query("SELECT * FROM order_table ORDER BY order_date DESC")
    LiveData<List<Order>> getAllOrders();
}
