package com.huffman.codegenerator.generator;

import com.huffman.codegenerator.context.Field;
import com.huffman.codegenerator.context.GeneratorContext;
import org.apache.velocity.VelocityContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Pojo生成器
 *
 * @Author: maxwellens
 * @Date: 2019/3/27 14:48
 */
public class PojoGenerator extends BaseGenerator
{
    @Override
    protected void initVelocityContext(VelocityContext velocityContext, GeneratorContext generatorContext)
    {
        super.initVelocityContext(velocityContext, generatorContext);
        Set<String> imports = new HashSet<String>();
        List<Field> fields = generatorContext.getFields();
        for (Field field : fields)
        {
            System.out.printf("name:%s,columnType:%s\r\n",field.getColumnName(),field.getColumnType());
            String type = field.getPropertyType();
            if (type.equals("BigDecimal"))
            {
                imports.add("java.math.BigDecimal");
            }
            if (type.equals("Date"))
            {
                imports.add("java.util.Date");
            }
            if (type.equals("Timestamp"))
            {
                imports.add("java.util.Timestamp");
            }
        }
        velocityContext.put("imports", imports);
    }
}
