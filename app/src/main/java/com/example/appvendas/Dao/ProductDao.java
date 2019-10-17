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
    public void insertProduct(Product produto);

    @Delete
    public void deleteProduct(Product produto);

    @Update
    public void updateProduct(Product produto);

    @Query("SELECT * from Product ORDER BY id ASC")
    public LiveData<List<Product>> getAlphabetizedProducts();

}
