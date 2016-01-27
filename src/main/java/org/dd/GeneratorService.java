package org.dd;

import org.dd.dao.DaoMysqlImpl;
import org.dd.model.Column;
import org.dd.model.Table;
import org.dd.util.ConfigUtil;
import org.dd.util.FileUtil;
import org.dd.util.PropertyUtil;
import org.dd.util.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成Service
 * @author SDD
 * @version $v: 1.0.0, $time:2015/10/9 11:29 Exp $
 */
public class GeneratorService {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private DaoMysqlImpl dao;

    public void generator(List<Table> tableList,  List<Column> columnList){
        for(Table table : tableList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.putAll(ConfigUtil.configMap);
            // 得到该表的所有字段
            List<Column> columns = this.getColumn(columnList, table.getTableName());
            // 得到主键
            Column columnKey = this.getColumnKey(columns);
            table.setColumns(columns);
            table.setColumnKey(columnKey);

            map.put("table", table);
            map.put("columns", columns);
            map.put("packages", this.getPackageList(table));

            // 转换模板文件内容
            String baseServiceContent = templateService.mergeTemplateIntoString("baseService.vm", map);
            // 保存文件
            FileUtil.saveJavaFile(baseServiceContent, ConfigUtil.get("out.path"), ConfigUtil.get("base_service_package"), table.getClassName() + "BaseService");

            // 转换模板文件内容
            String baseServiceImplContent = templateService.mergeTemplateIntoString("baseServiceImpl.vm", map);
            // 保存文件
            FileUtil.saveJavaFile(baseServiceImplContent, ConfigUtil.get("out.path"), ConfigUtil.get("base_service_impl_package"), table.getClassName() + "BaseServiceImpl");

            // 转换模板文件内容
            String serviceContent = templateService.mergeTemplateIntoString("service.vm", map);
            // 保存文件
            FileUtil.saveJavaFile(serviceContent, ConfigUtil.get("out.path"), ConfigUtil.get("service_package"), table.getClassName() + "Service");

            // 转换模板文件内容
            String serviceImplContent = templateService.mergeTemplateIntoString("serviceImpl.vm", map);
            // 保存文件
            FileUtil.saveJavaFile(serviceImplContent, ConfigUtil.get("out.path"), ConfigUtil.get("service_impl_package"), table.getClassName() + "ServiceImpl");
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
    private List<String> getPackageList(Table table) {
        List<String> list = new ArrayList<String>();
        // 加上Model的包名
        list.add(ConfigUtil.get("model_package") + "." + table.getClassName());
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
