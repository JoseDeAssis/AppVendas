package com.example.appvendas.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appvendas.Entity.Product;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insertProduct(Product produto);

    @Delete
    void deleteProduct(Product produto);

    @Update
    void updateProduct(Product produto);

    @Query("SELECT * from product_table ORDER BY id ASC")
    LiveData<List<Product>> getAlphabetizedProducts();

}
