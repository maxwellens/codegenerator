package tester;

import com.google.common.base.Strings;
import com.nietzche.codegenerator.context.GeneratorContext;
import com.nietzche.codegenerator.generator.Generator;
import com.nietzche.codegenerator.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: maxwellens
 * @Date: 2019/3/27 15:05
 */
@Slf4j
public class GeneratorTester
{
    private static ApplicationContext context;

    @BeforeClass
    public static void beforeClass()
    {
        context = new ClassPathXmlApplicationContext("classpath:spring-generator.xml");
    }

    @Test
    public void generateCode() throws IOException
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
        String[] tableNames = tables.split(",");
        //所有生成器上下文
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
            for (Generator generator : map.values())
            {
                log.info("开始生成表{}的代码", context.getTableName());
                generator.generate(context);
            }
        }
        log.info("-------------自动化代码生成完毕-------------------");
    }

    @AfterClass
    public static void doAfter()
    {
        if (context != null && context instanceof ClassPathXmlApplicationContext)
        {
            ((ClassPathXmlApplicationContext) context).close();
        }
    }
}
