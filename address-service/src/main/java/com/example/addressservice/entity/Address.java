package com.example.addressservice.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "address")
@Data
public class Address {
    //id, lane 1, state, zip, employee_id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "lane1")
    private String lane1;
    private String state;
    private long zip;
}
