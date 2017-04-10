package com.modds.generator.genera;

import com.modds.generator.entity.JavaClass;
import com.modds.generator.entity.JavaMethod;
import com.modds.generator.entity.Table;

import java.io.FileWriter;
import java.lang.reflect.Method;

import static com.modds.generator.entity.JavaPackage.basepackage;

/**
 * Created by xiejh on 2016/12/28.
 */
public class JavaGenerator {
    private Table table;
    private JavaClass javaClass;

    public JavaGenerator(Table table) {
        this.table = table;
    }

    public JavaGenerator() {
    }

    public void generator() throws Exception {
        JavaClass dao = JavaClass.dao(table,table.getHierarchyName().getDao_name());
        dao.getJavaMethods().add(JavaMethod.JavaDao.insertSelective(table));
        dao.getJavaMethods().add(JavaMethod.JavaDao.updateByPrimaryKeySelective(table));
        dao.getJavaMethods().add(JavaMethod.JavaDao.deleteByPrimaryKey(table));
        dao.getJavaMethods().add(JavaMethod.JavaDao.selectByPrimaryKey(table));
        dao.getJavaMethods().add(JavaMethod.JavaDao.selectByCondition(table));

        JavaClass service = JavaClass.service(table,table.getHierarchyName().getService_name());
        service.getJavaMethods().add(JavaMethod.JavaService.insertSelective(table));
        service.getJavaMethods().add(JavaMethod.JavaService.updateByPrimaryKeySelective(table));
        service.getJavaMethods().add(JavaMethod.JavaService.deleteByPrimaryKey(table));
        service.getJavaMethods().add(JavaMethod.JavaService.selectByPrimaryKey(table));
        service.getJavaMethods().add(JavaMethod.JavaService.selectByCondition(table));
        service.getJavaFields().add(JavaMethod.JavaService.dao(table));

        FileWriter fw = null;
        FileWriter daoFw = null;
        try {
            daoFw = new FileWriter(String.format("d://tmp/java/test/%s.java",dao.getName()));
            daoFw.write(dao.toString());

            fw = new FileWriter(String.format("d://tmp/java/test/%s.java",service.getName()));
            fw.write(service.toString());
        } catch (Exception e) {

        } finally {
            if (fw != null) {
                fw.close();
            }
            if(daoFw != null){
                daoFw.close();
            }
        }

    }


}
