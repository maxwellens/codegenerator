package com.nietzche.codegenerator.generator;

import com.nietzche.codegenerator.context.GeneratorContext;
import org.apache.velocity.VelocityContext;

import java.io.IOException;

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
    }
}
