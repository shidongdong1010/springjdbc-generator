package org.dd.model;

import org.dd.util.PropertyUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 枚举
 * @author SDD
 * @version $v: 1.0.0, $time:2015/10/9 14:26 Exp $
 */
public class Enumeration {

    /** 名称 **/
    private String name;

    /** 说明 **/
    private String comment;

    /** 表 **/
    private Table table;

    /** 列 **/
    private Column column;

    /** 枚举属性 **/
    private List<EnumerationProperty> propertyList = new ArrayList<EnumerationProperty>();

    public Enumeration(Table table, Column column){
        this.table = table;
        this.column = column;

        int index1 = column.getColumnComment().indexOf("(");
        int index2 = column.getColumnComment().indexOf(")");

        if(index1 < 0 ){
            index1 = column.getColumnComment().indexOf("（");
        }
        if(index2 < 0){
            index2 = column.getColumnComment().indexOf("）");
        }

        if(index1 >= 0 && index2 >= 0 && index1 < index2){
            // 字段的首字母大写为枚举名称
            this.name = table.getClassName() + PropertyUtil.firstUppercase(column.getPropertyName());
            // 截取括号前的字符串为枚举描述
            this.comment = column.getColumnComment().substring(0, index1);

            // 截取括号内的字符串
            String propertyCommentStr = column.getColumnComment().substring(index1 + 1, index2);
            String[] propertyStrs = propertyCommentStr.split(",");
            if(propertyStrs == null || propertyStrs.length < 2){
                propertyStrs = propertyCommentStr.split("，");
            }
            for(String propertyStr : propertyStrs) {
                String[] propertys = propertyStr.split("-");
                if(propertys.length == 2) {
                    EnumerationProperty enumerationProperty = new EnumerationProperty();
                    enumerationProperty.setName(propertys[0].trim());
                    enumerationProperty.setComment(propertys[1].trim());
                    propertyList.add(enumerationProperty);
                }
            }
        }
    }

    /** 得到类名称 **/
    public String getClassName(){
        String property =  PropertyUtil.fieldToProperty(name);
        // 首字母大写
        return PropertyUtil.firstUppercase(property);
    }

    /** 得到类属性名称 **/
    public String getPropertyName(){
        return PropertyUtil.fieldToProperty(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public List<EnumerationProperty> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(List<EnumerationProperty> propertyList) {
        this.propertyList = propertyList;
    }
}
