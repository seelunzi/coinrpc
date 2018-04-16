package com.example.demo.utils;

import com.alibaba.fastjson.JSON;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/***
 * @author tangwenbo
 * */
public class CommonUtil {

    public static final String HexPrefix = "0x";

    public CommonUtil() {
    }

    public static boolean isNoHasEmptyInListstr(List<String> list) {
        Iterator var1 = list.iterator();

        String l;
        do {
            if (!var1.hasNext()) {
                return true;
            }

            l = (String) var1.next();
        } while (l != null && !"".equals(l.trim()));

        return false;
    }

    public static boolean isNohashEmptyInArr(String[] arr) {
        String[] var1 = arr;
        int var2 = arr.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            String l = var1[var3];
            if (l == null || "".equals(l.trim())) {
                return false;
            }
        }

        return true;
    }

    public static String strRoundDown(String str, int digit) {
        BigDecimal a = new BigDecimal(str);
        a = a.setScale(digit, 1);
        return a.toString();
    }

    public static JsonResult analysisBitcoinException(String msg) {
        JsonResult result = new JsonResult();
        int start = msg.indexOf("{");
        int end = msg.lastIndexOf("}") + 1;
        String str = msg.substring(start, end);
        Map<String, Object> map = (Map) JSON.parseObject(str, Map.class);
        String error = map.get("error").toString();
        Map<String, Object> data = (Map) JSON.parseObject(error, Map.class);
        result.setSuccess(false);
        result.setCode(data.get("code").toString());
        result.setMsg(data.get("message").toString());
        return result;
    }
}
