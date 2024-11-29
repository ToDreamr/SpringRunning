package com.pray.stream;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

/**
 * CharSetCheck
 *
 * @author 九歌天上有
 * @since 2024/11/29 下午7:26
 */
public class CharSetCheck {
    //查看byte数组是什么编码格式
    public static void byteToCheck() throws UnsupportedEncodingException {

//        Map<String , Charset> map = Charset.availableCharsets();
//        Set<Map.Entry<String , Charset>> set = map.entrySet();
//        for(Map.Entry<String , Charset> entry : set){
//            checkEncoding(String.valueOf(entry.getValue()));
//            String s = String.valueOf(entry.getValue());
//            System.out.println(s);
//        }
        checkEncoding("CESU-8");
    }

    //检查编码格式
    private static void checkEncoding( String charset ) throws UnsupportedEncodingException {
        //从字节流中来的
        byte[] byteArray =  {123, 34, 99, 111, 100, 101, 34, 58, 32, 48, 44, 32, 34, 109, 101, 115, 115, 34, 58, 32, 34, -27, -68, -89, -27, -98, -126, -24,-82, -95, -25, -82, -105, -25, -88, -117, -27, -70, -113, -24, -65, -112, -24, -95, -116, -27, -121, -70, -23, -108, -103, 34, 44, 32, 34, 100, 97, 116, 97, 34, 58, 32, 123, 34, 103, 97, 109, 97, 54, 34, 58, 32, 48, 44, 32, 34, 104, 111, 114, 105, 122, 111, 110, 116, 97, 108, 115, 116, 114, 101, 115, 115, 34, 58, 32, 48, 44, 32, 34, 112, 105, 99, 116, 117, 114, 101, 95, 97, 100, 100, 114, 101, 115, 115, 34, 58, 32, 34, 104, 116, 116, 112, 58, 47, 47, 52, 55, 46, 49, 49, 51, 46, 49, 48, 52, 46, 50, 50, 57, 47, 112, 121, 116, 104, 111, 110, 47, -24, -82, -95, -25, -82, -105, -27, -121, -70, -23, -108, -103, -24, -65, -108, -27, -101, -98, -27, -101, -66, 46, 112, 110, 103, 34, 44, 32, 34, 115, 97, 103, 95, 109, 97, 120, 34, 58, 32, 48, 44, 32, 34, 97, 34, 58, 32, 48, 125, 125};
        //打印编码
        System.out.println( charset+"编码格式");
        System.out.println(new String(byteArray,charset));
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
       byteToCheck();
    }

}
