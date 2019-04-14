package com.huffman.codegenerator.context;

import lombok.Data;

import java.util.Arrays;

/**
 * @Author: maxwellens
 * @Date: 2019/3/26 17:36
 */
@Data
public class Field
{
    /**
     * 数据库字段名
     */
    private String columnName;
    /**
     * 数据库字段类型
     */
    private int columnType;
    /**
     * Java属性名
     */
    private String propertyName;
    /**
     * Java数据类型
     */
    private String propertyType;
    /**
     * 资源名
     */
    private String resourceName;
    /**
     * 注解
     */
    private String comment;
    /**
     * 是否自增主键
     */
    private boolean autoIncrement;
    /**
     * 是否不为空
     */
    private boolean notNull;

    /**
     * 字段是否为数字
     *
     * @return
     */
    private boolean isNumber()
    {
        String[] target = {"Byte", "Short", "Integer", "Long", "Float", "Double", "BigDecimal"};
        return Arrays.asList(target).contains(propertyType);
    }

    /**
     * 字段是否为文本
     *
     * @return
     */
    private boolean isText()
    {
        String[] target = {"String"};
        return Arrays.asList(target).contains(propertyType);
    }

    /**
     * 是否日期类型
     *
     * @return
     */
    private boolean isDate()
    {
        String[] target = {"Date", "DateTime", "Timestamp"};
        return Arrays.asList(target).contains(propertyType);
    }

    public String getHtmlType()
    {
        if (isNumber())
        {
            return "number";
        } else
        {
            return "text";
        }
    }

}
