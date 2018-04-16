//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.demo.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/***
 * @author tangtwenbo
 * 钱包实体类
 * */
@Data
@ToString
public class Wallet implements Serializable {
    private int id;
    private String coinCode;
    private String totalMoney;
    private String withdrawalsAddressMoney;
    private String withdrawalsAddress;
    private String coldwalletAddress;

    public Wallet() {
    }
}
