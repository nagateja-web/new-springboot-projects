package com.example.addressservice.service;

import com.example.addressservice.entity.Address;
import com.example.addressservice.repository.AddressRepository;
import com.example.addressservice.response.AddressResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepository ar;

    @Autowired
    private ModelMapper mm;

    public AddressResponse findAddressByEmployeeId(int employeeId){
        Address address = ar.findAddressByEmployeeId(employeeId);
        AddressResponse addressRepo = mm.map(address, AddressResponse.class);
        return addressRepo;
    }
}
