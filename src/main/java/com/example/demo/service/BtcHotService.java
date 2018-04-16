package com.example.demo.service;

import com.azazar.bitcoin.jsonrpcclient.Bitcoin;
import com.example.demo.model.Wallet;
import com.example.demo.utils.JsonResult;

import java.util.List;
import java.util.Map;

/***
 * @author tangwenbo
 * */
public interface BtcHotService {

    String createNewAddress(String var1);

    double getBalance();

    double getBalance(String var1);

    List<Bitcoin.Transaction> listTransactions(String var1, Integer var2);

    List<Bitcoin.Transaction> listTransactions(String var1, int var2, int var3);

    String getRawTransaction(String var1);

    String sendToAddress(String var1, double var2);

    String sendFrom(String var1, String var2, double var3);

    String sendFromByAccount(String var1, String var2, double var3);

    String validateAddress(String var1);

    Map<String, Number> listaccounts();

    List<String> getAddressesByAccount(String var1);

    double getReceivedByAddress(String var1);

    String getAccount(String var1);

    JsonResult sendtoAddress(String var1, Double var2);

    String sendfrom(String var1, String var2, double var3);

    Wallet getWalletInfo(String var1);

    JsonResult sendFrom(String var1, String var2);
}
