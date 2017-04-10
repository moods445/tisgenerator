package com.modds.generator;

import com.alibaba.fastjson.JSON;
import com.modds.generator.entity.Column;
import com.modds.generator.entity.DataBase;
import com.modds.generator.entity.PrimaryKey;
import com.modds.generator.entity.Table;
import com.modds.generator.utils.StringHelperUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

/**
 * Created by xiejh on 2016/12/28.
 */
public class TableInfoUtils {

    public static DataBase concerTableInfo(DatabaseMetaData metaData) throws Exception {
        DataBase dataBase = new DataBase();
        // 获取表
        ResultSet rs = null;
        rs = metaData.getTables(null, "%", "%", new String[]{"TABLE"});
        while (rs.next()) {
            Table table = new Table();

            String tablename = rs.getString("TABLE_NAME");
            table.setTable_name(tablename);
            dataBase.getTables().put(tablename, table);

            table.setTable_type(rs.getString("TABLE_TYPE"));
            table.setRemarks(rs.getString("REMARKS"));
            table.setTable_cat(rs.getString("TABLE_CAT"));
            table.setTable_schem(rs.getString("TABLE_SCHEM"));

            StringBuffer sb = new StringBuffer();
            StringBuffer sbpackage = new StringBuffer();

            // 获取当前表的非主键列
            TableInfoUtils.converColumnInfo(metaData, table);

            // 获取主键列
            converPrimaryKeyInfo(metaData,table);

        }
        return dataBase;
    }

    public static void converPrimaryKeyInfo(DatabaseMetaData metaData, Table table) throws Exception {
        ResultSet prs = null;
        ResultSet crs = null;
        prs = metaData.getPrimaryKeys(null, "%", table.getTable_name());
        PrimaryKey primaryKey = null;
        while (prs.next()) {
            short keySeq = prs.getShort("KEY_SEQ");//序列号(主键内值1表示第一列的主键，值2代表主键内的第二列)
            String pkName = prs.getString("PK_NAME"); //主键名称
            String columnName = prs.getString("COLUMN_NAME");//列名
            primaryKey = conversionToChild(table.getColumns().get(columnName),Column.class,PrimaryKey.class);
            primaryKey.setKey_seq(keySeq);
            primaryKey.setPk_name(pkName);



            table.getColumns().remove(columnName);
            table.getPrimarykeys().put(columnName,primaryKey);
        }
    }

    public static <T extends Column> T getColumnInfo(ResultSet crs,Class<T> cls,T t) throws Exception {
        T column = t;
        if(column == null){
            column = cls.newInstance();
        }
        String tableCat = crs.getString("TABLE_CAT");  //表类别（可能为空）
        String tableSchemaName = crs.getString("TABLE_SCHEM");  //表模式（可能为空）,在oracle中获取的是命名空间,其它数据库未知
        String tableName_ = crs.getString("TABLE_NAME");  //表名


        String columnName = crs.getString("COLUMN_NAME");  //列名
        column.setColumn_name(columnName);


        int dataType = crs.getInt("DATA_TYPE");     //对应的java.sql.Types的SQL类型(列类型ID)
        column.setData_type(dataType);

        String dataTypeName = crs.getString("TYPE_NAME");  //java.sql.Types类型名称(列类型名称)
        column.setType_name(dataTypeName);

        int columnSize = crs.getInt("COLUMN_SIZE");  //列大小
        column.setColumn_size(columnSize);

        int decimalDigits = crs.getInt("DECIMAL_DIGITS");  //小数位数
        column.setDecimal_digits(decimalDigits);

        int numPrecRadix = crs.getInt("NUM_PREC_RADIX");  //基数（通常是10或2） --未知
        column.setNum_prec_radix(numPrecRadix);

        /**
         *  0 (columnNoNulls) - 该列不允许为空
         *  1 (columnNullable) - 该列允许为空
         *  2 (columnNullableUnknown) - 不确定该列是否为空
         */
        int nullAble = crs.getInt("NULLABLE");  //是否允许为null
        column.setNullable(nullAble);

        String remarks = crs.getString("REMARKS");  //列描述
        column.setRemarks(remarks);

        String columnDef = crs.getString("COLUMN_DEF");  //默认值
        column.setColumn_def(columnDef);

        int charOctetLength = crs.getInt("CHAR_OCTET_LENGTH");    // 对于 char 类型，该长度是列中的最大字节数
        column.setChar_octet_length(charOctetLength);


        int ordinalPosition = crs.getInt("ORDINAL_POSITION");   //表中列的索引（从1开始）
        column.setOrdinal_position(ordinalPosition);

        /**
         * ISO规则用来确定某一列的是否可为空(等同于NULLABLE的值:[ 0:'YES'; 1:'NO'; 2:''; ])
         * YES -- 该列可以有空值;
         * NO -- 该列不能为空;
         * 空字符串--- 不知道该列是否可为空
         */
        String isNullAble = crs.getString("IS_NULLABLE");
        column.setIs_nullable(isNullAble);
        return column;
    }

    public static void converColumnInfo(DatabaseMetaData metaData, Table table) throws Exception {

        ResultSet crs = null;

        crs = metaData.getColumns(null, "%", table.getTable_name(), "%");

        Column column = null;
        while (crs.next()) {
            column = getColumnInfo(crs,Column.class,null);
            table.getColumns().put(column.getColumn_name(),column);
        }
    }

    /**
     * 将父类转换为子类
     * @param t
     * @param tcls
     * @param scls
     * @param <T>
     * @param <S>
     * @return
     * @throws Exception
     */
    public static <T,S extends T> S conversionToChild(T t,Class<T> tcls,Class<S> scls)throws Exception{
        S s = scls.newInstance();

        // 私有字段
        Field[] fields = tcls.getDeclaredFields();
        if(fields != null){
            for(Field field : fields){
                field.setAccessible(true);
                if(field.get(t) == null) continue;
                setFieldValue(s,field,field.get(t));
            }
        }

        // 公有字段
        fields = tcls.getFields();
        if(fields != null){
            for(Field field : fields){
                if(field.get(t) == null) continue;
                Field sfield = scls.getField(field.getName());
                sfield.set(s,field.get(t));
            }
        }

        return s;


    }

    public static <T,V> void setFieldValue(T t,Field field,V v)throws Exception{
        Method method = t.getClass().getMethod(StringHelperUtils.setField(field.getName()),field.getType());
        method.invoke(t,v);
    }

}
