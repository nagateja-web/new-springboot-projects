package com.jyora.cs.service;

import com.jyora.cs.domain.Logistic;
import org.bson.Document;
import java.util.List;

public interface LogisticService {
    Document createLogistic(Document document);
    List<Document> getLogistic(String productId);
    Document deleteLogistic(String productId);
    Logistic modifyLogistic(String productId, Logistic logistic);

}
