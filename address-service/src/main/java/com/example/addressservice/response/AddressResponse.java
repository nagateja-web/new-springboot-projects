package com.example.addressservice.response;

import lombok.Data;

@Data
public class AddressResponse {

    private int id;
    private String lane1;
    private String state;
    private long zip;
}
