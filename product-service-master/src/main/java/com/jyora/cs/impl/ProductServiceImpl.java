package com.jyora.cs.impl;

import com.jyora.cs.domain.Product;
import com.jyora.cs.service.ProductService;
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
public class ProductServiceImpl implements ProductService {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public Document createProduct(Document document) {

        Document product = mongoTemplate.insert(document,"Products");

        return product;
    }

    @Override
    public List<Document> getProduct(String productId) {
        Criteria crit = Criteria.where("productId").is(productId);
        Query query = new Query(crit);
        query.fields().exclude("_id");
        List<Document> product = mongoTemplate.find(query, Document.class,"Products");
        return product;

    }

    @Override
    public Document deleteProduct(String productId) {
        Criteria crit = Criteria.where("productId").is(productId);
        Query query = new Query(crit);
        query.fields().exclude("_id");
        Document product = mongoTemplate.findAndRemove(query,Document.class,"Products");
        return product;
    }

    @Override
    public Product modifyProduct(String productId, Product product) {
        Criteria crit = Criteria.where("productId").is(productId);
        Query query = new Query(crit);
        query.fields().exclude("_id");
        Update update = new Update();
        update.set("productId", product.getProductId());
        update.set("name", product.getName());
        update.set("category", product.getCategory());
        update.set("quantity", product.getQuantity());
        update.set("pricePerUnit", product.getPricePerUnit());
        log.info("Inside modifyProduct product  before calling modify ");
        Product product1 = mongoTemplate.findAndModify(query, update,new FindAndModifyOptions().returnNew(true),Product.class,"Products");
        return product1;
    }


}
