package com.hulman.codegenerator.generator;

import com.hulman.codegenerator.context.Field;
import com.hulman.codegenerator.context.GeneratorContext;
import org.apache.velocity.VelocityContext;

import java.util.List;

/**
 * @Author: maxwellens
 * @Date: 2019/3/27 14:48
 */
public class MapperGenerator extends BaseGenerator
{
    @Override
    protected void initVelocityContext(VelocityContext velocityContext, GeneratorContext generatorContext)
    {
        super.initVelocityContext(velocityContext, generatorContext);
        List<Field> fields = generatorContext.getFields();
        StringBuilder colSb = new StringBuilder();
        StringBuilder proSb = new StringBuilder();
        for (Field field : fields)
        {
            if (!field.isAutoIncrement())
            {
                colSb.append("`").append(field.getColumnName()).append("`,");
                proSb.append("#{").append(field.getPropertyName()).append("},");
            }
        }
        velocityContext.put("columns", colSb.substring(0, colSb.length() - 1));
        velocityContext.put("properties", proSb.substring(0, proSb.length() - 1));
    }
}
