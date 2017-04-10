package com.modds.generator.entity;


import com.modds.generator.utils.StringHelperUtils;
import org.apache.commons.lang3.StringUtils;

import static com.modds.generator.entity.JavaPackage.basepackage;

/**
 * Created by xiejh on 2016/12/26.
 */
public class HierarchyName {
    Table table;

    String xml_name;
    String dao_name;
    String service_name;
    String entity_name;
    String package_path;
    String commonName;
    String table_name;

    public HierarchyName(String table_name) {
        this.table_name = table_name;
        String tmp = StringHelperUtils.slide2Camel(table_name.replaceAll("xb_",""));
        this.commonName = String.valueOf(tmp.charAt(0)).toUpperCase()+tmp.substring(1);
        this.package_path = basepackage;
    }

    public String getPackage_path() {
        return package_path;
    }

    public void setPackage_path(String package_path) {
        this.package_path = package_path;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String getXml_name() {
        if(StringUtils.isEmpty(xml_name)){
            this.xml_name = commonName()+"Dao";
        }
        return xml_name;
    }

    public void setXml_name(String xml_name) {
        this.xml_name = xml_name;
    }

    public String getDao_name() {
        if(StringUtils.isEmpty(dao_name)){
            this.dao_name = commonName()+"Dao";
        }
        return dao_name;
    }
    public String getFullDao_name(){
        return package_path+".dao."+getDao_name();
    }

    public void setDao_name(String dao_name) {
        this.dao_name = dao_name;
    }


    public String getService_name() {
        if(StringUtils.isEmpty(service_name)){
            this.service_name = commonName()+"Service";
        }
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getEntity_name() {
        if(StringUtils.isEmpty(entity_name)){
            this.entity_name = commonName();
        }
        return entity_name;
    }

    public String getFullEntity_name(){
        return package_path+".entity."+getEntity_name();
    }

    public void setEntity_name(String entity_name) {
        this.entity_name = entity_name;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public  String commonName(){
        return commonName;
    }

}
