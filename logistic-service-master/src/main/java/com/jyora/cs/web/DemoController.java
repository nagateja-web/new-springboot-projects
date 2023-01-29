package com.jyora.cs.web;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("demo")
public class DemoController {

    @Autowired
    MongoTemplate mongoTemplate;

    @GetMapping("/springboot")
    public String selectAll() {


        return "Hello SpringBoot Template";
    }

    @GetMapping("/springboot-template")
    public List<Document> getCustData() {
        System.out.println("DB Name:" + mongoTemplate.getDb().getName());
        System.out.println("Colls:" + mongoTemplate.getCollectionNames());
        return mongoTemplate.findAll(Document.class, "ClientData");
    }


}
