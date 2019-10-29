package com.hulman.codegenerator.util;

import java.util.Arrays;

/**
 * @Author: maxwellens
 * @Date: 2019/3/26 22:13
 */
public class CodeStyle
{
    public static final String SPACE = " ";
    public static final String UNDER_SCORE = "_";
    public static final String DASH = "-";
    public static final String SLASH = "\\";
    public static final String BACK_SLASH = "/";
    public static final String DOT = ".";
    public static final String VERTICAL = "|";
    private String[] segments;

    public CodeStyle(String string)
    {
        //包含分隔符，全部转成空格分割
        if (containsSeperator(string))
        {
            string = string.replace(UNDER_SCORE, SPACE).replace(UNDER_SCORE, SPACE).replace(DASH, SPACE).replace(SLASH, SPACE).replace(BACK_SLASH, SPACE).replace(DOT, SPACE).replace(SLASH, SPACE).replace(VERTICAL, SPACE);

        } else
        {
            //不包含任何分隔符，按照驼峰命名规范分词
            string = StringUtils.firstCharToUpperCase(string);
            for (int i = 0; i < string.length(); i++)
            {
                char c = string.charAt(i);
                if (StringUtils.isUpperCase(c))
                {
                    string = string.replaceFirst(c + "", SPACE + c);
                    i++;
                }
            }
            string = string.trim();
        }
        segments = StringUtils.toLowerCase(string.split(SPACE));
    }

    private static boolean containsSeperator(String string)
    {
        for (int i = 0; i < string.length(); i++)
        {
            char c = string.charAt(i);
            if (c == '-' || c == '-' || c == ' ' || c == '.' || c == '|' || c == '/' || c == '\\' || c == '_')
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 生成驼峰的类命名规范
     *
     * @return
     */
    public String toClassName()
    {
        return concatClassSegment(segments);
    }

    /**
     * 生成驼峰的类命名规范（复数形式）
     *
     * @return
     */
    public String toComplexClassName()
    {
        String[] strings = Arrays.copyOf(segments, segments.length);
        strings[segments.length - 1] = Inflector.pluralize(segments[segments.length - 1]);
        return concatClassSegment(strings);
    }

    private String concatClassSegment(String[] strings)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.length; i++)
        {
            sb.append(StringUtils.firstCharToUpperCase(strings[i]));
        }
        return sb.toString();
    }

    /**
     * 生成驼峰的实例命名规范
     *
     * @return
     */
    public String toInstanceName()
    {
        return concatInstanceSegment(segments);
    }

    /**
     * 生成驼峰的实例命名规范（复数）
     *
     * @return
     */
    public String toComplexInstanceName()
    {
        String[] strings = Arrays.copyOf(segments, segments.length);
        strings[segments.length - 1] = Inflector.pluralize(segments[segments.length - 1]);
        return concatInstanceSegment(strings);
    }

    /**
     * 生成资源命名规范（复数）
     *
     * @return
     */
    public String toComplexResourceName()
    {
        String[] strings = Arrays.copyOf(segments, segments.length);
        strings[segments.length - 1] = Inflector.pluralize(segments[segments.length - 1]);
        return StringUtils.concat(strings, DASH);
    }

    private String concatInstanceSegment(String[] segments)
    {
        String[] strings = Arrays.copyOf(segments, segments.length);
        StringBuilder sb = new StringBuilder();
        sb.append(segments[0]);
        for (int i = 1; i < segments.length; i++)
        {
            strings[i] = StringUtils.firstCharToUpperCase(segments[i]);
        }
        return StringUtils.concat(strings, "");
    }

    /**
     * 生成数据库命名规范
     *
     * @return
     */
    public String toDbName()
    {
        return StringUtils.concat(segments, UNDER_SCORE);
    }

    /**
     * 生成资源命名规范
     *
     * @return
     */
    public String toResourceName()
    {
        return StringUtils.concat(segments, DASH);
    }

}
