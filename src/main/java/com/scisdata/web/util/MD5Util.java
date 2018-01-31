package com.scisdata.web.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by fangshilei on 18/1/24.
 */
public class MD5Util {
    public static String getEncryption(String originString) throws UnsupportedEncodingException{
        String result = "";
        if(originString != null){
            try{
                //指定加密的方式为MD5
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte bytes[] = md.digest(originString.getBytes("ISO8859-1"));
                for(int i =0;i<bytes.length;i++){
                    //将整数转换成十六进制形式的字符串 与0xFF进行运算保证结果为32位
                    String str = Integer.toHexString(bytes[i]&0xFF);
                    if(str.length() == 1){
                        str += "F";
                    }
                    result += str;
                }
            }catch(NoSuchAlgorithmException e){
                e.printStackTrace();
            }
        }
        return result;
    }
}
