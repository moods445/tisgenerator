package com.modds.generator.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiejh on 2016/12/26.
 */
public class DataBase {
    Map<String,Table> tables = new HashMap<>();

    public Map<String, Table> getTables() {
        return tables;
    }

    public void setTables(Map<String, Table> tables) {
        this.tables = tables;
    }
}
