package org.dd;

import org.apache.commons.lang3.StringUtils;
import org.dd.dao.DaoMysqlImpl;
import org.dd.mapper.DbTypeMappingUtil;
import org.dd.mapper.TypeMapping;
import org.dd.model.Column;
import org.dd.model.Table;
import org.dd.util.ConfigUtil;
import org.dd.util.FileUtil;
import org.dd.util.PropertyUtil;
import org.dd.util.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version $v: 1.0.0, $time:2015/9/29 15:41 Exp $
 */
@Service
public class GeneratorModel {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private DaoMysqlImpl dao;

    public void generator(List<Table> tableList,  List<Column> columnList){
        for(Table table : tableList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.putAll(ConfigUtil.configMap);
            List<Column> columns = this.getColumn(columnList, table.getTableName());
            Column columnKey = this.getColumnKey(columns);
            table.setColumns(columns);
            table.setColumnKey(columnKey);

            map.put("table", table);
            map.put("list", columns);
            map.put("packages", this.getPackageList(columns));

            // 转换模板文件内容
            String content = templateService.mergeTemplateIntoString("model.vm", map);
            // 保存文件
            FileUtil.saveJavaFile(content, ConfigUtil.get("out.path"), ConfigUtil.get("model_package"), table.getClassName());
        }
    }

    /** 得到表的所有字段 **/
    private List<Column> getColumn(List<Column> columnAll, String tableName){
        List<Column> columns = new ArrayList<Column>();
        for(Column column : columnAll) {
            if(column.getTableName().equals(tableName)) {
                columns.add(column);
            }
        }
        return columns;
    }

    /** 得到去重后的Package列表 **/
    private List<String> getPackageList(List<Column> columns) {
        List<String> list = new ArrayList<String>();
        for(Column column : columns) {
            TypeMapping typeMapping = DbTypeMappingUtil.getByColumnType(column.getColumnType());
            if (typeMapping == null ) continue;
            String javaPackage = typeMapping.getJavaPackage();
            if(StringUtils.isNotBlank(javaPackage)){
                if(!list.contains(javaPackage)){
                    list.add(javaPackage);
                }
            }
        }
        return list;
    }

    /** 得到主键 **/
    private Column getColumnKey(List<Column> columns){
        for(Column column : columns) {
            if(column.getColumnKey().equals("PRI")){
                return column;
            }
        }
        return null;
    }

    public TemplateService getTemplateService() {
        return templateService;
    }

    public void setTemplateService(TemplateService templateService) {
        this.templateService = templateService;
    }

    public DaoMysqlImpl getDao() {
        return dao;
    }

    public void setDao(DaoMysqlImpl dao) {
        this.dao = dao;
    }
}
