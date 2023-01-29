package com.jyora.cs.web;

import com.jyora.cs.domain.Logistic;
import com.jyora.cs.service.LogisticService;
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
    private LogisticService logisticService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostMapping("/create-bulk-logistics")
    public com.jyora.cs.domain.ResponseEntity createAllLogistics(@RequestBody Logistic logistic, @RequestParam Integer noOfLogisticsTobeCreated) {

        try {
            List<Document> createdLogistics = new ArrayList<>();
            for (int i = 0; i < noOfLogisticsTobeCreated; i++) {
                Document document = new Document();
                document.put("productId", logistic.getProductId());
                document.put("noOfProductsAvailable", logistic.getNoOfProductsAvailable());
                document.put("noOfProductsSold", logistic.getNoOfProductsSold());
                document.put("shipmentAddress", logistic.getShipmentAddress());
                Document createdLogistic = logisticService.createLogistic(document);
                createdLogistics.add(createdLogistic);
            }
            if(createdLogistics == null){
                log.info("Inside createAllLogistics Logistics not created ");
                return new com.jyora.cs.domain.ResponseEntity("Logistics not created", HttpStatus.OK.toString());
            }
            else{
                log.info("End createAllLogistics Logistics created: " + createdLogistics.toString());
                return new com.jyora.cs.domain.ResponseEntity("Totally " + createdLogistics.size() + " Logistics created Sucessfully.", HttpStatus.OK.toString());
            }

        } catch (Exception e) {
            return new com.jyora.cs.domain.ResponseEntity("Creating Logistics Failed " + e, HttpStatus.EXPECTATION_FAILED.toString());
        }
    }

    @DeleteMapping("/delete-bulk-logistics")
    public com.jyora.cs.domain.ResponseEntity DeleteAllLogistics() {
        try {
            List<Document> logistics = mongoTemplate.findAllAndRemove(new Query(), Document.class, "Logistics");

            if (logistics == null) {
                log.info("Inside DeleteAllLogistics Logistics not deleted ");
                return new com.jyora.cs.domain.ResponseEntity("Logistics not deleted", HttpStatus.OK.toString());
            } else{
                log.info("End DeleteAllLogistics Logistics deleted: " + logistics.toString());
                return new com.jyora.cs.domain.ResponseEntity("Logistics deleted", HttpStatus.OK.toString(), logistics);
            }

        }
        catch(Exception e){

            return new com.jyora.cs.domain.ResponseEntity( "Deleting Logistics Failed "+e, HttpStatus.EXPECTATION_FAILED.toString());

        }

    }

    @GetMapping("/get-logistics")
    public com.jyora.cs.domain.ResponseEntity GetAllLogistics() {
        try {
            List<Document> logisticsList = mongoTemplate.findAll(Document.class, "Logistics");

            if (logisticsList == null) {
                log.info("Inside getAllLogistics Logistics not found ");
                return new com.jyora.cs.domain.ResponseEntity("Logistics not found", HttpStatus.OK.toString());
            } else {
                log.info("End getAllLogistics Logistics Found: " + logisticsList.toString());
                return new com.jyora.cs.domain.ResponseEntity("Logistics found", HttpStatus.OK.toString(), logisticsList);
            }
        }
        catch(Exception e){

            return new com.jyora.cs.domain.ResponseEntity( "Finding Logistics Failed "+e, HttpStatus.EXPECTATION_FAILED.toString());

        }
    }
}
