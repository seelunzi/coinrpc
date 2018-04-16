
package com.example.demo.client;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import lombok.extern.slf4j.Slf4j;

import java.net.MalformedURLException;
import java.net.URL;

/****
 * @author tang
 * */
@Slf4j
public class CdtRpcHttpClient {
    private static String protocol = "";
    private static String ip = "";
    private static String port = "";
    public static final String chargeAccount = "";
    public static final String walletPassword = "";
    public static final String id = "";
    public static final String memo = "";
    private static volatile JsonRpcHttpClient jsonrpcClient;

    public static JsonRpcHttpClient getClient() {
        if (jsonrpcClient == null) {
            synchronized (CdtRpcHttpClient.class) {
                if (jsonrpcClient == null) {
                    try {
                        log.info("获取bds币的rpc连接成功");
                        String url = protocol + "://" + ip + ":" + port;
                        jsonrpcClient = new JsonRpcHttpClient(new URL(url));
                    } catch (MalformedURLException e) {
                        log.info("bds钱包接口连接失败");
                    }
                }
            }
        }
        return jsonrpcClient;
    }
}