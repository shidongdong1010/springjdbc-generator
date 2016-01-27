package org.dd.model;

import org.dd.mapper.DbTypeMappingUtil;
import org.dd.mapper.TypeMapping;
import org.dd.util.PropertyUtil;

/**
 * 字段
 * @author SDD
 * @version $v: 1.0.0, $time:2015/9/29 14:06 Exp $
 */
public class Column {

    /** 表名 **/
    private String tableName;

    /** 字段名 **/
    private String columnName;

    /** 字段说明 **/
    private String columnComment;

    /** 数据类型 **/
    private String dataType;

    /** 字段类型 **/
    private String columnType;

    /** 索引类型[PRI主键] **/
    private String columnKey;

    /** 得到属性名称 **/
    public String getPropertyName(){
        return PropertyUtil.fieldToProperty(columnName);
    }

    /** 得到属性类型 **/
    public TypeMapping getPropertyType(){
        return DbTypeMappingUtil.getByColumnType(dataType);
    }

    /** 得到属性名称的Getter方法 **/
    public String getPropertyNameGetter(){
        return PropertyUtil.propertyGtter(getPropertyName());
    }

    /** 得到属性名称的Setter方法 **/
    public String getPropertyNameSetter(){
        return PropertyUtil.propertySetter(getPropertyName());
    }

    public boolean isPK(){
        if("PRI".equals(columnKey)){
            return true;
        }
        return false;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }
}
