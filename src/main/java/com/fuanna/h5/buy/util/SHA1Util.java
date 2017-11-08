package com.fuanna.h5.buy.util;

import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SHA1Util {
	public final static String Sha1(String s) {
        char hexDigits[]={'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };       
        try {
            byte[] btInput = isContainChinese(s) ? s.getBytes("utf-8") : s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("sha-1");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
       }
	}
	
    public static boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
    
    public static void main(String[] args) {
		String string = "hehe.nimeide";
		String[] splitArr = string.split("\\.");
		System.out.println(splitArr[splitArr.length - 1].toUpperCase());
	}
}
