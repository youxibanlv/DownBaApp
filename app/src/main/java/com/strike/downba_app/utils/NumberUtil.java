package com.strike.downba_app.utils;

public class NumberUtil {

	public static Integer parseToInt(String str) {
		Integer i = null;
		if (str != null && !"".equals(str)) {
			try {
				i = Integer.parseInt(str);
			} catch (Exception e) {
				return i;
			}
		}
		return i;

	}

	public static String numToString(int num){
		String str;
        if (num/100000000>1){
            str = num/100000000 +"亿";
        }else if (num/10000000 >1){
            str = num/10000000 +"千万";
        }else if (num / 1000000 > 1){
			str = (num/1000000) +"百万";
		}else if (num/10000 > 1){
			str = (num/10000) + "万";
		}else if (num/1000 > 1){
			str = (num/1000) + "千";
		}else{
            str = num+"";
        }
		return str;
	}
}
