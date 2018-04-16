package com.example.demo.utils;

import com.alibaba.fastjson.JSON;
import org.nutz.lang.Strings;

import java.util.*;

public class StringUtils {

    public static String lowerFirst(String s) {
        return Strings.lowerFirst(s);
    }

    public static String upperFirst(String s) {
        return Strings.upperFirst(s);
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String toNULL(String str) {
        return str != null ? str : "";
    }

    public static boolean isNull(String str) {
        return str != null && !"".equals(str);
    }

    public static Map<String, Object> str2map(String param) {
        Map<String, Object> map = new HashMap();
        if (!"".equals(param) && null != param) {
            param = param.replace("{", "").replace("}", "");
            String[] params = param.split(",");

            for (int i = 0; i < params.length; ++i) {
                String[] p = params[i].split("=");
                if (p.length == 2) {
                    map.put(p[0], p[1]);
                }
            }

            return map;
        } else {
            return map;
        }
    }

    public static String regularString(String str) {
        String s2 = str.replaceAll("^(.{3})(.*)(.{4})$", "$1****$3");
        return s2;
    }

    public static String getSplitList(List<String> list, String sigin) {
        StringBuffer b = new StringBuffer();
        Iterator i$ = list.iterator();

        while (i$.hasNext()) {
            String l = (String) i$.next();
            b.append(l + sigin);
        }

        b.deleteCharAt(b.length() - 1);
        return b.toString();
    }

    public static List<Integer> getMergeIntegerList(String nums, String sigin) {
        List<Integer> list = new ArrayList();
        if (null != nums) {
            String[] arry = nums.split(sigin);

            for (int a = 0; a < arry.length; ++a) {
                list.add(Integer.valueOf(arry[a]));
            }
        }

        return list;
    }

    public static String stringSort(String[] s, String rule) {
        StringBuffer result = new StringBuffer();
        List<String> list = new ArrayList(s.length);

        for (int i = 0; i < s.length; ++i) {
            list.add(s[i]);
        }

        Collections.sort(list);
        String[] arr = (String[]) list.toArray(s);

        for (int i = 0; i < arr.length; ++i) {
            result.append(arr[i]).append(rule);
        }

        return result.deleteCharAt(result.length() - 1).toString();
    }

    public static String string2params(Map<String, String> map) {
        StringBuffer result = new StringBuffer();
        Iterator i$ = map.entrySet().iterator();

        while (i$.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry) i$.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            result.append(key).append("=").append(value == null ? "" : value.trim()).append("&");
        }

        return result.deleteCharAt(result.length() - 1).toString();
    }

    public static String string2json(String str) {
        String result = "";
        if (!"".equals(str) && str != null && str.contains("{") && str.contains("}")) {
            int start = str.indexOf("{");
            int end = str.lastIndexOf("}") + 1;
            result = str.substring(start, end);

            try {
                JSON.parseObject(result);
            } catch (Exception var5) {
                var5.printStackTrace();
                result = "";
            }
        }

        return result;
    }

    public static boolean containEmpty(String[] s) {
        if (s != null && s.length > 0) {
            String[] arr$ = s;
            int len$ = s.length;

            for (int i$ = 0; i$ < len$; ++i$) {
                String l = arr$[i$];
                if (l == null || "".equals(l)) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }
}
