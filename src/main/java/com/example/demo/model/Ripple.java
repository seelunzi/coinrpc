package com.example.demo.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Ripple {
    private int id;
    private String address;
    private String privateKey;
}
