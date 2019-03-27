package com.nietzche.codegenerator.util;

import com.google.common.base.Strings;

/**
 * @Author: maxwellens
 * @Date: 2019/3/26 22:13
 */
public class CodeStyle
{
    public static final String SPACE = " ";
    public static final String UNDERLINE = "_";
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
            string = string.replace(UNDERLINE, SPACE).replace(UNDERLINE, SPACE).replace(DASH, SPACE).replace(SLASH,
                    SPACE).replace(BACK_SLASH, SPACE)
                    .replace(DOT, SPACE).replace(SLASH, SPACE).replace(VERTICAL, SPACE);

        } else
        {
            //不包含任何分隔符，按照驼峰命名规范分词
            string = firstCharToUpperCase(string);
            for (int i = 0; i < string.length(); i++)
            {
                char c = string.charAt(i);
                if (isUpperCase(c))
                {
                    string = string.replace(c + "", SPACE + c);
                    i++;
                }
            }
            string = string.trim();
        }
        segments = toLowerCase(string.split(SPACE));
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
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < segments.length; i++)
        {
            sb.append(firstCharToUpperCase(segments[i]));
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
        StringBuilder sb = new StringBuilder();
        sb.append(segments[0]);
        for (int i = 1; i < segments.length; i++)
        {
            sb.append(firstCharToUpperCase(segments[i]));
        }
        return sb.toString();
    }

    /**
     * 生成数据库命名规范
     *
     * @return
     */
    public String toDbName()
    {
        return concatsStringBySeperator(UNDERLINE);
    }

    /**
     * 生成资源命名规范
     *
     * @return
     */
    public String toResourceName()
    {
        return concatsStringBySeperator(DASH);
    }

    private String concatsStringBySeperator(String seperator)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < segments.length; i++)
        {
            sb.append(segments[i]).append(seperator);
        }
        return sb.toString().substring(0, sb.length() - 1);
    }

    /**
     * 字符串所有字符转小写
     *
     * @param strings
     * @return
     */
    private static String[] toLowerCase(String[] strings)
    {
        for (int i = 0; i < strings.length; i++)
        {
            strings[i] = strings[i].toLowerCase();
        }
        return strings;
    }

    /**
     * 首字母转小写
     *
     * @param string
     * @return
     */
    private static String firstCharToLowerCase(String string)
    {
        if (Strings.isNullOrEmpty(string))
        {
            return string;
        }
        char firstChar = string.charAt(0);
        if (isUpperCase(firstChar))
        {
            firstChar += 32;
            return concatString(string, firstChar);
        } else
        {
            return string;
        }
    }

    /**
     * 首字母转大写
     *
     * @param string
     * @return
     */
    private static String firstCharToUpperCase(String string)
    {
        if (Strings.isNullOrEmpty(string))
        {
            return string;
        }
        char firstChar = string.charAt(0);
        if (isLowerCase(firstChar))
        {
            firstChar -= 32;
            return concatString(string, firstChar);
        } else
        {
            return string;
        }
    }

    private static String concatString(String string, char firstChar)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(firstChar);
        for (int i = 1; i < string.length(); i++)
        {
            sb.append(string.charAt(i));
        }
        return sb.toString();
    }

    /**
     * 字符是否是大写字母
     *
     * @param c
     * @return
     */
    private static boolean isUpperCase(char c)
    {
        return c >= 65 && c <= 90;
    }

    /**
     * 字符是否是小写字母
     *
     * @param c
     * @return
     */
    private static boolean isLowerCase(char c)
    {
        return c >= 97 && c <= 122;
    }

    public static void main(String[] args)
    {
        String source = "thank.you very-much";
        //String source = "ThankYouVeryMuch";
        long t1 = System.currentTimeMillis();
        CodeStyle codeStyle = new CodeStyle(source);
        long t2 = System.currentTimeMillis();
        System.out.println(t2 - t1);
        System.out.println("sourceName:" + source);
        System.out.println("className:" + codeStyle.toClassName());
        System.out.println("instanceName:" + codeStyle.toInstanceName());
        System.out.println("resourceName:" + codeStyle.toResourceName());
        System.out.println("dbName:" + codeStyle.toDbName());
    }
}
