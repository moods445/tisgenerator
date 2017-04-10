package com.modds.generator.entity;

import com.modds.generator.utils.StringHelperUtils;

import java.util.HashMap;
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
    Map<String, Column> columns = new HashMap<>();
    Map<String, PrimaryKey> primarykeys = new HashMap<>();

    Map<String, Column> allColumns;


    HierarchyName hierarchyName;

    public String getTable_name() {
        return table_name;
    }

    public HierarchyName getHierarchyName() {
        return hierarchyName;
    }

    public void setHierarchyName(HierarchyName hierarchyName) {
        this.hierarchyName = hierarchyName;
    }

    public void setTable_name(String table_name) {

        String tmp = StringHelperUtils.slide2Camel(table_name.replaceAll("xb_", ""));
        String commonName = String.valueOf(tmp.charAt(0)).toUpperCase() + tmp.substring(1);

        hierarchyName = new HierarchyName(table_name);
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

    public String generateXmlName() {
        return hierarchyName.getXml_name();
    }

    public String generateXmlFullName() {
        return generateXmlName() + ".xml";
    }

    public Map<String, PrimaryKey> getPrimarykeys() {
        return primarykeys;
    }

    public void setPrimarykeys(Map<String, PrimaryKey> primarykeys) {
        this.primarykeys = primarykeys;
    }

    public Map<String, Column> getColumnsIncludePrimaryKey() {
        allColumns = new HashMap<>();
        allColumns.putAll(primarykeys);
        allColumns.putAll(columns);
        return allColumns;
    }
}
