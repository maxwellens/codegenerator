package com.nietzche.codegenerator.generator;

import com.nietzche.codegenerator.generator.DaoGenerator;
import com.nietzche.codegenerator.generator.Generator;
import com.nietzche.codegenerator.generator.PojoGenerator;

/**
 * @Author: maxwellens
 * @Date: 2019/3/27 14:47
 */
public class GeneratorFactory
{
    public static final String POJO = "pojo";
    public static final String DAO = "dao";
    public static final String SERVICE = "service";
    public static final String CONTROLLER = "controller";
    public static final String MAPPER = "mapper";

    public static Generator getInstance(String name)
    {
        if (POJO.equalsIgnoreCase(name))
        {
            return new PojoGenerator();
        } else if (DAO.equalsIgnoreCase(name))
        {
            return new DaoGenerator();
        } else if (SERVICE.equalsIgnoreCase(name))
        {
            return new DaoGenerator();
        } else if (CONTROLLER.equalsIgnoreCase(name))
        {
            return new DaoGenerator();
        } else if (MAPPER.equalsIgnoreCase(name))
        {
            return new DaoGenerator();
        } else
        {
            return null;
        }
    }
}
