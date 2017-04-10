package com.modds.generator.bean;

import com.modds.generator.utils.StringHelperUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by xiejh on 2016/12/26.
 */
public class Table {

    String table_name;
    String table_type;
    String remarks;
    String table_cat;
    String table_schem;
    Map<String, Column> columns = new LinkedHashMap<>();
    Map<String, PrimaryKey> primarykeys = new LinkedHashMap<>();

    Map<String,Column> allColumns = new LinkedHashMap<>();

    private String entityName;

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        String entityName = StringHelperUtils.slide2Camel(table_name.toLowerCase().replaceAll("xb_",""));
        entityName = StringHelperUtils.replaceToUpperCase(entityName,0);
        this.entityName = entityName;
        this.table_name = table_name;
    }

    public String getTable_type() {
        return table_type;
    }

    public void setTable_type(String table_type) {
        this.table_type = table_type;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTable_cat() {
        return table_cat;
    }

    public void setTable_cat(String table_cat) {
        this.table_cat = table_cat;
    }

    public String getTable_schem() {
        return table_schem;
    }

    public void setTable_schem(String table_schem) {
        this.table_schem = table_schem;
    }

    public Map<String, Column> getColumns() {
        return columns;
    }

    public void setColumns(Map<String, Column> columns) {
        this.columns = columns;
    }

    public Map<String, PrimaryKey> getPrimarykeys() {
        return primarykeys;
    }

    public void setPrimarykeys(Map<String, PrimaryKey> primarykeys) {
        this.primarykeys = primarykeys;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Map<String, Column> getAllColumns() {
        return allColumns;
    }

    public void setAllColumns(Map<String, Column> allColumns) {
        this.allColumns = allColumns;
    }
}
