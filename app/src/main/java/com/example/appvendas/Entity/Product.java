package com.example.appvendas.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product_table")
public class Product {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    @ColumnInfo(name = "product_name")
    private String productName;

    @ColumnInfo(name = "description")
    private String productDescrition;

    @NonNull
    @ColumnInfo(name = "price")
    private double productPrice;

    @NonNull
    @ColumnInfo(name = "on_sale")
    private boolean onSaleProduct;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getProductName() {
        return productName;
    }

    public void setProductName(@NonNull String productName) {
        this.productName = productName;
    }

    public String getProductDescrition() {
        return productDescrition;
    }

    public void setProductDescrition(String productDescrition) {
        this.productDescrition = productDescrition;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public boolean isOnSaleProduct() {
        return onSaleProduct;
    }

    public void setOnSaleProduct(boolean onSaleProduct) {
        this.onSaleProduct = onSaleProduct;
    }
}
