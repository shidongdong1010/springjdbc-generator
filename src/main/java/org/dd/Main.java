package org.dd;

import org.dd.dao.DaoMysqlImpl;
import org.dd.model.Column;
import org.dd.model.Table;
import org.dd.util.ConfigUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

/**
 * @author SDD
 * @version $v: 1.0.0, $time:2015/9/29 13:44 Exp $
 */
public class Main {

    public static void main(String args[]) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");

        // 将Properties文件中的所有属性转换成Map
        Properties p = (Properties) context.getBean("configProperties");
        Set<String> propertyNames = p.stringPropertyNames();
        Map<String, String> propertyMap = new HashMap<String, String>();
        for(String propertyName : propertyNames){
            propertyMap.put(propertyName, p.getProperty(propertyName));
        }
        ConfigUtil.setConfig(propertyMap);

        String schema = ConfigUtil.get("druid.schema");

        //List<String> tableName = Arrays.asList(new String[] {"umc_announcement_info", "umc_advertising_info"});
        //List<String> tableName = Arrays.asList(new String[] {"umc_advertising_info"});
        // List<String> tableName = Arrays.asList(new String[] {"sys_default_setting"});
        List<String> tableName = null;
        //List<String> tableName = Arrays.asList(new String[] {"itb_traffic_prize"});

        DaoMysqlImpl dao = (DaoMysqlImpl)context.getBean("dao");
        // 所有表名
        List<Table> tableList = dao.findTableAll(schema, tableName);
        // 所有字段
        List<Column> columnList = dao.findColumnAll(schema, tableName);

        // 生成Model
        GeneratorModel generatorModel = context.getBean(GeneratorModel.class);
        generatorModel.generator(tableList, columnList);

        // 生成Mpaaer
        GeneratorMapper generatorMapper = context.getBean(GeneratorMapper.class);
        generatorMapper.generator(tableList, columnList);

        // 生成Dto
        GeneratorDTO generatorDo = context.getBean(GeneratorDTO.class);
        generatorDo.generator(tableList, columnList);

        // 生成Convert
        GeneratorConvert generatorConvert = context.getBean(GeneratorConvert.class);
        generatorConvert.generator(tableList, columnList);

        // 生成Dao
        GeneratorDao generatorDao = context.getBean(GeneratorDao.class);
        generatorDao.generator(tableList, columnList);

        // 生成Service
        GeneratorService generatorService = context.getBean(GeneratorService.class);
        generatorService.generator(tableList, columnList);

        // 生成Enum
        GeneratorEnum generatorEnum = context.getBean(GeneratorEnum.class);
        generatorEnum.generator(tableList, columnList);

        // 生成返回码
        GeneratorResultCode generatorResultCode = context.getBean(GeneratorResultCode.class);
        generatorResultCode.generator(tableList, columnList);
    }
}
