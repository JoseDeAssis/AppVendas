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

    @NonNull
    @ColumnInfo(name = "order_date")
    Date order_date;

}
