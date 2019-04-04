package com.nietzche.codegenerator.context;

import lombok.Data;

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
     * 注解
     */
    private String remark;

}
