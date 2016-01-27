package org.dd.dao;

import org.dd.model.Column;
import org.dd.model.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * mysqlDao的实现
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015/9/29 14:35 Exp $
 */
@Repository
public class DaoMysqlImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * 查询所有表的信息
     * @return
     */
    public List<Table> findTableAll(String schema, List<String> tableNames) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append("    lower(table_name) table_name, ");
        sql.append("    table_comment ");
        sql.append(" FROM  INFORMATION_SCHEMA.TABLES ");
        sql.append("   WHERE ");
        sql.append(" TABLE_SCHEMA = :schema ");
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("schema", schema);
        if(tableNames != null) {
            sql.append(" and lower(table_name) in (:table_name)");
            param.addValue("table_name", tableNames);
        }
        sql.append(" ORDER BY table_name ");

        return new NamedParameterJdbcTemplate(jdbcTemplate).query(sql.toString(), param, BeanPropertyRowMapper.newInstance(Table.class));

        //return jdbcTemplate.query(sql.toString(), BeanPropertyRowMapper.newInstance(Table.class), param);
    }

    /**
     * 查询所有表的字段信息
     * @param schema
     * @return
     */
    public List<Column> findColumnAll(String schema, List<String> tableNames) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append("    lower(table_name) table_name, ");
        sql.append("    lower(column_name) column_name,");
        sql.append("    column_comment, ");
        sql.append("    lower(data_type) data_type, ");
        sql.append("    lower(column_type) column_type, ");
        sql.append("    column_key ");
        sql.append(" FROM INFORMATION_SCHEMA.COLUMNS ");
        sql.append(" WHERE ");
        sql.append(" TABLE_SCHEMA = :schema ");
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("schema", schema);
        if(tableNames != null) {
            sql.append(" and lower(table_name) in (:table_name)");
            param.addValue("table_name", tableNames);
        }
        sql.append(" ORDER BY table_name , ordinal_position asc");

        return new NamedParameterJdbcTemplate(jdbcTemplate).query(sql.toString(), param, BeanPropertyRowMapper.newInstance(Column.class));
        //return jdbcTemplate.query(sql.toString(), BeanPropertyRowMapper.newInstance(Column.class), schema);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
