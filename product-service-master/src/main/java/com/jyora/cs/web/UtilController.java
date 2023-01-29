package com.jyora.cs.web;
import com.jyora.cs.domain.Product;
import com.jyora.cs.service.ProductService;
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
    private ProductService productService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostMapping("/create-bulk-products")
    public com.jyora.cs.domain.ResponseEntity createAllProducts(@RequestBody Product product, @RequestParam Integer noOfProductsTobeCreated) {

        try {
            List<Document> createdProducts = new ArrayList<>();
            for (int i = 0; i < noOfProductsTobeCreated; i++) {
                Document document = new Document();
                document.put("productId", product.getProductId());
                document.put("name", product.getName());
                document.put("category", product.getCategory());
                document.put("quantity", product.getQuantity());
                document.put("pricePerUnit", product.getPricePerUnit());
                Document createdProduct = productService.createProduct(document);
                createdProducts.add(createdProduct);
            }
            if(createdProducts == null){
                log.info("Inside createAllProducts Products not created ");
                return new com.jyora.cs.domain.ResponseEntity("Products not created", HttpStatus.OK.toString());
            }
            else{
                log.info("End createAllProducts Products created: " + createdProducts.toString());
                return new com.jyora.cs.domain.ResponseEntity("Totally " + createdProducts.size() + " Products created Sucessfully.", HttpStatus.OK.toString());
            }

        } catch (Exception e) {
            return new com.jyora.cs.domain.ResponseEntity("Creating Products Failed " + e, HttpStatus.EXPECTATION_FAILED.toString());
        }
    }

    @DeleteMapping("/delete-bulk-products")
    public com.jyora.cs.domain.ResponseEntity DeleteAllProducts() {
        try {
            List<Document> products = mongoTemplate.findAllAndRemove(new Query(), Document.class, "Products");

            if (products == null) {
                log.info("Inside deleteAllProducts Products not deleted ");
                return new com.jyora.cs.domain.ResponseEntity("Products not deleted", HttpStatus.OK.toString());
            } else{
                log.info("End deleteAllProducts Products deleted: " + products.toString());
                return new com.jyora.cs.domain.ResponseEntity("Products deleted", HttpStatus.OK.toString(), products);
            }

        }
        catch(Exception e){

            return new com.jyora.cs.domain.ResponseEntity( "Deleting Products Failed "+e, HttpStatus.EXPECTATION_FAILED.toString());

        }

    }

    @GetMapping("/get-products")
    public com.jyora.cs.domain.ResponseEntity GetAllProducts() {
        try {
            List<Document> productsList = mongoTemplate.findAll(Document.class, "Products");

            if (productsList == null) {
                log.info("Inside getAllProductsAccounts Products not found ");
                return new com.jyora.cs.domain.ResponseEntity("Products not found", HttpStatus.OK.toString());
            } else {
                log.info("End getAllProducts Products Found: " + productsList.toString());
                return new com.jyora.cs.domain.ResponseEntity("Products found", HttpStatus.OK.toString(), productsList);
            }
        }
        catch(Exception e){

            return new com.jyora.cs.domain.ResponseEntity( "Finding Products Failed "+e, HttpStatus.EXPECTATION_FAILED.toString());

        }
    }
}
