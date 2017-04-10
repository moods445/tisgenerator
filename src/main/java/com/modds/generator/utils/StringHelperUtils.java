package com.modds.generator.utils;

/**
 * Created by xiejh on 2016/12/27.
 */
public class StringHelperUtils {

    public static String slide2Camel(String str){
        int c = (int)str.charAt(0);
        if(c >= 65 && c <= 90){
            str = str.replace(str.charAt(0), (char)(c+32));
        }

        String[] strs = str.split("_");
        StringBuilder sb = new StringBuilder();
        sb.append(strs[0]);
        for(int i=1,l = strs.length;i< l;i++){
            String s = strs[i];
            sb.append(Character.toUpperCase(s.charAt(0)));
            sb.append(s.substring(1));
        }
        return sb.toString();
    }

    public static String getField(String str){
        return "get"+replaceToUpperCase(str,0);
    }
    public static String setField(String str){
        return "set"+replaceToUpperCase(str,0);
    }

    public static String replaceToUpperCase(String str,int index){
        int c = (int)str.charAt(index);
        StringBuilder sb = new StringBuilder();
        if(c >= 97 && c <= 122){
            char ch= (char)(c-32);
            String s1 = str.substring(0,index);
            String s2 = str.substring(index+1,str.length());
            sb.append(s1);
            sb.append(ch);
            sb.append(s2);
        }
        return sb.toString();
    }

    public static String replaceToLowerCase(String str,int index){
        int c = (int)str.charAt(index);
        StringBuilder sb = new StringBuilder();
        if(c >= 65 && c <= 90){
            char ch= (char)(c+32);
            String s1 = str.substring(0,index);
            String s2 = str.substring(index+1,str.length());
            sb.append(s1);
            sb.append(ch);
            sb.append(s2);
        }
        return sb.toString();
    }
}
