package com.nietzche.codegenerator.generator;

import com.nietzche.codegenerator.context.GeneratorContext;
import org.apache.velocity.VelocityContext;

/**
 * @Author: maxwellens
 * @Date: 2019/3/27 14:48
 */
public class ServiceGenerator extends BaseGenerator
{
    @Override
    protected void initVelocityContext(VelocityContext velocityContext, GeneratorContext generatorContext)
    {
        super.initVelocityContext(velocityContext, generatorContext);
        vmFileName = "service.vm";
        targetPath = generatorContext.getSrcPath() + "service/";
        targetFileName = generatorContext.getClassName() + "Service.java";
    }
}
