package com.jyora.cs.web;

import com.jyora.cs.domain.Product;
import com.jyora.cs.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("products")
@Log4j2
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostMapping("/create-account")
    public Document createAccount(String name, String age, String emailId, String mobileNumber) {



        Document account = new Document();
        account.put("name", name);
        account.put("age", age);
        account.put("email", emailId);
        account.put("mobileNumber", mobileNumber);

        Document createdAccount = mongoTemplate.save(account, "Accounts");

        return createdAccount;
    }

    @PostMapping("/create-product")
    public com.jyora.cs.domain.ResponseEntity createProduct(@RequestBody Product product)  {
        try {
            if(product.getProductId()==null){
                throw new Exception("ProductId not provided");
            }
            Document document = new Document();
            document.put("name", product.getName());
            document.put("productId", product.getProductId());
            document.put("category", product.getCategory());
            document.put("quantity", product.getQuantity());
            document.put("pricePerUnit", product.getPricePerUnit());
            Document createdProduct = productService.createProduct(document);
            if (createdProduct == null) {

                log.info("Inside createdProduct Product not created ");
                return new com.jyora.cs.domain.ResponseEntity("Product not created",HttpStatus.OK.toString());
            }
            else{
                log.info("End createProduct Found product: "+ createdProduct.toString());
                return new com.jyora.cs.domain.ResponseEntity("Product created",HttpStatus.OK.toString(),product);
            }

        } catch (Exception e) {
           return new com.jyora.cs.domain.ResponseEntity("Product Creation Failed", HttpStatus.EXPECTATION_FAILED.toString());
        }

    }
 @GetMapping ("/get-product")
 public com.jyora.cs.domain.ResponseEntity getProduct( @RequestParam String productId ) {
     log.info("Start getProduct productId: "+productId);
     try {

         List<Document> product = productService.getProduct(productId);

         if (product.size() == 0) {
             log.info("Inside getProduct method,  Product is not found.");
             return new com.jyora.cs.domain.ResponseEntity("Product not found",HttpStatus.OK.toString());

         }
         else{
             log.info("End getProduct Found product: "+ product.toString());
             return new com.jyora.cs.domain.ResponseEntity("Product found",HttpStatus.OK.toString(),product);
         }

     } catch (Exception e) {
         return new com.jyora.cs.domain.ResponseEntity("Product Fetching Failed", HttpStatus.EXPECTATION_FAILED.toString());
     }

 }

    @DeleteMapping ("/delete-product")
    public com.jyora.cs.domain.ResponseEntity deleteProduct(@RequestParam String productId) {
        log.info("Start deleteProduct mobileNumber: "+productId);
        try {

            Document product = productService.deleteProduct(productId);

            if (product != null) {
                log.info("End deleteProduct | product deleted Successfully ");
                return new com.jyora.cs.domain.ResponseEntity("Product Deleted", HttpStatus.OK.toString(), product);
            }
            else{
                return new com.jyora.cs.domain.ResponseEntity
                        ("Product not found", HttpStatus.OK.toString());
            }


        } catch (Exception e) {

            return new com.jyora.cs.domain.ResponseEntity("Product Deletion Failed", HttpStatus.EXPECTATION_FAILED.toString());
        }

    }

    @PutMapping  ("/modify-product")
    public com.jyora.cs.domain.ResponseEntity modifyProduct(@RequestParam String productId, @RequestBody Product pro ) {

        Product product;
        try {

            product = productService.modifyProduct(productId,pro);

            if (product == null) {
                log.info("End modifyProduct product not found ");
                return new com.jyora.cs.domain.ResponseEntity
                        ("Product not found", HttpStatus.OK.toString());
            } else {
                log.info("End modifyProduct product found and updated Successfully |product: " + product.toString());
                return new com.jyora.cs.domain.ResponseEntity("Product found and updated", HttpStatus.OK.toString(), product);
            }

        } catch (Exception e) {

            return new com.jyora.cs.domain.ResponseEntity("Product Updation Failed", HttpStatus.EXPECTATION_FAILED.toString());
        }

    }


}

