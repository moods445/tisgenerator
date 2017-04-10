package com.modds.generator.utils;
/*
 *    Copyright 2009-2012 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

import java.math.BigDecimal;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public enum JdbcJavaType {
    /*
     * This is added to enable basic support for the
     * ARRAY data type - but a custom type handler is still required
     */
    ARRAY(Types.ARRAY, Object.class),
    BIT(Types.BIT, Boolean.class),
    TINYINT(Types.TINYINT, Byte.class),
    SMALLINT(Types.SMALLINT, Short.class),
    INTEGER(Types.INTEGER, Integer.class),
    BIGINT(Types.BIGINT, Long.class),
    FLOAT(Types.FLOAT, Float.class),
    REAL(Types.REAL, Float.class),
    DOUBLE(Types.DOUBLE, Double.class),
    NUMERIC(Types.NUMERIC, BigDecimal.class),
    DECIMAL(Types.DECIMAL, BigDecimal.class),
    CHAR(Types.CHAR, String.class),
    VARCHAR(Types.VARCHAR, String.class),
    LONGVARCHAR(Types.LONGVARCHAR, String.class),
    DATE(Types.DATE, Date.class),
    TIME(Types.TIME, Date.class),
    TIMESTAMP(Types.TIMESTAMP, Date.class),
    //    BINARY(Types.BINARY,"byte[]"),
//    VARBINARY(Types.VARBINARY,"byte[]"),
//    LONGVARBINARY(Types.LONGVARBINARY,"byte[]"),
    NULL(Types.NULL, Object.class),
    OTHER(Types.OTHER, Object.class),
    //    BLOB(Types.BLOB,"byte[]"),
    CLOB(Types.CLOB, String.class),
    BOOLEAN(Types.BOOLEAN, Boolean.class),
    //    CURSOR(-10), // Oracle
//    UNDEFINED(Integer.MIN_VALUE + 1000),
    NVARCHAR(Types.NVARCHAR, String.class), // JDK6
    NCHAR(Types.NCHAR, String.class), // JDK6
    NCLOB(Types.NCLOB, String.class), // JDK6
    STRUCT(Types.STRUCT, Object.class);

    public final int TYPE_CODE;
    public final Class JAVA_CLASS;

    private static Map<Integer, JdbcJavaType> codeLookup = new HashMap<Integer, JdbcJavaType>();

    static {
        for (JdbcJavaType type : JdbcJavaType.values()) {
            codeLookup.put(type.TYPE_CODE, type);
        }
    }

    JdbcJavaType(int code, Class cls) {
        this.TYPE_CODE = code;
        this.JAVA_CLASS = cls;
    }

    public static JdbcJavaType forCode(int code) {
        return codeLookup.get(code);
    }

    public static String getJavaClassName(int code) {
        switch (code) {
            case Types.BINARY:
            case Types.VARBINARY:
            case Types.LONGVARBINARY:
            case Types.BLOB:
                return "byte[]";
            default:
                return forCode(code).getJavaClassName();
        }
    }

    public String getJavaClassName() {
        return this.JAVA_CLASS.getSimpleName();
    }

}

