package com.example.appvendas.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "product_table")
public class Product implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "product_id")
    private long id;

    @NonNull
    @ColumnInfo(name = "product_name")
    private String productName;

    @NonNull
    @ColumnInfo(name = "product_code")
    private String productCode;

    @ColumnInfo(name = "product_description")
    private String productDescrition;

    @NonNull
    @ColumnInfo(name = "product_group")
    private String productGroup;

    @NonNull
    @ColumnInfo(name = "product_price")
    private double productPrice;

    @NonNull
    @ColumnInfo(name = "product_on_sale")
    private int onSaleProduct;

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

    @NonNull
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(@NonNull String productCode) {
        this.productCode = productCode;
    }

    public String getProductDescrition() {
        return productDescrition;
    }

    public void setProductDescrition(String productDescrition) {
        this.productDescrition = productDescrition;
    }

    @NonNull
    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(@NonNull String productGroup) {
        this.productGroup = productGroup;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getOnSaleProduct() {
        return onSaleProduct;
    }

    public void setOnSaleProduct(int onSaleProduct) {
        this.onSaleProduct = onSaleProduct;
    }
}
