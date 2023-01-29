package com.jyora.cs.domain;

import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document
public class Inventory {

    private List<Product> products;
    private Integer inventoryId;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Integer getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "products=" + products +
                ", inventoryId=" + inventoryId +
                '}';
    }
}
