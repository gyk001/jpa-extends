package cn.guoyukun.spring.utils;

/**
 * @author yukun.gyk
 * @date 2013/1/12
 */
public class StringUtils {
    private StringUtils(){}

    public static boolean isEmpty(String str){
        return str==null || str.isEmpty();
    }

    public static boolean isBlank(String str){
        return str==null || str.trim().isEmpty();
    }
}
