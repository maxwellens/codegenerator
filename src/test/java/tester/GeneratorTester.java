package tester;

import com.hulfman.codegenerator.generator.GeneratorRunner;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.sql.SQLException;

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
    public void generateCode() throws IOException, SQLException
    {
        GeneratorRunner runner = new GeneratorRunner(context);
        runner.run();
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
