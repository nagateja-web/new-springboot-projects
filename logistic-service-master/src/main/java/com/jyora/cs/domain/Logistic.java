package com.jyora.cs.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Logistic {

    private String productId;
    private Integer noOfProductsAvailable;
    private Integer noOfProductsSold;
    private String shipmentAddress;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getNoOfProductsAvailable() {
        return noOfProductsAvailable;
    }

    public void setNoOfProductsAvailable(Integer noOfProductsAvailable) {
        this.noOfProductsAvailable = noOfProductsAvailable;
    }

    public Integer getNoOfProductsSold() {
        return noOfProductsSold;
    }

    public void setNoOfProductsSold(Integer noOfProductsSold) {
        this.noOfProductsSold = noOfProductsSold;
    }

    public String getShipmentAddress() {
        return shipmentAddress;
    }

    public void setShipmentAddress(String shipmentAddress) {
        this.shipmentAddress = shipmentAddress;
    }

    @Override
    public String toString() {
        return "Logistic{" +
                "productId='" + productId + '\'' +
                ", noOfProductsAvailable=" + noOfProductsAvailable +
                ", noOfProductsSold=" + noOfProductsSold +
                ", shipmentAddress='" + shipmentAddress + '\'' +
                '}';
    }
}
