package com.modds.generator.utils;

import java.io.File;

/**
 * Created by xiejh on 2017/1/19.
 */
public class FileUtils {

    public static boolean createFolder(String path){
        if(path == null || "".equals(path)) return true;
        File file =new File(path);
        if(!file.exists() && !file.isDirectory()){
            return file.mkdirs();
        }
        return true;
    }

    public static void tree(File file){
        System.out.println("tree");
        if(file.getAbsoluteFile().exists()){
            if(file.isDirectory()){
                System.out.println(file.getAbsolutePath());
                for(File p : file.listFiles()){
                    tree(p);
                }
            }else{
                System.out.println("\t\t\t\t\tfileName : " + file.getAbsolutePath());
            }
        }else{
            System.out.println(file.getAbsoluteFile()+"文件 不存在 " );
        }
    }
}
