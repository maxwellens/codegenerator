package com.nietzche.codegenerator.util;

import com.nietzche.codegenerator.context.GeneratorContext;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * @Author: maxwellens
 * @Date: 2019/3/26 20:02
 */
public class FileUtils
{
    public static final String COLON = ":";
    public static final String BACK_SLANT = "/";
    public static final String CONFIG_FILE_NAME = "generator-config.properties";
    public static final String SOURCE_PATH = "sourcePath:";
    public static final String RESOURCE_PATH = "resourcePath:";

    public static boolean isAbsolutePath(String path)
    {
        if (path.startsWith(BACK_SLANT) || path.indexOf(COLON) > 0)
        {
            return true;
        }
        return false;
    }

    public static void checkFolder(File file)
    {
        File parent = file.getParentFile();
        if (!parent.exists())
        {
            parent.mkdirs();
        }
    }

    public static String parseTargetFileName(String targetFileName, GeneratorContext context)
    {
        String fileName = targetFileName;
        if (fileName.startsWith(SOURCE_PATH))
        {
            fileName = context.getProjectPath() + fileName.replace(SOURCE_PATH, "src/main/java/");
        } else if (fileName.startsWith(RESOURCE_PATH))
        {
            fileName = context.getProjectPath() + fileName.replace(RESOURCE_PATH, "src/main/resources/");
        } else if (targetFileName.startsWith(BACK_SLANT))
        {
            fileName = context.getProjectPath() + fileName;
        }
        return fileName.replace("{{className}}", context.getClassName());
    }

    public static Properties loadConfigProperties()
    {
        Properties properties = new Properties();
        try
        {
            properties.load(FileUtils.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME));
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        return properties;
    }
}
