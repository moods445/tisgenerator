package com.modds.generator.bean;

import com.modds.generator.utils.JdbcJavaType;
import com.modds.generator.utils.StringHelperUtils;

/**
 * Created by xiejh on 2016/12/26.
 */
public class Column {

    public String column_name;
    int data_type; //对应的java.sql.Types的SQL类型(列类型ID)
    String type_name; //java.sql.Types类型名称(列类型名称)
    String jdbc_name; // mybatis 中jdbcType

    int column_size;  //列大小
    int decimal_digits;  //小数位数
    int num_prec_radix; //基数（通常是10或2） --未知

    /**
     * 0 (columnNoNulls) - 该列不允许为空
     * 1 (columnNullable) - 该列允许为空
     * 2 (columnNullableUnknown) - 不确定该列是否为空
     */
    int nullable;

    /**
     * ISO规则用来确定某一列的是否可为空(等同于NULLABLE的值:[ 0:'YES'; 1:'NO'; 2:''; ])
     * YES -- 该列可以有空值;
     * NO -- 该列不能为空;
     * 空字符串--- 不知道该列是否可为空
     */
    String is_nullable;

    String remarks; // 列描述

    String column_def; // 默认值

    int char_octet_length; // 对于 char 类型，该长度是列中的最大字节数

    int ordinal_position; //表中列的索引（从1开始）

    String propertyName;

    String propertyType;

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getColumn_name() {
        return column_name;
    }

    public void setColumn_name(String column_name) {
        this.propertyName = StringHelperUtils.slide2Camel(column_name.toLowerCase());
        this.column_name = column_name;
    }

    public int getData_type() {
        return data_type;
    }

    public void setData_type(int data_type) {
        this.data_type = data_type;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.propertyType = JdbcJavaType.forCode(this.data_type).JAVA_CLASS.getSimpleName();
        this.jdbc_name= JdbcJavaType.forCode(this.data_type).getJavaClassName();
        this.type_name = type_name;
    }

    public String getJdbc_name() {
        return jdbc_name;
    }

    public void setJdbc_name(String jdbc_name) {
        this.jdbc_name = jdbc_name;
    }

    public int getColumn_size() {
        return column_size;
    }

    public void setColumn_size(int column_size) {
        this.column_size = column_size;
    }

    public int getDecimal_digits() {
        return decimal_digits;
    }

    public void setDecimal_digits(int decimal_digits) {
        this.decimal_digits = decimal_digits;
    }

    public int getNum_prec_radix() {
        return num_prec_radix;
    }

    public void setNum_prec_radix(int num_prec_radix) {
        this.num_prec_radix = num_prec_radix;
    }

    public int getNullable() {
        return nullable;
    }

    public void setNullable(int nullable) {
        this.nullable = nullable;
    }

    public String getIs_nullable() {
        return is_nullable;
    }

    public void setIs_nullable(String is_nullable) {
        this.is_nullable = is_nullable;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getColumn_def() {
        return column_def;
    }

    public void setColumn_def(String column_def) {
        this.column_def = column_def;
    }

    public int getChar_octet_length() {
        return char_octet_length;
    }

    public void setChar_octet_length(int char_octet_length) {
        this.char_octet_length = char_octet_length;
    }

    public int getOrdinal_position() {
        return ordinal_position;
    }

    public void setOrdinal_position(int ordinal_position) {
        this.ordinal_position = ordinal_position;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
}
