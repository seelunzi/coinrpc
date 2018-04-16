package com.example.demo.utils;

import com.example.demo.client.AdminClient;
import org.web3j.protocol.admin.JsonRpc2_0Admin;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;

import java.io.IOException;

public class Ceshi3 {

    public static void main(String[] args) {
        JsonRpc2_0Admin client = AdminClient.getClient();
        NewAccountIdentifier account = null;
        try {
            account = (NewAccountIdentifier) client.personalNewAccount("asddsa").send();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print(account.getAccountId());
    }
}
