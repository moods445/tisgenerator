package com.modds.generator;

import com.modds.generator.entity.DataBase;
import com.modds.generator.entity.Table;
import com.modds.generator.genera.JavaGenerator;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Runner {
    private static String path = "entity";
    private static String[] classNames = new String[]{"ShipStopping",
            "ArriveShip", "TBLUserType"};
    private static Map<String, String> fkTableNamesAndPk = new HashMap<String, String>();

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/app";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "root";

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        Connection conn = null;
        DatabaseMetaData metaData = null;
        ResultSet rs = null;
        ResultSet crs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String catalog = conn.getCatalog(); // catalog 其实也就是数据库名
            metaData = conn.getMetaData();

            DataBase dataBase = TableInfoUtils.concerTableInfo(metaData);


            if (dataBase.getTables() != null && dataBase.getTables().size() > 0) {
                for (Map.Entry<String, Table> entry : dataBase.getTables().entrySet()) {
                    dataBase.getTables().put(entry.getKey(), entry.getValue());
                    GeneratorUtils.generateXml(entry.getValue());
                    new JavaGenerator(entry.getValue()).generator();
                }
            }


        } catch (Exception e) {
            e.printStackTrace(System.out);
        } finally {
            try {
                if (null != rs) {
                    rs.close();
                }
                if (null != conn) {
                    conn.close();
                }
            } catch (Exception e2) {
            }
        }
    }



    /**
     * 适合表名为单个单词， 例如：表名是TBLUSER 类名是TBLUser;当表名是USER 类名是User;当表面是USERTYPE(两个单词)
     * 时，类名是Usertype,如果要 UserType，将期望的类名添加到classNames字段中（与数据库表名一致 不区分大小写）。
     *
     * @param tablename
     * @return
     */
    public static String getClassName(String tablename) {
        String res = tablename.toLowerCase();
        if (tablename.startsWith("TBL")) {
            return tablename.substring(0, 4) + res.substring(4);
        }
        return tablename.substring(0, 1).toUpperCase() + res.substring(1);
    }

    /**
     * 设置字段类型 MySql数据类型
     *
     * @param columnType 列类型字符串
     * @param sbpackage  封装包信息
     * @return
     */
    public static String getFieldType(String columnType, StringBuffer sbpackage) {
        /*
         * tinyblob tinyblob byte[]
            tinytext varchar java.lang.string
            blob blob byte[]
            text varchar java.lang.string
            mediumblob mediumblob byte[]
            mediumtext varchar java.lang.string
            longblob longblob byte[]
            longtext varchar java.lang.string
            enum('value1','value2',...) char java.lang.string
            set('value1','value2',...) char java.lang.string
         */
        columnType = columnType.toLowerCase();
        if (columnType.equals("varchar") || columnType.equals("nvarchar")
                || columnType.equals("char")
//                || columnType.equals("tinytext")
//                || columnType.equals("text")
//                || columnType.equals("mediumtext")
//                || columnType.equals("longtext")
                ) {
            return "String";
        } else if (columnType.equals("tinyblob")
                || columnType.equals("blob")
                || columnType.equals("mediumblob")
                || columnType.equals("longblob")) {
            return "byte[]1111";
        } else if (columnType.equals("datetime")
                || columnType.equals("date")
                || columnType.equals("timestamp")
                || columnType.equals("time")
                || columnType.equals("year")) {
            sbpackage.append("import java.util.Date;\r\n");
            return "Date";
        } else if (columnType.equals("bit")
                || columnType.equals("int")
                || columnType.equals("tinyint")
                || columnType.equals("smallint")
//                ||columnType.equals("bool")
//                ||columnType.equals("mediumint")
//                ||columnType.equals("bigint")
                ) {
            return "int";
        } else if (columnType.equals("float")) {
            return "Float";
        } else if (columnType.equals("double")) {
            return "Double";
        } else if (columnType.equals("decimal")) {
//            sbpackage.append("import java.math.BigDecimal;\r\n");
//            return "BigDecimal";
        }
        return "ErrorType";
    }

    /**
     * 设置类标题注释
     *
     * @param sbpackage
     * @param className
     */
    public static void getTitle(StringBuffer sbpackage, String className) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        sbpackage.append("\r\n/**\r\n");
        sbpackage.append("*\r\n");
        sbpackage.append("* 标题: " + className + "<br/>\r\n");
        sbpackage.append("* 说明: <br/>\r\n");
        sbpackage.append("*\r\n");
        sbpackage.append("* 作成信息: DATE: " + format.format(new Date())
                + " NAME: author\r\n");
        sbpackage.append("*\r\n");
        sbpackage.append("* 修改信息<br/>\r\n");
        sbpackage.append("* 修改日期 修改者 修改ID 修改内容<br/>\r\n");
        sbpackage.append("*\r\n");
        sbpackage.append("*/\r\n");
    }


}