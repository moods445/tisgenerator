package com.modds.generator.bean;

/**
 * Created by xiejh on 2016/12/27.
 */
public class PrimaryKey extends Column {
    short key_seq;
    String pk_name;

    public short getKey_seq() {
        return key_seq;
    }

    public void setKey_seq(short key_seq) {
        this.key_seq = key_seq;
    }

    public String getPk_name() {
        return pk_name;
    }

    public void setPk_name(String pk_name) {
        this.pk_name = pk_name;
    }
}
