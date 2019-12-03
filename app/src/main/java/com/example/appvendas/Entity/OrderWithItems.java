package com.example.appvendas.Entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class OrderWithItems {

    @Embedded
    public Order order;

    @Relation(
            parentColumn = "order_id",
            entityColumn = "order_id",
            entity = Item.class)
    private List<Item> items;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
