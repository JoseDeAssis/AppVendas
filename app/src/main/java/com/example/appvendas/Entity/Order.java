package com.example.appvendas.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "order_table")
public class Order {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "order_id")
    private long id;

    /*

    @NonNull
    @ColumnInfo(name = "client_id")
    private long client_id;

     */

    @NonNull
    @ColumnInfo(name = "order_date")
    private Date order_date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(@NonNull Date order_date) {
        this.order_date = order_date;
    }
}
