package com.strike.downba_app.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by strike on 16/6/4.
 */
public class VerifyUtils {

    // 判断输入用户名是否正确
    public static boolean checkUserName(String userName) {
        if (userName == null || "".equals(userName)){
            return false;
        }
        return isEmail(userName) || isPhoneNum(userName);
    }

    // 判断输入用密码是否正确
    public static boolean checkPassword(String password) {
        String regexWifiName = "^[a-zA-Z0-9_]{3,32}";
        return Pattern.matches(regexWifiName, password);
    }
    //支付宝校验
    public static boolean isAlipay(String alipay){
        return isEmail(alipay) || isPhoneNum(alipay);
    }
    // 邮箱是否正确
    public static boolean isEmail(String email) {
        boolean tag = true;
        final String pattern1 = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        final Pattern pattern = Pattern.compile(pattern1);
        final Matcher mat = pattern.matcher(email);
        if (!mat.find()) {
            tag = false;
        }
        return tag;
    }
    //验证手机号
    public static boolean isPhoneNum(String phoneNum){
        final String pattern1 = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        final Pattern pattern = Pattern.compile(pattern1);
        final Matcher mat = pattern.matcher(phoneNum);
        return mat.find();
    }
    //验证验证URL
    public static boolean isUrl(String url){
//        final String UrlPatte = "^(http|https|ftp)\\://([a-zA-Z0-9\\.\\-]+(\\:[a-zA-"
//                + "Z0-9\\.&%\\$\\-]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{"
//                + "2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}"
//                + "[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|"
//                + "[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-"
//                + "4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0"
//                + "-9\\-]+\\.)*[a-zA-Z0-9\\-]+\\.[a-zA-Z]{2,4})(\\:[0-9]+)?(/"
//                + "[^/][a-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&%\\$\\=~_\\-@]*)*$";
//        return Pattern.matches(UrlPatte, url);
        if (TextUtils.isEmpty(url)) return false;
        return url.contains("http://");
    }


}
