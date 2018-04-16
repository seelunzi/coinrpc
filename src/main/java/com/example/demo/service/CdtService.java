package com.example.demo.service;

import com.example.demo.model.Wallet;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.HashMap;

/***
 * @author tangwenbo
 * */
public interface CdtService {
    /***
     * 得到账户余额
     * @param accountName 账户名称
     * */
    BigDecimal getBalance(String accountName);

    /***
     * 解锁账户
     * @param  password unlock account password
     * **/
    boolean unlock(String password);

    /***
     * 锁账户
     * @return lock true or false
     * */
    boolean lock();

    /***
     *
     * */
    String transfer(String fromAccount, String toAccount, String amount, String symbol, String memo);

    ResponseEntity send2ColdAddress(String toAddress, String amount);

    HashMap<Boolean, String> sendFrom(String amount, String toAddress, String memo);

    Wallet getWalletInfo();
}
