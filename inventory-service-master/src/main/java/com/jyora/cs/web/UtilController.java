package com.jyora.cs.web;

import com.jyora.cs.domain.Inventory;
import com.jyora.cs.service.InventoryService;
import lombok.extern.log4j.Log4j2;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@Log4j2
@RequestMapping("util")
public class UtilController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostMapping("/create-bulk-inventories")
    public com.jyora.cs.domain.ResponseEntity createAllInventories(@RequestBody Inventory inventory, @RequestParam Integer noOfInventoriesTobeCreated) {

        try {
            List<Document> createdInventories = new ArrayList<>();
            for (int i = 0; i < noOfInventoriesTobeCreated; i++) {
                Document document = new Document();
                document.put("products", inventory.getProducts());
                document.put("inventoryId", inventory.getInventoryId());
                Document createdInventory = inventoryService.createInventory(document);
                createdInventories.add(createdInventory);
            }
            if(createdInventories == null){
                log.info("Inside createAllInventories inventories not created ");
                return new com.jyora.cs.domain.ResponseEntity("Inventories not created", HttpStatus.OK.toString());
            }
            else{
                log.info("End createAllInventories inventories created: " + createdInventories.toString());
                return new com.jyora.cs.domain.ResponseEntity("Totally " + createdInventories.size() + " Inventories created Sucessfully.", HttpStatus.OK.toString());
            }

        } catch (Exception e) {
            return new com.jyora.cs.domain.ResponseEntity("Creating inventories Failed " + e, HttpStatus.EXPECTATION_FAILED.toString());
        }
    }

    @DeleteMapping("/delete-bulk-inventories")
    public com.jyora.cs.domain.ResponseEntity DeleteAllInventories() {
        try {
            List<Document> inventories = mongoTemplate.findAllAndRemove(new Query(), Document.class, "Inventory");

            if (inventories == null) {
                log.info("Inside DeleteAllInventories inventories not deleted ");
                return new com.jyora.cs.domain.ResponseEntity("Inventories not deleted", HttpStatus.OK.toString());
            } else{
                log.info("End DeleteAllInventories inventories deleted: " + inventories.toString());
                return new com.jyora.cs.domain.ResponseEntity("Inventories deleted", HttpStatus.OK.toString(), inventories);
            }

        }
        catch(Exception e){

            return new com.jyora.cs.domain.ResponseEntity( "Deleting Inventories Failed "+e, HttpStatus.EXPECTATION_FAILED.toString());

        }

    }

    @GetMapping("/get-inventories")
    public com.jyora.cs.domain.ResponseEntity GetAllInventories() {
        try {
            List<Document> inventoryList = mongoTemplate.findAll(Document.class, "Inventory");

            if (inventoryList == null) {
                log.info("Inside GetAllInventories inventories not found ");
                return new com.jyora.cs.domain.ResponseEntity("Inventories not found", HttpStatus.OK.toString());
            } else {
                log.info("End GetAllInventories inventories Found: " + inventoryList.toString());
                return new com.jyora.cs.domain.ResponseEntity("Inventories found", HttpStatus.OK.toString(), inventoryList);
            }
        }
        catch(Exception e){

            return new com.jyora.cs.domain.ResponseEntity( "Finding inventories Failed "+e, HttpStatus.EXPECTATION_FAILED.toString());

        }
    }

}


