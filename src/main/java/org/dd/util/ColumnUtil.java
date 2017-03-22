package org.dd.util;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author SDD
 * @version $v: 1.0.0, $time:2015/9/29 15:04 Exp $
 */
public class ColumnUtil {

    /**
     * 对象属性转换为字段  例如：userName to user_name
     * @param property 字段名
     * @return
     */
    public static String propertyToField(String property) {
        if (null == property) {
            return "";
        }
        char[] chars = property.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (char c : chars) {
            if (CharUtils.isAsciiAlphaUpper(c)) {
                sb.append("_" + StringUtils.lowerCase(CharUtils.toString(c)));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 字段转换成对象属性 例如：user_name to userName
     *
     * @param field
     * @return
     */
    public static String fieldToProperty(String field) {
        if (null == field) {
            return "";
        }
        char[] chars = field.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '_') {
                int j = i + 1;
                if (j < chars.length) {
                    sb.append(StringUtils.upperCase(CharUtils.toString(chars[j])));
                    i++;
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 得到对象属性的Gtter
     * @param property
     * @return
     */
    public static String propertyGtter(String property){
        if (null == property) {
            return "";
        }
        // 首字母大写
        return "get" + firstUppercase(property);
    }

    /**
     * 得到对象属性的Setter
     * @param property
     * @return
     */
    public static String propertySetter(String property){
        if (null == property) {
            return "";
        }
        // 首字母大写
        return "set" + firstUppercase(property);
    }

    /**
     * 首字母大写
     * @param property
     * @return
     */
    public static String firstUppercase(String property){
        char[] chars = property.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (i == 0) {
                sb.append(StringUtils.upperCase(CharUtils.toString(chars[0])));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
