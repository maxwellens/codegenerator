package com.hulfman.codegenerator.generator;

import com.google.common.base.Strings;
import com.hulfman.codegenerator.context.GeneratorContext;
import com.hulfman.codegenerator.util.FileUtils;
import com.hulfman.codegenerator.util.MetaDataHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: maxwellens
 * @Date: 2019/4/4 16:06
 */
@Slf4j
public class GeneratorRunner
{
    private static final String STAR = "*";

    private ApplicationContext context;

    public GeneratorRunner(ApplicationContext context)
    {
        this.context = context;
    }

    public void run() throws IOException, SQLException
    {
        log.info("-------------开始执行自动化代码生成-------------------");
        Map<String, Generator> map = context.getBeansOfType(Generator.class);
        Properties properties = FileUtils.loadConfigProperties();
        String tables = properties.getProperty("tables");
        if (Strings.isNullOrEmpty(tables))
        {
            log.error("tables properties not found");
            return;
        }
        String[] tableNames;
        if (STAR.equals(tables))
        {
            MetaDataHelper metaDataHelper = new MetaDataHelper(properties);
            List<String> tabs = metaDataHelper.getTables();
            tableNames = new String[tabs.size()];
            tabs.toArray(tableNames);
        } else
        {
            tableNames = tables.split(",");
        }
        for (String tableName : tableNames)
        {
            log.info("开始生成表{}的代码", tableName);
            GeneratorContext context = new GeneratorContext(properties);
            try
            {
                context.init(tableName);
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
            for (Generator generator : map.values())
            {
                generator.generate(context);
            }
        }
        log.info("-------------自动化代码生成完毕-------------------");
    }
}
