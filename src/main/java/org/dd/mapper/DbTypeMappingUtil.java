package org.dd.mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @version $v: 1.0.0, $time:2015/9/30 16:18 Exp $
 */
public class DbTypeMappingUtil {
    static List<TypeMapping> values = new ArrayList<TypeMapping>();

    public static void add(TypeMapping typeMapping) {
       values.add(typeMapping);
    }

    public static TypeMapping getByColumnType(String columnType) {
        for (TypeMapping enumObj : DbTypeMappingUtil.values) {
            if (enumObj.getColumnType().equals(columnType)) {
                return enumObj;
            }
        }
        return null;
    }

    public static TypeMapping getByJavaType(String javaType) {
        for (TypeMapping enumObj : DbTypeMappingUtil.values) {
            if (enumObj.getJavaType().equals(javaType)) {
                return enumObj;
            }
        }
        return null;
    }
}
