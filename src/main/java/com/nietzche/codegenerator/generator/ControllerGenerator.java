package com.nietzche.codegenerator.generator;

import com.nietzche.codegenerator.context.Field;
import com.nietzche.codegenerator.context.GeneratorContext;
import org.apache.velocity.VelocityContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: maxwellens
 * @Date: 2019/3/27 14:48
 */
public class ControllerGenerator extends BaseGenerator
{
    @Override
    protected void initVelocityContext(VelocityContext velocityContext, GeneratorContext generatorContext)
    {
        super.initVelocityContext(velocityContext, generatorContext);
        vmFileName = "controller.vm";
        targetPath = generatorContext.getSrcPath() + "controller/";
        targetFileName = generatorContext.getClassName() + "Controller.java";
    }
}
