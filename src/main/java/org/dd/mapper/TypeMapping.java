package org.dd.mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @version $v: 1.0.0, $time:2015/9/30 15:43 Exp $
 */
public class TypeMapping {

    private String columnType;  // 字段类型
    private String javaType;    //  java类型
    private String javaPackage; // java 包类型

    public TypeMapping(String columnType, String javaType, String javaPackage){
        this.columnType = columnType;
        this.javaType = javaType;
        this.javaPackage = javaPackage;

        DbTypeMappingUtil.add(this);
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getJavaPackage() {
        return javaPackage;
    }

    public void setJavaPackage(String javaPackage) {
        this.javaPackage = javaPackage;
    }
}
