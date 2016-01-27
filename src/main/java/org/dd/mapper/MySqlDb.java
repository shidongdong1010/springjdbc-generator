package org.dd.mapper;

/**
 * mysql 字段类型映射
 *
 * @author Administrator
 * @version $v: 1.0.0, $time:2015/9/29 14:20 Exp $
 */
public class MySqlDb {

    public MySqlDb(){
        new TypeMapping("varchar", "String", null);

        new TypeMapping("text", "String", null);

        new TypeMapping("char", "String", null);

        new TypeMapping("date", "Date", "java.util.Date");

        new TypeMapping("timestamp", "Date", "java.util.Date");

        new TypeMapping("datetime", "Date", "java.util.Date");

        new TypeMapping("bigint", "Long", null);

        new TypeMapping("int", "Long", null);

        new TypeMapping("smallint", "Long", null);

        new TypeMapping("double", "Double", null);

        new TypeMapping("decimal", "Double", null);
    }
}
