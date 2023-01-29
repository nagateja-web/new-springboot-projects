package com.jyora.cs.service;

import com.jyora.cs.domain.Inventory;
import org.bson.Document;
import java.util.List;

public interface InventoryService {

    Document createInventory(Document document);
    List<Document> getInventory(Integer inventoryId);
    Document deleteInventory(Integer inventoryId);
    Inventory modifyInventory(Integer inventoryId, Inventory inventory);

}
