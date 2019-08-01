package com.hulfman.codegenerator.util;

import com.google.common.base.Strings;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: maxwellens
 */
public class StringUtils
{
    /**
     * 字符串所有字符转小写
     *
     * @param strings
     * @return
     */
    public static String[] toLowerCase(String[] strings)
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
    public static String firstCharToLowerCase(String string)
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
    public static String firstCharToUpperCase(String string)
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

    public static String concatString(String string, char firstChar)
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
    public static boolean isUpperCase(char c)
    {
        return c >= 65 && c <= 90;
    }

    /**
     * 字符是否是小写字母
     *
     * @param c
     * @return
     */
    public static boolean isLowerCase(char c)
    {
        return c >= 97 && c <= 122;
    }

    public static String concat(List<String> list, String seperator)
    {
        if (list == null || list.size() == 0)
        {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String string : list)
        {
            if (string != null && string.length() > 0)
            {
                sb.append(string).append(seperator);
            }
        }
        if (sb.length() > 0)
        {
            return sb.substring(0, sb.length() - seperator.length());
        } else
        {
            return "";
        }
    }

    public static String concat(List<String> list)
    {
        return concat(list, ",");
    }

    public static String concat(String[] strings, String seperator)
    {
        return concat(Arrays.asList(strings), seperator);
    }

    public static String concat(String... strings)
    {
        return concat(Arrays.asList(strings), ",");
    }
}
