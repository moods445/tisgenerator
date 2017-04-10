package com.modds.generator.bean;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by xiejh on 2017/1/16.
 */
public class Config {

    Map<String, String> jdbc;
    String packageName;

    String out;

    Map<String,String> ftl;

    String templates;

    Map<String,String> extension;

    public Map<String, String> getExtension() {
        return extension;
    }

    public void setExtension(Map<String, String> extension) {
        this.extension = extension;
    }

    public String getTemplates() {
        return templates;
    }

    public void setTemplates(String templates) {
        this.templates = templates;
    }

    public Map<String, String> getFtl() {
        return ftl;
    }

    public void setFtl(Map<String, String> ftl) {
        this.ftl = ftl;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public Map<String, String> getJdbc() {
        return jdbc;
    }

    public void setJdbc(Map<String, String> jdbc) {
        this.jdbc = jdbc;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public static Config setConfig(String filePath) throws Exception{
        Config config;
        if(filePath.endsWith(".yml")){
            Yaml yaml = new Yaml();

            InputStream fileInputStream = new FileInputStream(new File(filePath));
            try{
                config = yaml.loadAs(fileInputStream,Config.class);
            }finally {
                fileInputStream.close();
            }

            if(fileInputStream != null){
                fileInputStream.close();
            }
        }else{
            throw new Exception("需要一个yaml文件(.yml)");
        }

        if(config != null){
            config.test();
        }else{
            throw new RuntimeException("config is null");
        }
        return config;

    }

    public void test(){
        File  f = new File(this.templates);
        if(!f.exists() && !f.isDirectory()){
            throw new RuntimeException("templates is not a folder");
        }
        for(Map.Entry<String,String> entry : ftl.entrySet()){
            File file = new File(templates+"/"+entry.getValue());
            if(!file.exists() && !file.isFile()){
                throw new RuntimeException("templates is not a file");
            }
        }
    }

}
