package com.jyora.cs.impl;

import com.jyora.cs.domain.Logistic;
import com.jyora.cs.domain.Product;
import com.jyora.cs.service.LogisticService;
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
public class LogisticServiceImpl implements LogisticService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Document createLogistic(Document document) {

        Document logistic = mongoTemplate.insert(document,"Logistics");

        return logistic;
    }

    @Override
    public List<Document> getLogistic(String productId) {

        Criteria crit = Criteria.where("productId").is(productId);
        Query query = new Query(crit);
        query.fields().exclude("_id");
        List<Document> logistics = mongoTemplate.find(query, Document.class,"Logistics");
        return logistics;

    }

    @Override
    public Document deleteLogistic(String productId) {

        Criteria crit = Criteria.where("productId").is(productId);
        Query query = new Query(crit);
        query.fields().exclude("_id");
        Document logistic = mongoTemplate.findAndRemove(query,Document.class,"Logistics");
        return logistic;

    }

    @Override
    public Logistic modifyLogistic(String productId, Logistic logistic) {
        Criteria crit = Criteria.where("productId").is(productId);
        Query query = new Query(crit);
        query.fields().exclude("_id");
        Update update = new Update();
        update.set("productId", logistic.getProductId());
        update.set("noOfProductsAvailable", logistic.getNoOfProductsAvailable());
        update.set("noOfProductsSold", logistic.getNoOfProductsSold());
        update.set("shipmentAddress", logistic.getShipmentAddress());
        log.info("Inside modifyLogistic logistic  before calling modify ");
        Logistic logistic1 = mongoTemplate.findAndModify(query, update,new FindAndModifyOptions().returnNew(true),Logistic.class,"Logistics");
        return logistic1;
    }
}
