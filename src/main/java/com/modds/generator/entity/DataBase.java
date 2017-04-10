package com.modds.generator.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiejh on 2016/12/26.
 */
public class DataBase {
    private String db_url;
    String jdbc_driver = "com.mysql.jdbc.Driver";
    String user ;
    String password;
    String db_name;
    Map<String,Table> tables = new HashMap<>();

    public String getDb_url() {
        return db_url;
    }

    public void setDb_url(String db_url) {
        this.db_url = db_url;
    }

    public String getJdbc_driver() {
        return jdbc_driver;
    }

    public void setJdbc_driver(String jdbc_driver) {
        this.jdbc_driver = jdbc_driver;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDb_name() {
        return db_name;
    }

    public void setDb_name(String db_name) {
        this.db_name = db_name;
    }

    public Map<String, Table> getTables() {
        return tables;
    }

    public void setTables(Map<String, Table> tables) {
        this.tables = tables;
    }
}
