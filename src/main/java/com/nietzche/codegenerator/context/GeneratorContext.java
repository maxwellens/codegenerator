package com.nietzche.codegenerator.context;

import com.google.common.base.Strings;
import com.nietzche.codegenerator.util.CodeStyle;
import com.nietzche.codegenerator.util.MetaDataHelper;
import lombok.Data;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * 一个GeneratorContext对应一张表
 *
 * @Author: maxwellens
 * @Date: 2019/3/26 16:55
 */
@Data
public class GeneratorContext
{
    public static final String TRUE = "true";

    public GeneratorContext(Properties properties)
    {
        this.properties = properties;
    }

    public void init(String tableName) throws SQLException
    {
        this.tableName = tableName;
        CodeStyle codeStyle = new CodeStyle(tableName);
        className = codeStyle.toClassName();
        instanceName = codeStyle.toInstanceName();
        resourceName = codeStyle.toResourceName();
        MetaDataHelper metaDataHelper = new MetaDataHelper(properties);
        fields = metaDataHelper.getFields(tableName);
        projectPath = properties.getProperty("project-path");
        if (Strings.isNullOrEmpty(projectPath))
        {
            projectPath = System.getProperty("user.dir") + "/";
        }
        vmPath = properties.getProperty("vm-path");
        if (Strings.isNullOrEmpty(vmPath))
        {
            vmPath = "vm/";
        }
        author = properties.getProperty("author");
        if (Strings.isNullOrEmpty(author))
        {
            System.getProperty("user.name");
        }
        if (TRUE.equalsIgnoreCase(properties.getProperty("over-written")))
        {
            overWritten = true;
        }
        basePackage = properties.getProperty("base-package");
        if (basePackage == null)
        {
            throw new IllegalArgumentException("base-package not config");
        }
        srcPath = properties.getProperty("src-path");
        if (Strings.isNullOrEmpty(srcPath))
        {
            srcPath = "src/main/java/" + basePackage.replace(".", "/") + "/";
        }
    }

    /**
     * 配置文件属性
     */
    private Properties properties;
    /**
     * 字段
     */
    private List<Field> fields;
    /**
     * 项目路径
     */
    private String projectPath;
    /**
     * 模板文件路径
     * 可以是绝对路径，也可以是相对路径
     * 优先相对于projectPath，如果文件不存在，则相对于classPath
     */
    private String vmPath;
    /**
     * 源代码目录
     * 可以是绝对路径，也可以是相对路径（相对于projectPath）
     */
    private String srcPath;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 作者
     */
    private String author;
    /**
     * 类名(Java驼峰命名规范)
     */
    private String className;
    /**
     * 实例名(Java驼峰命名规范)
     */
    private String instanceName;
    /**
     * 资源名(-分割)
     */
    private String resourceName;
    /**
     * 包名(.分割)
     */
    private String basePackage;
    /**
     * 是否覆盖原文件
     */
    private boolean overWritten;
}
