package com.example.appvendas.Entity;

import androidx.room.Embedded;
import androidx.room.Relation;

public class ItemWithOrder {

    @Embedded
    private Item item;

    @Relation(
            parentColumn = "order_id",
            entityColumn = "order_id",
            entity = Order.class)
    private Order order;

    @Relation(
            parentColumn = "product_id",
            entityColumn = "product_id",
            entity = Product.class)
    private Product product;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
