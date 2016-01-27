package org.dd.model;

import org.dd.util.ConfigUtil;
import org.dd.util.PropertyUtil;

import java.util.List;

/**
 * 表
 * @author SDD
 * @version $v: 1.0.0, $time:2015/9/29 14:04 Exp $
 */
public class Table {

    /** 表名 **/
    private String tableName;

    /** 表说明 **/
    private String tableComment;

    /** 索引类型[PRI主键] **/
    private Column columnKey;

    /** 字段列表 **/
    private List<Column> columns;

    /** 得到类名称 **/
    public String getClassName(){
        // 首字母大写
        return PropertyUtil.firstUppercase(getPropertyName());
    }

    /** 得到类属性名称 **/
    public String getPropertyName(){
        String[] prefixs = ConfigUtil.get("table.prefix").split(",");
        // 去掉表前缀
        return PropertyUtil.fieldToProperty(PropertyUtil.replacePrefix(tableName, prefixs));
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Column getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(Column columnKey) {
        this.columnKey = columnKey;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    /**
     * 得到查询SQL的字段
     * @return
     */
    public String getSelectColumnSql(){
        StringBuilder sql = new StringBuilder();
        for(int i = 0; i < columns.size(); i++){
            Column column = columns.get(i);
            if(i != 0){
                sql.append(", ");
            }
            sql.append("_this.`").append(column.getColumnName()).append("`");
        }
        return sql.toString();
    }

    /**
     * 得到查询SQL
     * @return
     */
    public String getSelectSql(){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(getSelectColumnSql());
        sql.append(" FROM ");
        sql.append(getTableName()).append(" _this");
        return sql.toString();
    }

    /**
     * 得到插入SQL
     * @return
     */
    public String getInsertSql(){
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ");
        sql.append(getTableName());
        sql.append("(");
        for(int i = 0; i < columns.size(); i++){
            Column column = columns.get(i);
            if(i != 0){
                sql.append(", ");
            }
            sql.append("`").append(column.getColumnName()).append("`");
        }
        sql.append(") VALUES (");
        for(int i = 0; i < columns.size(); i++){
            Column column = columns.get(i);
            if(i != 0){
                sql.append(", ");
            }
            sql.append(":").append(column.getColumnName());
        }
        sql.append(")");
        return sql.toString();
    }

    /**
     * 得到更新SQL
     * @return
     */
    public String getUpdateSql(){
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        sql.append(getTableName());
        sql.append(" SET ");
        for(int i = 0; i < columns.size(); i++){
            Column column = columns.get(i);
            if(i != 0){
                sql.append(", ");
            }
            sql.append("`").append(column.getColumnName()).append("`");
            sql.append(" = :").append(column.getColumnName());
        }
        sql.append(" WHERE ");
        sql.append("`").append(columnKey.getColumnName()).append("`").append(" = :").append(columnKey.getColumnName());
        return sql.toString();
    }

    /**
     * 得到删除SQL
     * @return
     */
    public String getDeleteSql(){
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ");
        sql.append(getTableName());
        sql.append(" WHERE ");
        sql.append("`").append(columnKey.getColumnName()).append("`").append(" = ?");
        return sql.toString();
    }
}
