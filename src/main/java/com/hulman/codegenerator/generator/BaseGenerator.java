package com.hulman.codegenerator.generator;

import com.google.common.base.Strings;
import com.hulman.codegenerator.context.GeneratorContext;
import com.hulman.codegenerator.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * @Author: maxwellens
 * @Date: 2019/3/26 16:17
 */
@Slf4j
public class BaseGenerator implements Generator
{

    protected String vmFileName = null;
    protected String targetFileName = null;

    public void setVmFileName(String vmFileName)
    {
        this.vmFileName = vmFileName;
    }

    public void setTargetFileName(String targetFileName)
    {
        this.targetFileName = targetFileName;
    }

    protected void initVelocityContext(VelocityContext velocityContext, GeneratorContext generatorContext)
    {
        velocityContext.put("tableName", generatorContext.getTableName());
        velocityContext.put("tableComment", generatorContext.getTableComment());
        velocityContext.put("author", generatorContext.getAuthor());
        velocityContext.put("className", generatorContext.getClassName());
        velocityContext.put("complexClassName", generatorContext.getComplexClassName());
        velocityContext.put("instanceName", generatorContext.getInstanceName());
        velocityContext.put("complexInstanceName", generatorContext.getComplexInstanceName());
        velocityContext.put("resourceName", generatorContext.getResourceName());
        velocityContext.put("complexResourceName", generatorContext.getComplexResourceName());
        velocityContext.put("basePackage", generatorContext.getBasePackage());
        velocityContext.put("fields", generatorContext.getFields());
        Properties properties = generatorContext.getProperties();
        for (Object key : properties.keySet())
        {
            velocityContext.put(key.toString(), properties.getProperty(key.toString()));
        }
    }

    @Override
    public void generate(GeneratorContext generatorContext) throws IOException
    {
        VelocityContext velocityContext = new VelocityContext();
        initVelocityContext(velocityContext, generatorContext);
        if (Strings.isNullOrEmpty(vmFileName))
        {
            throw new IllegalArgumentException("vm file not set");
        }
        if (Strings.isNullOrEmpty(vmFileName))
        {
            throw new IllegalArgumentException("target file not set");
        }
        String vmPath = generatorContext.getVmPath();
        VelocityEngine engine = new VelocityEngine();
        engine.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        engine.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        engine.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
        Template template = null;
        //绝对路径
        if (FileUtils.isAbsolutePath(vmPath))
        {
            engine.init();
            template = engine.getTemplate(vmPath + vmFileName);
        } else
        {
            File absoluteFile = new File(generatorContext.getProjectPath() + vmPath + vmFileName);
            //相对于projectPath路径
            if (absoluteFile.exists())
            {
                engine.init();
                template = engine.getTemplate(vmPath + vmFileName);
            } else
            {
                //相对于classPath路径
                engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
                engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
                engine.init();
                template = engine.getTemplate(vmPath + vmFileName);
            }
        }
        File targetFile = new File(FileUtils.parseTargetFileName(targetFileName, generatorContext));
        if (targetFile.exists())
        {
            if (generatorContext.isOverWritten())
            {
                log.info("{}已存在，将替换", targetFile.getAbsoluteFile());
            } else
            {
                log.error("{}已存在，取消操作", targetFile.getAbsoluteFile());
                return;
            }
        }
        FileUtils.checkFolder(targetFile);
        FileWriter writer = new FileWriter(targetFile);
        template.merge(velocityContext, writer);
        writer.close();
    }
}
