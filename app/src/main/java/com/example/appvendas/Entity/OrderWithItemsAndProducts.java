package com.example.appvendas.Entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class OrderWithItemsAndProducts {

    @Embedded
    private Order order;

    @Relation(
            parentColumn = "order_id",
            entityColumn = "order_id",
            entity = Item.class)
    private List<ItemWithProducts> itemWithProductsList;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<ItemWithProducts> getItemWithProductsList() {
        return itemWithProductsList;
    }

    public void setItemWithProductsList(List<ItemWithProducts> itemWithProductsList) {
        this.itemWithProductsList = itemWithProductsList;
    }
}
