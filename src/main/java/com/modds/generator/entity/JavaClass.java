package com.modds.generator.entity;

import java.util.ArrayList;
import java.util.List;

import static com.modds.generator.entity.JavaPackage.basepackage;

/**
 * Created by xiejh on 2016/12/28.
 */
public class JavaClass {
    private String accessModifier;  // access modifiers
    private String name; // class name
    private String extendsName; //  super class name
    private List<String> interfaceName = new ArrayList<>(); // interface name
    private String packagePath;  // package path
    private List<String> importJavaPath = new ArrayList<>(); // import java path
    private List<Class> importInnerJavaPath = new ArrayList<>(); // jar java path
    private List<String> annotations = new ArrayList<>();
    private List<JavaField> javaFields = new ArrayList<>();
    private List<JavaMethod> javaMethods = new ArrayList<>() ;
    private List<Constructor> constructors = new ArrayList<>();
    private Table table;

    public String getAccessModifier() {
        return accessModifier;
    }

    public void setAccessModifier(String accessModifier) {
        this.accessModifier = accessModifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtendsName() {
        return extendsName;
    }

    public void setExtendsName(String extendsName) {
        this.extendsName = extendsName;
    }

    public String getExtendNameString(){
        if(extendsName ==null){
            return "";
        }else{
            return " extends "+ extendsName;
        }
    }

    public List<String> getInterfaceName() {
        return interfaceName;
    }
    public String getIntegerfaceNameString(){
        if(interfaceName == null || interfaceName.size() == 0){
            return "";
        }else{
            StringBuilder sb = new StringBuilder();
            sb.append(" implement ");
            for(String s: interfaceName){
                sb.append(s+",");
            }
            sb.replace(sb.length()-1,sb.length(),"");
            return sb.toString();
        }
    }



    public void setInterfaceName(List<String> interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }
    public String getPackagePathString(){
        String s ;
        if(packagePath == null){
            s = basepackage;
        }else{
            s = packagePath;
        }
        return "package "+ s +";";
    }

    public List<String> getImportJavaPath() {
        return importJavaPath;
    }
    public String getImportJavaPathString(){

        if(importJavaPath != null && importJavaPath.size()>0){
            StringBuilder sb  = new StringBuilder();
            for(String path: importJavaPath){
                sb.append("import "+ path +";");
            }
            return sb.toString();
        }else{
            return "";
        }
    }

    public void setImportJavaPath(List<String> importJavaPath) {
        this.importJavaPath = importJavaPath;
    }

    public List<Class> getImportInnerJavaPath() {
        return importInnerJavaPath;
    }

    public String getImportInnerJavaPathString(){
        if(importInnerJavaPath != null && importInnerJavaPath.size()>0) {
            StringBuilder sb = new StringBuilder();
            for(Class cls : importInnerJavaPath){
                sb.append("import "+ cls.getName()+";");
            }
            return sb.toString();
        }else{
            return "";
        }

    }

    public void setImportInnerJavaPath(List<Class> importInnerJavaPath) {
        this.importInnerJavaPath = importInnerJavaPath;
    }

    public List<Constructor> getConstructors() {
        return constructors;
    }

    public void setConstructors(List<Constructor> constructors) {
        this.constructors = constructors;
    }

    public List<String> getAnnotations() {
        return annotations;
    }

    public  String getAnnotationString(){
        StringBuilder sb = new StringBuilder();
        if(annotations !=null && annotations.size()>0){
            for(String s : annotations){
                sb.append("@"+ s +"\n");
            }
        }
        return sb.toString();
    }

    public void setAnnotations(List<String> annotations) {
        this.annotations = annotations;
    }

    public List<JavaField> getJavaFields() {
        return javaFields;
    }
    public String getJavaFieldsString(){
        if(javaFields == null || javaFields.size()==0) return "";
        else{
            StringBuilder sb = new StringBuilder();
            for(JavaField field : javaFields){
                sb.append(field.toString());
            }
            return sb.toString();
        }
    }

    public void setJavaFields(List<JavaField> javaFields) {
        this.javaFields = javaFields;
    }

    public List<JavaMethod> getJavaMethods() {
        return javaMethods;
    }

    public String getJavaMethodsString(){
        if(javaMethods == null || javaMethods.size()==0) return "";
        else {
            StringBuilder sb = new StringBuilder();
            for(JavaMethod javaMethod : javaMethods){
                sb.append(javaMethod.toString());
            }
            return sb.toString();
        }
    }

    public void setJavaMethods(List<JavaMethod> javaMethods) {
        this.javaMethods = javaMethods;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getPackagePathString());
        sb.append(getImportInnerJavaPathString());
        sb.append(getImportJavaPathString());
        sb.append(getAnnotationString());
        sb.append(accessModifier+" class ");
        sb.append(name+" ");
        sb.append(getExtendNameString());
        sb.append(getIntegerfaceNameString());
        sb.append("{");
        sb.append(getJavaFieldsString());
        sb.append(getJavaMethodsString());
        sb.append("}");
        return sb.toString();
    }

    public JavaClass(String name) {
        this.name = name;
    }

    public JavaClass() {
    }

    public JavaClass(String accessModifier, String name) {
        this.accessModifier = accessModifier;
        this.name = name;
    }

    public static JavaClass dao(Table table,String name){
        JavaClass javaClass = new JavaClass("public",table.getHierarchyName().getDao_name());

        List<String> anno = new ArrayList<>();
        anno.add("Repository");
        javaClass.setAnnotations(anno);
        javaClass.setExtendsName("BaseDao");
        javaClass.setName(name);
        return javaClass;
    }
    public static JavaClass service(Table table,String name){
        JavaClass javaClass = new JavaClass("public",table.getHierarchyName().getService_name());

        List<String> anno = new ArrayList<>();
        anno.add("Service");
        javaClass.setAnnotations(anno);
        javaClass.setName(name);

        return javaClass;
    }
}
