package com.pray.gitlab;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PlaceholderUtil
 *
 * @author Cotton Eye Joe
 * @since 2024/11/19 14:54
 */
public class PlaceholderUtil {
    /** 默认替换形如#{param}的占位符 */
    private static Pattern pattern = Pattern.compile("\\#\\{.*?\\}");

    /**
     * 替换字符串中形如#{}的占位符
     * @param src
     * @param parameters
     * @return
     */
    public static String replace(String src, Map<String, Object> parameters) {
        Matcher paraMatcher = pattern.matcher(src);

        // 存储参数名
        String paraName = "";
        String result = new String(src);

        while (paraMatcher.find()) {
            paraName = paraMatcher.group().replaceAll("\\#\\{", "").replaceAll("\\}", "");
            Object objParam = parameters.get(paraName);
            if(objParam!=null){
                result = result.replace(paraMatcher.group(), objParam.toString());
            }
        }
        return result;
    }

    /**
     * 替换字符串中形如#{}的占位符
     * @param str
     * @param params
     * @return
     */
    public static String anotherReplace(String str, Map<String, String> params) {
        Map<String, Object> newParams = new HashMap<>(params);
        return replace(str, newParams);
    }

}
