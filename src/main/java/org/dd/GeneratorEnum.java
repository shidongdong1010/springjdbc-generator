package org.dd;

import org.apache.commons.lang3.StringUtils;
import org.dd.dao.DaoMysqlImpl;
import org.dd.mapper.DbTypeMappingUtil;
import org.dd.mapper.TypeMapping;
import org.dd.model.Column;
import org.dd.model.Enumeration;
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
public class GeneratorEnum {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private DaoMysqlImpl dao;

    public void generator(List<Table> tableList,  List<Column> columnList){
        for(Table table : tableList) {
            List<Column> columns = this.getColumn(columnList, table.getTableName());
            Column columnKey = this.getColumnKey(columns);
            table.setColumns(columns);
            table.setColumnKey(columnKey);

            for(Column column : columns){
                Map<String, Object> map = new HashMap<String, Object>();
                map.putAll(ConfigUtil.configMap);
                map.put("table", table);
                map.put("list", columns);

                Enumeration enumeration = new Enumeration(table, column);
                if(enumeration.getPropertyList().size() <= 0){
                    continue;
                }

                map.put("enumeration", enumeration);

                // 转换模板文件内容
                String content = templateService.mergeTemplateIntoString("enum.vm", map);
                // 保存文件
                FileUtil.saveJavaFile(content, ConfigUtil.get("out.path"), ConfigUtil.get("enum_package"), enumeration.getClassName() + "Enum");
            }
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
