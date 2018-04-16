package com.example.demo.utils;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;

import java.net.URL;

public class Ceshi2 {

    public static void main(String[] args) {
        // TODO 多个参数时使用例子
//      String[] temp = new String[]{"0x12341234"};
//      Object[] params = new Object[]{"0x1", "0x2", "0x8888f1f195afa192cfee860698584c030f4c9db1", temp};
////         密码为123456
        Object[] params = new Object[]{"123456"};
        String methodName = "personal_newAccount";
        try {
            JsonRpcHttpClient client = new JsonRpcHttpClient(new URL("http://192.168.1.172:8545"));
            Object address = client.invoke(methodName, params, Object.class);
            System.out.println(address);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}

