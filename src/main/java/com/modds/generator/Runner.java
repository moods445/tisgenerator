package com.modds.generator;

import com.modds.generator.bean.Config;
import com.modds.generator.bean.DataBase;
import com.modds.generator.bean.Table;
import com.modds.generator.utils.JarLoader;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Map;

public class Runner {

    /**
     * 两种方法
     *
     * @param path
     */
    public static String getResource(String path) {

//        Runner.class.getResource(path).getPath();
//        Runner.class.getClassLoader().getResource(path).getPath();

        return Runner.class.getResource(path).getPath();

    }

    /**
     * 分为三个部分<br>
     * <p>1. 读取自定义的配置文件(yaml)</p>
     * <p>2. 读取数据库，为生成文件做准备</p>
     * <p>3. 生成mapper,dao,service,bean</p>
     */
    public static void main(String[] args) throws Exception {

        String configfilepath = "application.yml";
        if(args.length>0){
            String s1 = args[0];
            String s2 = args[1];
            if(s1.equals("-configfile")){
                configfilepath = s2;
            }
        }

        Config config =null;
        try {
            config = Config.setConfig("application.yml");
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            System.out.println("so load default application.yml");
            config = Config.setConfig(getResource("/application.yml"));
        }
        Connection conn = null;
        DatabaseMetaData metaData = null;
        ResultSet rs = null;
        ResultSet crs = null;
        try {
            // base data
            String jarlocation = config.getJdbc().get("location");
            String driverClass = config.getJdbc().get("driverClass");
            String db_url = config.getJdbc().get("url");
            String user = config.getJdbc().get("username");
            String password = config.getJdbc().get("password");

            JarLoader.load(jarlocation);
            Class.forName(config.getJdbc().get("driverClass"));
            conn = DriverManager.getConnection(db_url, user, password);
            metaData = conn.getMetaData();

            DataBase dataBase = TableInfoUtils.concerTableInfo(metaData);

            GeneratorFactory generatorFactory = new GeneratorFactory(config);
            if (dataBase.getTables() != null && dataBase.getTables().size() > 0) {
                for (Map.Entry<String, Table> entry : dataBase.getTables().entrySet()) {
                    generatorFactory.getGenerator(entry.getValue()).generate();
                }
            }
            System.out.println("success");


        } catch (Exception e) {
            e.printStackTrace(System.out);
        } finally {
                if (rs != null) {
                    rs.close();
                }
                if (conn != null) {
                    conn.close();
                }
        }
    }

}