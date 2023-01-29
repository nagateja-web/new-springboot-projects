package com.jyora.cs.web;

import com.jyora.cs.domain.Inventory;
import com.jyora.cs.service.InventoryService;
import lombok.extern.log4j.Log4j2;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Log4j2
@RequestMapping("inventory")
public class InventoryController {
    @Autowired
    InventoryService inventoryService;

    @PostMapping("/create-inventory")
    public com.jyora.cs.domain.ResponseEntity createInventory(@RequestBody Inventory inventory) {


        try {
            Document document = new Document();
            document.put("products", inventory.getProducts());
            document.put("inventoryId", inventory.getInventoryId());

            Document createdInventory = inventoryService.createInventory(document);

            if (createdInventory == null) {

                log.info("Inside createInventory Inventory not created ");
                return new com.jyora.cs.domain.ResponseEntity("Inventory not created", HttpStatus.OK.toString());

            }
            else{
                log.info("End createInventory Found Inventory: "+ createdInventory.toString());
                return new com.jyora.cs.domain.ResponseEntity("Inventory created",HttpStatus.OK.toString(),createdInventory);
            }

        } catch (Exception e) {

            return new com.jyora.cs.domain.ResponseEntity("Inventory Creation Failed", HttpStatus.EXPECTATION_FAILED.toString());
        }

    }

    @GetMapping("/get-inventory")
    public com.jyora.cs.domain.ResponseEntity getInventory( @RequestParam Integer inventoryId ) {
        log.info("Start getRecommendation inventoryId: "+inventoryId);
        try {

            List<Document> inventory = inventoryService.getInventory(inventoryId);

            if (inventory.size() == 0) {
                log.info("Inside getInventory inventory not found ");
                return new com.jyora.cs.domain.ResponseEntity("inventory not found",HttpStatus.OK.toString());

            }
            else{
                log.info("End getInventory Found inventory: "+ inventory.toString());
                return new com.jyora.cs.domain.ResponseEntity("inventory found",HttpStatus.OK.toString(),inventory);
            }

        } catch (Exception e) {

            return new com.jyora.cs.domain.ResponseEntity("inventory Fetching Failed", HttpStatus.EXPECTATION_FAILED.toString());
        }


    }

    @DeleteMapping("/delete-inventory")
    public com.jyora.cs.domain.ResponseEntity deleteInventory(@RequestParam Integer inventoryId) {
        log.info("Start deleteInventory inventoryId: "+inventoryId);
        try {

            Document inventory = inventoryService.deleteInventory(inventoryId);

            if (inventory != null) {
                log.info("End deleteInventory | inventory deleted Successfully ");
                return new com.jyora.cs.domain.ResponseEntity("inventory Deleted", HttpStatus.OK.toString(), inventory);
            }
            else{
                return new com.jyora.cs.domain.ResponseEntity
                        ("inventory not found", HttpStatus.OK.toString());
            }


        } catch (Exception e) {

            return new com.jyora.cs.domain.ResponseEntity("inventory Deletion Failed", HttpStatus.EXPECTATION_FAILED.toString());
        }

    }

    @PutMapping  ("/modify-inventory")
    public com.jyora.cs.domain.ResponseEntity modifyInventory(@RequestParam Integer inventoryId, @RequestBody Inventory inv ) {

        Inventory inventory;
        try {

            inventory = inventoryService.modifyInventory(inventoryId,inv);

            if (inventory == null) {
                log.info("End modifyInventory inventory not found ");
                return new com.jyora.cs.domain.ResponseEntity("Inventory not found", HttpStatus.OK.toString());
            } else {
                log.info("End modifyInventory inventory found and updated Successfully |inventory: " + inventory.toString());
                return new com.jyora.cs.domain.ResponseEntity("Inventory found and updated", HttpStatus.OK.toString(), inventory);
            }

        } catch (Exception e) {

            return new com.jyora.cs.domain.ResponseEntity("Inventory Updation Failed", HttpStatus.EXPECTATION_FAILED.toString());
        }

    }

}
