package com.modds.generator;

import com.modds.generator.bean.Config;
import com.modds.generator.bean.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiejh on 2017/1/19.
 */
public class GeneratorFactory {

    List<Generator> generators;



    public GeneratorFactory(Config config) {
        Generator.setConfig(config);
        generators = new ArrayList<>();
        generators.add(new Generator());
    }

    public Generator getGenerator(Table table) {
        generators.get(0).setTable(table);
        return generators.get(0);
    }
}
