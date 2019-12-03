package com.example.appvendas.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appvendas.Entity.Item;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert
    Long insert(Item item);

    @Update
    void update(Item... items);

    @Delete
    void delete(Item... items);

    @Query("SELECT * FROM item_table WHERE order_id in (:order_ids)")
    LiveData<List<Item>> getOrderItems(List<Long> order_ids);

}
