package com.strike.downba_app.http;

import com.google.gson.Gson;

import org.xutils.common.util.MD5;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by strike on 2017/5/4.
 */

public class HttpUtil {

    public static String getSign(BaseReq req) {
        Gson gson = new Gson();
        Class<? extends BaseReq> reqClass = req.getClass();
        Field [] fs = reqClass.getFields();
        Map<String, String> map = new HashMap<>();
        for (Field f:fs){
            f.setAccessible(true);
            try {
                Object value = f.get(req)==null?"":f.get(req);
                map.put(f.getName(), gson.toJson(value));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        StringBuilder contentBuffer = new StringBuilder();
        Object[] signParamArray = map.keySet().toArray();
        Arrays.sort(signParamArray);
        for (Object key : signParamArray) {
            String value = map.get(key);
            if (!"sign".equals(key) && !"key".equals(key) && !"rp".equals(key)) {// sign key 不参与签名
                contentBuffer.append(key + "=" + value + "&");
            }
        }
        contentBuffer.append("key="+HttpConstance.KEY);
        String content = contentBuffer.toString();
        String sign = MD5.md5(content);
        return sign;
    }

    public static String getSign(Map<String,String> map){
        StringBuilder contentBuffer = new StringBuilder();
        Gson gson = new Gson();
        Object[] signParamArray = map.keySet().toArray();
        Arrays.sort(signParamArray);
        for (Object key : signParamArray) {
            String value = map.get(key);
            if (!"sign".equals(key) && !"key".equals(key) && !"rp".equals(key)) {// sign key 不参与签名
                contentBuffer.append(key + "=" + gson.toJson(value) + "&");
            }
        }
        contentBuffer.append("key="+HttpConstance.KEY);
        String content = contentBuffer.toString();
        String sign = MD5.md5(content);
        return sign;
    }
}
