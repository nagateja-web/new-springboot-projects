package com.jyora.cs.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoDBConfig {
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb+srv://jyora-mongodb:jyora123@yellowkart.45hs9.mongodb.net/jyoradb?retryWrites=true&w=majority");
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "jyoradb");
    }

}

