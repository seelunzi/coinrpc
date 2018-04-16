package com.example.demo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.example.demo.client.CdtRpcHttpClient;
import com.example.demo.model.Wallet;
import com.example.demo.service.CdtService;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @author tangwenbo
 * */
@Service("CdtService")
@Slf4j
public class CdtServiceImpl implements CdtService {

    private JsonRpcHttpClient client = CdtRpcHttpClient.getClient();

    @Override
    public BigDecimal getBalance(String accountName) {
        BigDecimal balance = BigDecimal.ZERO;
        String methodName = "list_account_balances";
        List<String> list = new ArrayList();
        List result = null;
        list.add(accountName);
        try {
            result = this.client.invoke(methodName, list, List.class);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if ((result != null) && (!result.isEmpty())) {
            Map<String, Object> map = (Map) result.get(0);
            String amount_ = map.get("amount").toString();
            balance = new BigDecimal(amount_).divide(BigDecimal.valueOf(100000L), 8, 1);
        } else {
            log.info("未查询到数据accountName=" + accountName);
        }
        return balance;
    }

    public String getPublicKey(String AccountNum) {
        String chargeAccount = CdtRpcHttpClient.chargeAccount;
        String memo = AccountNum;
        return "ACCOUNT:" + chargeAccount + ",MEMO:" + memo;
    }

    @Override
    public boolean unlock(String password) {
        boolean result = false;
        String unlockMethodName = "unlock";
        List<String> unlockparams = new ArrayList();
        unlockparams.add(password);
        try {
            Object unlockResult = this.client.invoke(unlockMethodName, unlockparams, Object.class);
            if (unlockResult == null) {
                result = true;
            }
        } catch (ConnectException e) {
            log.info("unCDT钱包拒绝连接");
        } catch (Throwable e) {
            log.info("unCDT钱包接口error");
        }
        return result;
    }

    @Override
    public boolean lock() {
        boolean result = false;
        String unlockMethodName = "lock";
        List<String> unlockparams = new ArrayList();
        try {
            Object unlockResult = this.client.invoke(unlockMethodName, unlockparams, Object.class);
            if (unlockResult == null) {
                result = true;
            }
        } catch (ConnectException e) {
            log.info("CDT钱包拒绝连接");
        } catch (Throwable e) {
            log.info("CDT钱包接口error");
        }
        return result;
    }

    @Override
    public String transfer(String fromAccount, String toAccount, String amount, String symbol, String memo) {
        String txId = null;
        String password = CdtRpcHttpClient.walletPassword;
        if (unlock(password)) {
            String methodname = "transfer2";
            List<Object> list = new ArrayList();
            list.add(fromAccount);
            list.add(toAccount);
            list.add(amount);
            list.add(symbol);
            list.add(memo);
            try {
                Object result = this.client.invoke(methodname, list, Object.class);
                List<String> data = (List) result;
                if ((data != null) && (!data.isEmpty())) {
                    txId = (String) data.get(0);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } else {
            log.info("解锁失失败");
        }
        lock();
        return txId;
    }

    @Override
    public ResponseEntity send2ColdAddress(String toAddress, String amount) {
        String memo = CdtRpcHttpClient.memo;
        String coldAccount = toAddress;
        String fromAccount = CdtRpcHttpClient.chargeAccount;
        String txHash = transfer(fromAccount, coldAccount, amount, "CDT", memo);
        if (StrUtil.isNotBlank(txHash)) {
            return ResponseEntity.ok(txHash);
        } else {
            return ResponseEntity.badRequest().body("CDT转币接口ERRPOR");
        }
    }


    @Override
    public HashMap<Boolean, String> sendFrom(String amount, String toAddress, String memo) {
        HashMap<Boolean, String> result = new HashMap<>();
        String fromAccount = CdtRpcHttpClient.chargeAccount;
        BigDecimal money = BigDecimal.valueOf(Double.valueOf(amount).doubleValue());
        BigDecimal fee = BigDecimal.valueOf(0.5D);
        BigDecimal chargeAccountMoney = getBalance(fromAccount).subtract(fee);
        if (chargeAccountMoney.compareTo(money) >= 0) {
            String txId = transfer(fromAccount, toAddress, amount, "BDS", memo);
            if (StrUtil.isNotBlank(txId)) {
                result.put(true, txId);
            } else {
                result.put(false, "错误，提笔账户余额不足");
            }
        } else {
            result.put(false, "错误，提币账户可用余额不足");
        }
        return result;
    }

    @Override
    public Wallet getWalletInfo() {
        Wallet wallet = new Wallet();
        String chargeAccount = CdtRpcHttpClient.chargeAccount;
        String total = getBalance(chargeAccount).toString();
        String toMoney = total;
        String coldAddress = "";
        wallet.setCoinCode("CDT");
        wallet.setColdwalletAddress(coldAddress == null ? "" : coldAddress);
        wallet.setWithdrawalsAddress(chargeAccount == null ? "" : chargeAccount);
        wallet.setWithdrawalsAddressMoney(toMoney);
        wallet.setTotalMoney(total);
        return wallet;
    }

}
