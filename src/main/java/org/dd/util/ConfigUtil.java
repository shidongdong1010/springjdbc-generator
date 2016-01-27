package org.dd.util;

import java.util.Map;

/**
 * 配置文件
 * @author SDD
 * @version $v: 1.0.0, $time:2015/10/9 15:59 Exp $
 */
public class ConfigUtil {

    public static Map<String, String> configMap;

    public static void setConfig(Map<String, String> configMap) {
        ConfigUtil.configMap = configMap;
    }

    public static String get(String key){
        return configMap.get(key);
    }
}
