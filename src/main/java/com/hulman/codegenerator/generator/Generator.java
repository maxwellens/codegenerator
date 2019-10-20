package com.hulman.codegenerator.generator;

import com.hulman.codegenerator.context.GeneratorContext;

import java.io.IOException;

/**
 * @Author: maxwellens
 * @Date: 2019/3/26 16:13
 */
public interface Generator
{
    /**
     * 生成代码
     */
    void generate(GeneratorContext generatorContext) throws IOException;
}
