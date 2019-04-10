package com.huffman.codegenerator.util;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: maxwellens
 * @Date: 2019/3/26 20:02
 */
public class NumberUtils
{

    public static boolean isNumberType(String type)
    {
        if (StringUtils.isBlank(type))
        {
            return false;
        }
        List<String> numberTypeList = Arrays.asList("Integer", "Long", "Double", "BigDecimal");
        for (String numberType : numberTypeList)
        {
            if (StringUtils.equalsIgnoreCase(numberType, type))
            {
                return true;
            }
        }
        return false;
    }

}
