package com.hulfman.codegenerator.context;

import com.hulfman.codegenerator.util.StringUtils;
import lombok.Data;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: maxwellens
 * @Date: 2019/3/26 17:36
 */
@Data
public class Field
{
    /**
     * 支持的HTML控件类型
     */
    public static final String HTML_TYPE_TEXT = "text";
    public static final String HTML_TYPE_NUMBER = "number";
    public static final String HTML_TYPE_DATE = "date";
    public static final String HTML_TYPE_DATETIME = "datetime";
    public static final String HTML_TYPE_SELECT = "select";
    public static final String HTML_TYPE_RADIO = "radio";
    public static final String HTML_TYPE_SWITCH = "switch";
    /**
     * 支持的Java类型
     */
    public static final String JAVA_TYPE_BYTE = "Byte";
    public static final String JAVA_TYPE_SHORT = "Short";
    public static final String JAVA_TYPE_INTEGER = "Integer";
    public static final String JAVA_TYPE_LONG = "Long";
    public static final String JAVA_TYPE_FLOAT = "Float";
    public static final String JAVA_TYPE_DOUBLE = "Double";
    public static final String JAVA_TYPE_BIG_DECIMAL = "BigDecimal";
    public static final String JAVA_TYPE_DATE = "Date";
    public static final String JAVA_TYPE_TIMESTAMP = "Timestamp";
    public static final String JAVA_TYPE_STRING = "String";
    //public static final String JAVA_TYPE_BOOLEAN = "Boolean";
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
     * html控件类型
     */
    private String htmlType;
    /**
     * 资源名
     */
    private String resourceName;
    /**
     * 注释
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
     * 是否是枚举型
     */
    private boolean enumeration;
    /**
     * 是否作为查询条件
     */
    private boolean searchable;
    /**
     * 是否忽略该字段
     */
    private boolean ignore;
    /**
     * 枚举键值对
     */
    private Map<String, String> enumMap = new LinkedHashMap<>();

    public void parseRemark(String remark)
    {
        String[] segments = remark.split(" ");
        if (segments.length == 0)
        {
            comment = "";
        } else
        {
            comment = segments[0];
            for (int i = 1; i < segments.length; i++)
            {
                String option = segments[i];
                option = escapeEnumString(option);
                //枚举类型处理
                if (option.startsWith("-E"))
                {
                    enumeration = true;
                    htmlType = HTML_TYPE_SELECT;
                    String enums = option.substring(2, option.length());
                    String[] pairs = enums.split(",");
                    for (String pair : pairs)
                    {
                        String key;
                        String value;
                        int index = pair.indexOf(":");
                        if (index < 0)
                        {
                            key = pair;
                            value = null;
                        } else
                        {
                            key = pair.substring(0, index);
                            value = pair.substring(index + 1, pair.length());
                        }
                        enumMap.put(key, value);
                    }
                }
                //查询条件
                if (option.startsWith("-S"))
                {
                    searchable = true;
                }
                if (option.startsWith("-I"))
                {
                    ignore = true;
                }
            }
        }
    }

    /**
     * 字段是否为数字
     *
     * @return
     */
    private boolean isNumber()
    {
        String[] target = {JAVA_TYPE_BYTE, JAVA_TYPE_SHORT, JAVA_TYPE_INTEGER, JAVA_TYPE_LONG, JAVA_TYPE_FLOAT,
                JAVA_TYPE_DOUBLE, JAVA_TYPE_BIG_DECIMAL};
        return Arrays.asList(target).contains(propertyType);
    }

    /**
     * 字段是否为文本
     *
     * @return
     */
    private boolean isText()
    {
        String[] target = {JAVA_TYPE_STRING};
        return Arrays.asList(target).contains(propertyType);
    }

    /**
     * 是否日期类型
     *
     * @return
     */
    private boolean isDate()
    {
        String[] target = {JAVA_TYPE_DATE, JAVA_TYPE_TIMESTAMP};
        return Arrays.asList(target).contains(propertyType);
    }

    public void determineHtmlType()
    {
        if (htmlType != null)
        {
            return;
        }
        if (enumeration && htmlType != null)
        {
            htmlType = HTML_TYPE_SELECT;
            return;
        }
        if (isNumber())
        {
            htmlType = HTML_TYPE_NUMBER;
        } else
        {
            htmlType = HTML_TYPE_TEXT;
        }
    }

    public String getUpperPropertyName()
    {
        return StringUtils.firstCharToUpperCase(propertyName);
    }

    private String escapeEnumString(String string)
    {
        return string.replace("：", ":").replace("，", ",");
    }

}
