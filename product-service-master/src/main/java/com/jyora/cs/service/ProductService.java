package com.jyora.cs.service;

import com.jyora.cs.domain.Product;
import org.bson.Document;

import java.util.List;

public interface ProductService {

    Product modifyProduct(String productId, Product product);
    List<Document> getProduct(String productId);
    Document deleteProduct(String productId);
    Document createProduct(Document document);


}
