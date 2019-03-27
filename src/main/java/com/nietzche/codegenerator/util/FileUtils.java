package com.nietzche.codegenerator.util;

/**
 * @Author: maxwellens
 * @Date: 2019/3/26 20:02
 */
public class FileUtils
{
    public static final String COLON = ":";
    public static final String BACK_SLANT = "/";

    public static boolean isAbsolutePath(String path)
    {
        if (path.startsWith(BACK_SLANT) || path.indexOf(COLON) > 0)
        {
            return true;
        }
        return false;
    }
}
