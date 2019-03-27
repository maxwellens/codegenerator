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
     * 字段名
     */
    private String name;
    /**
     * 数据库类型
     */
    private int dbType;
    /**
     * Java数据类型
     */
    private String javaType;
    /**
     * 注解
     */
    private String remark;

}
