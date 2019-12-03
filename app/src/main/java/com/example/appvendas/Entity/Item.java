package com.example.appvendas.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.SET_DEFAULT;

@Entity(tableName = "item_table",
        primaryKeys = {"order_id", "product_id"},
        foreignKeys = { @ForeignKey(entity = Order.class,
                parentColumns = "order_id",
                childColumns = "order_id",
                onDelete = CASCADE),
                @ForeignKey(entity = Product.class,
                        parentColumns = "product_id",
                        childColumns = "product_id",
                        onDelete = SET_DEFAULT)
        })
public class Item {

    @ColumnInfo(index = true, name = "order_id")
    private long orderId;

    @ColumnInfo(index = true, name = "product_id")
    private long productId;

    @NonNull
    @ColumnInfo(name = "item_quantity")
    private int quantity;

    @NonNull
    @ColumnInfo(name = "item_price")
    private double itemPrice;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }
}
