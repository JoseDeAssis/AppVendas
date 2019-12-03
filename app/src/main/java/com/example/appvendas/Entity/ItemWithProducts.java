package com.example.appvendas.Entity;

import androidx.room.Embedded;
import androidx.room.Relation;

public class ItemWithProducts {

    @Embedded
    private Item item;

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
