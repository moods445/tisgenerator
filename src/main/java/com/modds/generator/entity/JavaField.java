package com.modds.generator.entity;

import java.util.List;

/**
 * Created by xiejh on 2016/12/28.
 */
public class JavaField {
    private String accessModifier;
    private Class type;
    private String typeName;
    private String name;
    private List<String> annotations;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getAccessModifier() {
        return accessModifier;
    }

    public void setAccessModifier(String accessModifier) {
        this.accessModifier = accessModifier;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getTypeString(){
        if(type != null){
            return type.getSimpleName();
        }else{
            return typeName;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAnnotations() {
        return annotations;
    }
    public String getAnnotationsString(){
        StringBuilder sb = new StringBuilder();
        if(annotations !=null){
            for(String s : annotations){
                sb.append("@"+ s +"\n");
            }
        }
        return sb.toString();
    }

    public void setAnnotations(List<String> annotations) {
        this.annotations = annotations;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getAnnotationsString());
        sb.append(accessModifier);
        sb.append(" ");
        sb.append(getTypeString());
        sb.append(" "+name+";");
        return sb.toString();
    }
}
