package com.example.demo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.azazar.bitcoin.jsonrpcclient.Bitcoin;
import com.azazar.bitcoin.jsonrpcclient.BitcoinException;
import com.azazar.bitcoin.jsonrpcclient.BitcoinJSONRPCClient;
import com.example.demo.model.Wallet;
import com.example.demo.service.BtcHotService;
import com.example.demo.utils.CommonUtil;
import com.example.demo.utils.JsonResult;
import com.example.demo.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @author tangwenbo
 * */
@Slf4j
@Service
public class BtcHotServiceImpl implements BtcHotService {

    private static volatile Map<String, BitcoinJSONRPCClient> mapClient = null;
    private static BitcoinJSONRPCClient client = BtcHotServiceImpl.getClient(null);
    public static String ourAddress = "";

    public BtcHotServiceImpl() {
    }

    public BtcHotServiceImpl(String type) {
        client = getClient(type);
    }

    public static BitcoinJSONRPCClient getClient(String type) {
        if (mapClient != null && mapClient.get(type) != null) {
            return (BitcoinJSONRPCClient) mapClient.get(type);
        } else {
            if (mapClient == null || mapClient.get(type) == null) {
                if (mapClient == null) {
                    mapClient = new HashMap(16);
                }
                log.info("获取" + type + "的rpc连接");
                String protocol = "http";
                String ip = "112.74.189.233";
                String port = "8080";
                String rpcuser = "bitcoinhotrpc";
                String rpcpassword = "FLmSASq9urqAxfxVmzUJ3cpvkcoeBeRrUfmtvmMuLBqG";
                String[] uri = new String[]{protocol, ip, port, rpcuser, rpcpassword};
                String rpcURI = protocol + "://" + rpcuser + ":" + rpcpassword + "@" + ip + ":" + port + "/";
                String[] uriArray = uri;
                int urlLength = uri.length;
                for (int i = 0; i < urlLength; ++i) {
                    String l = uriArray[i];
                    if (StrUtil.isBlank(l)) {
                        mapClient.put(type, (BitcoinJSONRPCClient) null);
                        log.info(type + "币接口配置参数无效请检查-----" + rpcURI);
                        return null;
                    }
                }
                try {
                    mapClient.put(type, new BitcoinJSONRPCClient(rpcURI));
                } catch (Exception var12) {
                    mapClient.put(type, (BitcoinJSONRPCClient) null);
                    log.info("建立连接异常-----" + rpcURI);
                }
            }

            return mapClient.get(type);
        }
    }

    @Override
    public String createNewAddress(String userName) {
        BitcoinJSONRPCClient client = BtcHotServiceImpl.getClient(null);
        try {
            if (StrUtil.isNotBlank(userName)) {
                return client.getNewAddress(userName);
            }
            return client.getNewAddress();
        } catch (BitcoinException var3) {
            log.info("钱包接口返回错误");
            var3.printStackTrace();
        } catch (NullPointerException var4) {
            log.info("client为空");
            var4.printStackTrace();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return null;
    }

    @Override
    public double getBalance(String account) {
        double balance = 0.0D;
        try {
            if (account != null) {
                balance = client.getBalance(account);
            } else {
                balance = client.getBalance();
            }
        } catch (BitcoinException var5) {
            log.info("钱包接口返回错误");
        } catch (NullPointerException var6) {
            log.info("client为空");
        }
        return balance;
    }

    @Override
    public List<Bitcoin.Transaction> listTransactions(String account, Integer count) {
        try {
            if (StrUtil.isNotBlank(account) && count != null) {
                return client.listTransactions(account, count);
            }

            if (StrUtil.isNotBlank(account) && count == null) {
                return client.listTransactions(account);
            }

            return client.listTransactions();
        } catch (BitcoinException var4) {
            log.info("钱包接口返回错误");
        } catch (NullPointerException var5) {
            log.info("client为空");
        }

        return null;
    }

    @Override
    public List<Bitcoin.Transaction> listTransactions(String account, int count, int from) {
        try {
            return client.listTransactions(account, count, from);
        } catch (BitcoinException var5) {
            log.info("钱包接口返回错误");
        } catch (NullPointerException var6) {
            log.info("client为空");
        }

        return null;
    }

    @Override
    public String sendFrom(String fromAddress, String toBitcoinAddress, double amount) {
        String message = "";
        String validate = null;
        String result = null;
        try {
            validate = this.validateAddress(fromAddress);
            validate = validate.replace(" ", "");
            Map<String, Object> map = StringUtils.str2map(validate);
            if ("true".equals(map.get("isvalid"))) {
                String r = client.sendFrom(fromAddress, toBitcoinAddress, amount);
                log.info("转币返回结果：===== : " + r);
                result = "{\"success\":\"true\",\"msg\":\"" + r + "\"}";
            } else {
                result = "{\"success\":\"false\",\"msg\":\"地址无效\"}";
            }
        } catch (BitcoinException var10) {
            message = "钱包接口返回错误";
            result = "{\"success\":\"false\",\"msg\":\"" + message + "\"}";
        } catch (NullPointerException var11) {
            message = "client为空";
            log.info("client为空");
            result = "{\"success\":\"false\",\"msg\":\"" + message + "\"}";
        }

        return result;
    }

    @Override
    public double getBalance() {
        try {
            return client.getBalance();
        } catch (BitcoinException var2) {
            log.info("钱包接口返回错误");
        } catch (NullPointerException var3) {
            log.info("client为空");
        }

        return Double.valueOf("0");
    }

    @Override
    public String getRawTransaction(String txId) {
        String ret = "";

        try {
            ret = client.getRawTransaction(txId).toString();
        } catch (BitcoinException var4) {
            log.info("钱包接口返回错误");
        } catch (NullPointerException var5) {
            log.info("client为空");
        }

        return ret;
    }

    @Override
    public String sendToAddress(String address, double amount) {
        String ret = "{code=0,msg=''}";
        String validate = "";
        try {
            validate = this.validateAddress(address);
            validate = validate.replace(" ", "");
            Map<String, Object> map = StringUtils.str2map(validate);
            System.out.println("验证结果0：" + map.get("isvalid"));
            System.out.println("验证结果1：" + map.toString());
            if ("true".equals(map.get("isvalid"))) {
                ret = client.sendToAddress(address, amount);
                ret = "{code=8,msg=" + ret + "}";
            } else {
                ret = "{code=1,msg='地址错误'}";
            }
        } catch (BitcoinException var7) {
            log.info("钱包接口返回错误");
            ret = "{code=1,msg='" + var7.getMessage() + "'}";
            var7.printStackTrace();
        } catch (NullPointerException var8) {
            log.info("client为空");
            ret = "{code=1,msg='" + var8.getMessage() + "'}";
            var8.printStackTrace();
        }

        return ret;
    }

    @Override
    public String validateAddress(String address) {
        String ret = "";

        try {
            ret = client.validateAddress(address).toString();
        } catch (BitcoinException var4) {
            log.info("钱包接口返回错误");
            var4.printStackTrace();
        } catch (NullPointerException var5) {
            log.info("client为空");
            var5.printStackTrace();
        }

        return ret;
    }

    @Override
    public Map<String, Number> listaccounts() {
        Map ret = null;

        try {
            ret = client.listAccounts();
        } catch (BitcoinException var3) {
            log.info("钱包接口返回错误");
        } catch (NullPointerException var4) {
            log.info("client为空");
        }

        return ret;
    }

    @Override
    public List<String> getAddressesByAccount(String account) {
        List ret = null;

        try {
            ret = client.getAddressesByAccount(account);
        } catch (BitcoinException var4) {
            log.info("钱包接口返回错误");
        } catch (NullPointerException var5) {
            log.info("client为空");
        }

        return ret;
    }

    @Override
    public double getReceivedByAddress(String address) {
        double ret = 0.0D;
        try {
            ret = client.getReceivedByAddress(address);
        } catch (BitcoinException var5) {
            log.info("钱包接口返回错误");
        } catch (NullPointerException var6) {
            log.info("client为空");
        }

        return ret;
    }

    @Override
    public String sendFromByAccount(String account, String toBitcoinAddress, double amount) {
        String ret = "";
        try {
            ret = client.sendFrom(account, toBitcoinAddress, amount);
        } catch (BitcoinException var7) {
            log.info("钱包接口返回错误");
        } catch (NullPointerException var8) {
            log.info("client为空");
        }

        System.out.println("转币操作：" + ret);
        return ret;
    }

    @Override
    public String getAccount(String address) {
        try {
            return client.getAccount(address);
        } catch (BitcoinException var3) {
            log.info("钱包接口返回错误");
        } catch (NullPointerException var4) {
            log.info("client为空");
        }

        return null;
    }

    @Override
    public JsonResult sendtoAddress(String toaddress, Double amount) {
        JsonResult result = new JsonResult();
        try {
            String hash = client.sendToAddress(toaddress, amount);
            if (StrUtil.isNotBlank(hash)) {
                result.setSuccess(true);
                result.setMsg(hash);
            }
        } catch (BitcoinException var5) {
            log.info("钱包接口返回错误");
            result = CommonUtil.analysisBitcoinException(var5.getMessage());
        } catch (NullPointerException var6) {
            log.info("client为空");
        }

        return result;
    }

    @Override
    public String sendfrom(String fromAddress, String toBitcoinAddress, double amount) {
        try {
            return client.sendFrom(fromAddress, toBitcoinAddress, amount);
        } catch (BitcoinException var6) {
            log.info("钱包接口返回错误");
        } catch (NullPointerException var7) {
            log.info("client为空");
        }

        return null;
    }

    public Map<String, Number> listAccounts() {
        try {
            return client.listAccounts();
        } catch (BitcoinException var2) {
            log.info("钱包接口返回错误");
        } catch (NullPointerException var3) {
            log.info("client为空");
        }

        return null;
    }

    @Override
    public JsonResult sendFrom(String amount, String toAddress) {
        JsonResult result = new JsonResult();
        Double fee = new Double("0.0005");
        Double money = Double.valueOf(amount);
        Double totalMoney = this.getBalance();
        Double needfunds = money + fee;
        if (totalMoney.compareTo(needfunds) >= 0) {
            result = this.sendtoAddress(toAddress, money);
        } else {
            result.setSuccess(false);
            result.setMsg("提币账户可用余额不足");
        }

        return result;
    }

    @Override
    public Wallet getWalletInfo(String coinCode) {
        Wallet wallet = new Wallet();
        Double balance = this.getBalance();
        if (balance != null) {
            BigDecimal money = BigDecimal.valueOf(balance);
            String total = money.toString();
            String withdrawalsAddress = "-";
            String coldwalletAddress = null;
            wallet.setCoinCode(coinCode);
            wallet.setColdwalletAddress(coldwalletAddress == null ? "" : coldwalletAddress);
            wallet.setWithdrawalsAddress(withdrawalsAddress);
            wallet.setWithdrawalsAddressMoney(total);
            wallet.setTotalMoney(total);
        }

        return wallet;
    }

}
