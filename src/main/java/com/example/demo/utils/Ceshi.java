package com.example.demo.utils;

import cn.hutool.core.codec.Base64;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Ceshi {

    public static void main(String[] args) throws Throwable {
        String cred = Base64.encode(("bitcoinhotrpc" + ":" + "FLmSASq9urqAxfxVmzUJ3cpvkcoeBeRrUfmtvmMuLBqG").getBytes());
        ;
        Map<String, String> headers = new HashMap<String, String>(1);
        //身份认证
        headers.put("Authorization", "Basic " + cred);
        JsonRpcHttpClient client = new JsonRpcHttpClient(
                new URL("http://:8080"), headers);
        String result = (String) client.invoke("getblockhash", new Object[]{1}, Object.class);

        System.out.println(result);
    }
}
