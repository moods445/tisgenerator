package com.modds.generator.entity;

import com.modds.generator.utils.StringHelperUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiejh on 2016/12/28.
 */
public class JavaMethod {
    private String accessModifier;
    private Class returnType;
    private String returnTypeName;
    private String name;
    private Map<Class, String> parameters = new HashMap<>();
    private Map<String, String> parameterNames = new HashMap<>();
    private List<Throwable> throwables = new ArrayList<>();
    private String body;
    private boolean isStatic;
    private String returnBody;
    private List<String> annotations = new ArrayList<>();

    public void setReturnTypeName(String returnTypeName) {
        this.returnTypeName = returnTypeName;
    }

    public void setParameterNames(Map<String, String> parameterNames) {
        this.parameterNames = parameterNames;
    }

    public String getAccessModifier() {
        return accessModifier;
    }

    public void setAccessModifier(String accessModifier) {
        this.accessModifier = accessModifier;
    }

    public Class getReturnType() {
        return returnType;
    }

    public String getReturnTypeString() {
        String name;
        if (returnType != null) {
            name = returnType.getClass().getSimpleName();
        } else if (returnTypeName != null) {
            name = returnTypeName;
        } else {
            name = "void";
        }
        return " " + returnTypeName + " ";
    }

    public void setReturnType(Class returnType) {
        this.returnType = returnType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Class, String> getParameters() {
        return parameters;
    }

    public String getParametersString() {
        StringBuilder sb = new StringBuilder();
        if (parameters != null && parameters.size() > 0) {
            for (Map.Entry<Class, String> parameter : parameters.entrySet()) {
                sb.append(parameter.getKey().getSimpleName() + " ");
                sb.append(parameter.getValue() + ",");
            }
        }
        if (parameterNames != null && parameterNames.size() > 0) {
            for (Map.Entry<String, String> entry : parameterNames.entrySet()) {
                sb.append(entry.getKey() + " ");
                sb.append(entry.getValue() + ",");
            }
        }
        if (sb.length() > 0) {
            sb.replace(sb.length() - 1, sb.length(), "");
        }
        return sb.toString();
    }

    public void setParameters(Map<Class, String> parameters) {
        this.parameters = parameters;
    }

    public List<Throwable> getThrowables() {
        return throwables;
    }

    public void setThrowables(List<Throwable> throwables) {
        this.throwables = throwables;
    }

    public String getBody() {
        return body;
    }

    public String getBodyString() {
        return "{" + body + "}";
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(accessModifier + " ");
        sb.append(isStatic ? "static " : "");
        sb.append(getReturnTypeString());
        sb.append(name);
        sb.append("(");
        sb.append(getParametersString());
        sb.append(")");
        sb.append(getBodyString());
        return sb.toString();
    }

    public JavaMethod() {
    }

    public static class JavaDao {

        public static JavaMethod selectByPrimaryKey(Table table) {
            JavaMethod javaMethod = new JavaMethod();
            javaMethod.setAccessModifier("public");
            javaMethod.setReturnTypeName(table.getHierarchyName().getEntity_name());
            javaMethod.setName("selectByPrimaryKey");

            Map<String, String> parNames = new HashMap<>();
            parNames.put("long", "id");
            javaMethod.setParameterNames(parNames);

            String body = String.format("return sqlSession.selectOne(\"select%sByPrimaryKey\",id);", table.getHierarchyName().commonName());
            javaMethod.setBody(body);
            return javaMethod;
        }

        public static JavaMethod deleteByPrimaryKey(Table table) {
            JavaMethod javaMethod = new JavaMethod();
            javaMethod.setAccessModifier("public");
            javaMethod.setReturnTypeName("int");
            javaMethod.setName("deleteByPrimaryKey");

            Map<String, String> parNames = new HashMap<>();
            parNames.put("long", "id");
            javaMethod.setParameterNames(parNames);

            String body = String.format("return sqlSession.update(\"delete%sByPrimaryKey\",id);", table.getHierarchyName().commonName());
            javaMethod.setBody(body);
            return javaMethod;
        }

        public static JavaMethod insertSelective(Table table) {
            JavaMethod javaMethod = new JavaMethod();
            javaMethod.setAccessModifier("public");
            javaMethod.setReturnTypeName("int");
            javaMethod.setName("insertSelective");

            Map<String, String> parNames = new HashMap<>();
            parNames.put(table.getHierarchyName().getEntity_name(), "record");
            javaMethod.setParameterNames(parNames);

            String body = String.format("return sqlSession.insert(\"insert%sByPrimaryKey\",record);", table.getHierarchyName().commonName());
            javaMethod.setBody(body);
            return javaMethod;
        }

        public static JavaMethod updateByPrimaryKeySelective(Table table) {
            JavaMethod javaMethod = new JavaMethod();
            javaMethod.setAccessModifier("public");
            javaMethod.setReturnTypeName("int");
            javaMethod.setName("updateByPrimaryKeySelective");

            Map<String, String> parNames = new HashMap<>();
            parNames.put(table.getHierarchyName().getEntity_name(), "record");
            javaMethod.setParameterNames(parNames);

            String body = String.format("return sqlSession.update(\"update%sByPrimaryKeySelective\",record);", table.getHierarchyName().commonName());
            javaMethod.setBody(body);
            return javaMethod;
        }

        public static JavaMethod selectByCondition(Table table) {
            JavaMethod javaMethod = new JavaMethod();
            javaMethod.setAccessModifier("public");
            javaMethod.setReturnTypeName("List<" + table.getHierarchyName().getEntity_name() + ">");
            javaMethod.setName("selectByCondition");

            Map<String, String> parNames = new HashMap<>();
            parNames.put(table.getHierarchyName().getEntity_name(), "record");
            javaMethod.setParameterNames(parNames);

            String body = String.format("return sqlSession.selectList(\"select%sByCondition\",record);", table.getHierarchyName().commonName());
            javaMethod.setBody(body);
            return javaMethod;
        }
    }

    public static class JavaService {

        public static JavaMethod selectByPrimaryKey(Table table) {
            JavaMethod javaMethod = new JavaMethod();
            javaMethod.setAccessModifier("public");
            javaMethod.setReturnTypeName(table.getHierarchyName().getEntity_name());
            javaMethod.setName("selectByPrimaryKey");

            Map<String, String> parNames = new HashMap<>();
            parNames.put("long", "id");
            javaMethod.setParameterNames(parNames);

            String daoName = StringHelperUtils.replaceToLowerCase(table.getHierarchyName().getDao_name(),0);
            String body = String.format("return %s.selectByPrimaryKey(id);", daoName);
            javaMethod.setBody(body);
            return javaMethod;
        }

        public static JavaMethod deleteByPrimaryKey(Table table) {
            JavaMethod javaMethod = new JavaMethod();
            javaMethod.setAccessModifier("public");
            javaMethod.setReturnTypeName("int");
            javaMethod.setName("deleteByPrimaryKey");

            Map<String, String> parNames = new HashMap<>();
            parNames.put("long", "id");
            javaMethod.setParameterNames(parNames);

            String daoName = StringHelperUtils.replaceToLowerCase(table.getHierarchyName().getDao_name(),0);
            String body = String.format("return %s.deleteByPrimaryKey(id);", daoName);
            javaMethod.setBody(body);
            return javaMethod;
        }

        public static JavaMethod insertSelective(Table table) {
            JavaMethod javaMethod = new JavaMethod();
            javaMethod.setAccessModifier("public");
            javaMethod.setReturnTypeName("int");
            javaMethod.setName("insertSelective");

            Map<String, String> parNames = new HashMap<>();
            parNames.put(table.getHierarchyName().getEntity_name(), "record");
            javaMethod.setParameterNames(parNames);

            String daoName = StringHelperUtils.replaceToLowerCase(table.getHierarchyName().getDao_name(),0);
            String body = String.format("return %s.insertSelective(record);", daoName);
            javaMethod.setBody(body);
            return javaMethod;
        }

        public static JavaMethod updateByPrimaryKeySelective(Table table) {
            JavaMethod javaMethod = new JavaMethod();
            javaMethod.setAccessModifier("public");
            javaMethod.setReturnTypeName("int");
            javaMethod.setName("updateByPrimaryKeySelective");

            Map<String, String> parNames = new HashMap<>();
            parNames.put(table.getHierarchyName().getEntity_name(), "record");
            javaMethod.setParameterNames(parNames);

            String daoName = StringHelperUtils.replaceToLowerCase(table.getHierarchyName().getDao_name(),0);
            String body = String.format("return %s.updateByPrimaryKeySelective(record);", daoName);
            javaMethod.setBody(body);
            return javaMethod;
        }

        public static JavaMethod selectByCondition(Table table) {
            JavaMethod javaMethod = new JavaMethod();
            javaMethod.setAccessModifier("public");
            javaMethod.setReturnTypeName("List<" + table.getHierarchyName().getEntity_name() + ">");
            javaMethod.setName("selectByCondition");

            Map<String, String> parNames = new HashMap<>();
            parNames.put(table.getHierarchyName().getEntity_name(), "record");
            javaMethod.setParameterNames(parNames);

            String daoName = StringHelperUtils.replaceToLowerCase(table.getHierarchyName().getDao_name(),0);
            String body = String.format("return %s.selectByCondition(record);", daoName);
            javaMethod.setBody(body);
            return javaMethod;
        }

        public static JavaField dao(Table table) {
            JavaField field = new JavaField();
            field.setName(StringHelperUtils.replaceToLowerCase(table.getHierarchyName().getDao_name(), 0));
            field.setAccessModifier("private");

            List<String> anno = new ArrayList<>();
            anno.add("Autowired");
            field.setAnnotations(anno);
            field.setTypeName(table.getHierarchyName().getDao_name());
            return field;
        }
    }

}
