package com.nietzche.codegenerator.starter;

import com.google.common.base.Strings;
import com.nietzche.codegenerator.context.GeneratorContext;
import com.nietzche.codegenerator.generator.Generator;
import com.nietzche.codegenerator.generator.GeneratorFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Author: maxwellens
 * @Date: 2019/3/26 21:58
 */
@Slf4j
public class DefaultGeneratorStarter implements GeneratorStarter
{
    public static final String CONFIG_FILE_NAME = "generator-config.properties";

    public void run()
    {
        log.info("-------------开始执行自动化代码生成-------------------");
        Properties properties = new Properties();
        try
        {
            properties.load(this.getClass().getClassLoader().getResourceAsStream(CONFIG_FILE_NAME));
        } catch (IOException e)
        {
            e.printStackTrace();
            log.error("generator-config.properties not found in classpath");
            return;
        }
        String tables = properties.getProperty("tables");
        if (Strings.isNullOrEmpty(tables))
        {
            log.error("tables properties not config");
            return;
        }
        String[] tableNames = tables.split(",");
        //所有生成器上下文
        List<GeneratorContext> generatorContexts = new ArrayList<GeneratorContext>();
        for (String tableName : tableNames)
        {
            GeneratorContext context = new GeneratorContext(properties);
            try
            {
                context.init(tableName);
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
            generatorContexts.add(context);
        }
        String gens = properties.getProperty("generators");
        if (Strings.isNullOrEmpty(gens))
        {
            log.info("generators not config,use:pojo,dao,mapper,service,controller as default");
            gens = "pojo,dao,mapper,service,controller";
        }
        String[] generators = gens.split(",");
        for (String name : generators)
        {
            Generator generator = GeneratorFactory.getInstance(name);
            try
            {
                for (GeneratorContext context : generatorContexts)
                {
                    log.info("{}生成器开始生成表{}的代码", name, context.getTableName());
                    generator.generate(context);
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        log.info("-------------自动化代码生成完毕-------------------");
    }

}
