package com.jyora.cs.impl;

import com.jyora.cs.domain.Inventory;
import com.jyora.cs.service.InventoryService;
import lombok.extern.log4j.Log4j2;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Log4j2
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Document createInventory(Document document) {

        Document inventory = mongoTemplate.insert(document,"Inventory");

        return inventory;
    }

    @Override
    public List<Document> getInventory(Integer inventoryId) {

        Criteria crit = Criteria.where("inventoryId").is(inventoryId);
        Query query = new Query(crit);
        query.fields().exclude("_id");
        List<Document> inventory = mongoTemplate.find(query, Document.class,"Inventory");
        return inventory;

    }

    @Override
    public Document deleteInventory(Integer inventoryId) {

        Criteria crit = Criteria.where("inventoryId").is(inventoryId);
        Query query = new Query(crit);
        query.fields().exclude("_id");
        Document inventory = mongoTemplate.findAndRemove(query,Document.class,"Inventory");
        return inventory;

    }

    @Override
    public Inventory modifyInventory(Integer inventoryId, Inventory inventory) {

        Criteria crit = Criteria.where("inventoryId").is(inventoryId);
        Query query = new Query(crit);
        query.fields().exclude("_id");
        Update update = new Update();
        update.set("products", inventory.getProducts());
        update.set("inventoryId", inventory.getInventoryId());
        log.info("Inside modifyProduct product  before calling modify ");
        Inventory inventory1 = mongoTemplate.findAndModify(query, update,new FindAndModifyOptions().returnNew(true),Inventory.class,"Inventory");
        return inventory1;

    }
}
