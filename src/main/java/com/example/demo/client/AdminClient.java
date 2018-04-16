package com.example.demo.client;

import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.JsonRpc2_0Admin;
import org.web3j.protocol.http.HttpService;

public class AdminClient {
    private static String protocol = "http";
    private static String ip = "192.168.1.172";
    private static String port = "8545";
    private static volatile Admin admin;

    public static JsonRpc2_0Admin getClient() {
        if (admin == null) {
            synchronized (Admin.class) {
                if (admin == null) {
                    String url = protocol + ":" + ip + ":" + port;
                    /***
                     *连接以太坊的钱包节点,得到以太坊钱包管理器
                     * */
                    admin = Admin.build(new HttpService(url));
                }
            }
        }
        return (JsonRpc2_0Admin) admin;
    }
}

