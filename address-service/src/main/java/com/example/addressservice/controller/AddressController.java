package com.example.addressservice.controller;

import com.example.addressservice.response.AddressResponse;
import com.example.addressservice.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressController {

    @Autowired
    private AddressService as;


    @GetMapping("/address/{employeeId}")
    public ResponseEntity<AddressResponse> getAddressByEmployeeId(@PathVariable("employeeId") int id){
        AddressResponse addressResponse = null;
        addressResponse = as.findAddressByEmployeeId(id);
        return ResponseEntity.status(HttpStatus.OK).body(addressResponse);
    }
}
