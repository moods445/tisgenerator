package com.modds.generator.entity;

/**
 * Created by xiejh on 2016/12/26.
 */
public class SelectAttribute {
    String id;
    String resultMap;
    String parameterType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultMap() {
        return resultMap;
    }

    public void setResultMap(String resultMap) {
        this.resultMap = resultMap;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public SelectAttribute(String id, String resultMap, String parameterType) {
        this.id = id;
        this.resultMap = resultMap;
        this.parameterType = parameterType;
    }

    public SelectAttribute() {
    }
}
