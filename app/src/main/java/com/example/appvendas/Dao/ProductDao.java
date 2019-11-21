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
    int deleteProduct(Product produto);

    @Update
    int updateProduct(Product produto);

    @Query("SELECT * from product_table ORDER BY product_id ASC")
    LiveData<List<Product>> getAllAlphabetizedProducts();

    @Query("SELECT * from product_table where product_on_sale == 0 ORDER BY product_id ASC")
    LiveData<List<Product>> getAlphabetizedProducts();

    @Query(("SELECT * from product_table where product_on_sale == 1 ORDER BY product_id ASC"))
    LiveData<List<Product>> getAlphabetizedHotProducts();

}
