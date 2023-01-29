package com.jyora.cs.web;

import com.jyora.cs.domain.Logistic;
import com.jyora.cs.service.LogisticService;
import lombok.extern.log4j.Log4j2;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Log4j2
@RequestMapping("logistics")
public class LogisticController {

    @Autowired
    LogisticService logisticService;

    @PostMapping("/create-logistic")
    public com.jyora.cs.domain.ResponseEntity createLogistic(@RequestBody Logistic logistic) {


        try {
            Document document = new Document();
            document.put("productId", logistic.getProductId());
            document.put("noOfProductsAvailable", logistic.getNoOfProductsAvailable());
            document.put("noOfProductsSold", logistic.getNoOfProductsSold());
            document.put("shipmentAddress",logistic.getShipmentAddress());

            Document createdLogistic = logisticService.createLogistic(document);

            if (createdLogistic == null) {

                log.info("Inside createLogistic Logistic not created ");
                return new com.jyora.cs.domain.ResponseEntity("Logistic not created", HttpStatus.OK.toString());

            }
            else{
                log.info("End createLogistic Found Logistic: "+ createdLogistic.toString());
                return new com.jyora.cs.domain.ResponseEntity("Logistic created",HttpStatus.OK.toString(),createdLogistic);
            }

        } catch (Exception e) {

            return new com.jyora.cs.domain.ResponseEntity("Logistic Creation Failed", HttpStatus.EXPECTATION_FAILED.toString());
        }

    }

    @GetMapping("/get-logistic")
    public com.jyora.cs.domain.ResponseEntity getLogistic( @RequestParam String productId ) {
        log.info("Start getLogistic productId: "+productId);
        try {

            List<Document> logistic = logisticService.getLogistic(productId);

            if (logistic.size() == 0) {
                log.info("Inside getLogistic Logistic not found ");
                return new com.jyora.cs.domain.ResponseEntity("Logistic not found",HttpStatus.OK.toString());

            }
            else{
                log.info("End getLogistic Found Logistic: "+ logistic.toString());
                return new com.jyora.cs.domain.ResponseEntity("Logistic found",HttpStatus.OK.toString(),logistic);
            }

        } catch (Exception e) {

            return new com.jyora.cs.domain.ResponseEntity("Logistic Fetching Failed", HttpStatus.EXPECTATION_FAILED.toString());
        }

    }

    @DeleteMapping ("/delete-logistic")
    public com.jyora.cs.domain.ResponseEntity deleteLogistic(@RequestParam String productId) {
        log.info("Start deleteLogistic productId: "+productId);
        try {

            Document logistic = logisticService.deleteLogistic(productId);

            if (logistic != null) {
                log.info("End deleteLogistic | logistic deleted Successfully ");
                return new com.jyora.cs.domain.ResponseEntity("logistic Deleted", HttpStatus.OK.toString(), logistic);
            }
            else{
                return new com.jyora.cs.domain.ResponseEntity
                        ("logistic not found", HttpStatus.OK.toString());
            }


        } catch (Exception e) {

            return new com.jyora.cs.domain.ResponseEntity("logistic Deletion Failed", HttpStatus.EXPECTATION_FAILED.toString());
        }

    }

    @PutMapping  ("/modify-logistic")
    public com.jyora.cs.domain.ResponseEntity modifyLogistic(@RequestParam String productId, @RequestBody Logistic logi ) {

        Logistic logistic;
        try {

            logistic = logisticService.modifyLogistic(productId,logi);

            if (logistic == null) {
                log.info("End modifyLogistic logistic not found ");
                return new com.jyora.cs.domain.ResponseEntity("logistic not found", HttpStatus.OK.toString());
            } else {
                log.info("End modifyLogistic logistic found and updated Successfully |logistic: " + logistic.toString());
                return new com.jyora.cs.domain.ResponseEntity("logistic found and updated", HttpStatus.OK.toString(), logistic);
            }

        } catch (Exception e) {

            return new com.jyora.cs.domain.ResponseEntity("logistic Updation Failed", HttpStatus.EXPECTATION_FAILED.toString());
        }

    }

}
