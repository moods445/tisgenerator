package com.modds.generator.genera;

import com.alibaba.fastjson.JSON;
import com.modds.generator.entity.*;
import com.modds.generator.utils.StringHelperUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.modds.generator.entity.JavaPackage.basepackage;

/**
 * Created by xiejh on 2016/12/26.
 */
public class XMLGenerator {
    public static Document document;
    public static final String Base_Column_List = "Base_Column_List";
    public static final String BaseResultMap = "BaseResultMap";

    private Table table;

    static {
        try {
            // 得到DOM解析器的工厂实例
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            // 从DOM工厂中获得DOM解析器
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            // 创建文档树模型对象
            document = dbBuilder.newDocument();
        } catch (Exception e) {
            System.err.println("XMLGenerator");
        }
    }

    public XMLGenerator(Table table) {
        this.table = table;
    }

    public Element generateXml() {
        Element root = root();
        root.appendChild(baseResultMap());
        root.appendChild(baseColumnList());
        root.appendChild(selectByPrimaryKey());
        root.appendChild(deleteByPrimaryKey());
        root.appendChild(insertSelective());
        root.appendChild(updateSelective());
        root.appendChild(selectByCondition());
        return root;
    }

    public Element root() {
        Element rootElement = document.createElement("mapper");
        rootElement.setAttribute("namespace", basepackage + ".dao." + table.getHierarchyName().getDao_name());
        return rootElement;
    }

    public Element resultMapChild(Column column) {
        Element element = document.createElement("result");
        element.setAttribute("column", column.getColumn_name());
        element.setAttribute("property", column.getColumn_name().toUpperCase());
        element.setAttribute("jdbcType", column.getType_name());
        return element;
    }

    public Element baseResultMap() {

        Map<String, String> attribute = new HashMap<>();
        attribute.put("id", BaseResultMap);
        attribute.put("type", table.getHierarchyName().getFullEntity_name());

        Element baseResultMap = resultMap(attribute);

        appenChildren(baseResultMap,resultMapId());
        appenChildren(baseResultMap,resultMapResult());

        return baseResultMap;
    }

    public Element resultMap(Map<String, String> attribute, Node... nodes) {
        return createElement("resultMap", attribute, nodes);
    }

    public Element result(Map<String, String> attribute, Node... nodes) {
        return createElement("result", attribute, nodes);
    }
    public Element[] resultMapResult(){
        Map<String,Column> columnMap = table.getColumns();
        Element[] columns= new Element[columnMap.size()];

        int i=0;
        for(Map.Entry<String,Column> columnEntry : columnMap.entrySet()){
            Map<String, String> attribute = new HashMap<>();

            attribute.put("column", columnEntry.getKey());
            attribute.put("property", columnEntry.getValue().getJavaFieldName());
            attribute.put("jdbcType", columnEntry.getValue().getType_name());
            columns[i++] = result(attribute);
        }
        return columns;
    }

    public Element id(Map<String, String> attribute, Node... nodes) {
        return createElement("id", attribute, nodes);
    }

    public Element[] resultMapId() {
        Map<String,PrimaryKey> primaryKeyMap = table.getPrimarykeys();
        if(primaryKeyMap.size() <= 0 ) return null;
        Element[] ids = new Element[table.getPrimarykeys().size()];

        int i=0;
        for(Map.Entry<String,PrimaryKey> primaryKeyEntry : primaryKeyMap.entrySet()){
            Map<String, String> attribute = new HashMap<>();

            attribute.put("column", primaryKeyEntry.getKey());
            attribute.put("property", primaryKeyEntry.getValue().getJavaFieldName());
            attribute.put("jdbcType", primaryKeyEntry.getValue().getType_name());
            ids[i++] = id(attribute);
        }
        return ids;
    }

    public Element sql(Map<String, String> attribute, Node... nodes) {
        return createElement("sql",attribute,nodes);
    }

    public Element baseColumnList(){
        Map<String,String> attribute = new HashMap<>();
        attribute.put("id",Base_Column_List);
        StringBuilder sb = new StringBuilder();
        for(String field : table.getColumnsIncludePrimaryKey().keySet()){
            sb.append(field);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        return sql(attribute,document.createTextNode(sb.toString()));
    }

    public Element insertSelective() {
        Element insertSelective = insert();
        insertSelective.appendChild(document.createTextNode("\n\tinsert into " + table.getTable_name()));
        insertSelective.appendChild(insertFieldsTrim());
        insertSelective.appendChild(insertValuesTrim());
        return insertSelective;
    }

    public Element updateSelective() {
        Map<String, String> attribute = new HashMap<>();
        attribute.put("id", String.format("update%sByPrimaryKeySelective", table.getHierarchyName().commonName()));
        attribute.put("parameterType", table.getHierarchyName().getFullEntity_name());

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, PrimaryKey> entry : table.getPrimarykeys().entrySet()) {
            String where = String.format("\t where %s = #{%s,jdbcType=%s}\n", entry.getKey(), entry.getValue().getJavaFieldName(), entry.getValue().getType_name());
            sb.append(where);
        }
        return update(attribute, updateSet(), document.createTextNode(sb.toString()));


    }

    public Element selectByCondition() {
        Map<String, String> attribute = new HashMap<>();
        attribute.put("id", String.format("select%sByCondition", table.getHierarchyName().commonName()));
        attribute.put("parameterType", table.getHierarchyName().getFullEntity_name());
        attribute.put("BaseResultMap", BaseResultMap);


        String s1 = "\n\tselect";
        String s3 = String.format("\n\tfrom %s \n\t where \n\t is_deleted = 0", table.getTable_name());

        Element selectByCondition = select(attribute, document.createTextNode(s1), include(), document.createTextNode(s3));
        for (Map.Entry<String, Column> entry : table.getColumns().entrySet()) {
            selectByCondition.appendChild(selectIf(entry.getValue()));
        }

        return selectByCondition;
    }

    public Element selectIf(Column column) {
        String text = String.format("\n\t and %s = #{%s,jdbcType=%s}", column.getColumn_name(), column.getJavaFieldName(), column.getType_name());
        String test = String.format("%s != null", column.getJavaFieldName());
        return ifDom(test, text);
    }

    public Element updateSet() {
        Element set = document.createElement("set");

        for (Map.Entry<String, Column> entry : table.getColumnsIncludePrimaryKey().entrySet()) {
            set.appendChild(updateIF(entry.getValue()));
        }

        return set;

    }

    public Element updateIF(Column column) {
        String testValue = String.format("%s != null", column.getJavaFieldName());
        Map<String, String> test = new HashMap<>();
        test.put("test", testValue);

        String testNode = String.format("%s = #{%s,jdbcType=%s},", column.getColumn_name(), column.getJavaFieldName(), column.getType_name());
        Element ifDom = ifDom(test, testNode);
        return ifDom;
    }


    public Element insertFieldsTrim() {
        Map<String, String> attribute = new HashMap<>();
        attribute.put("prefix", "(");
        attribute.put("suffix", ")");
        attribute.put("suffixOverrides", ",");

        Element trim = trim(attribute);

        for (Map.Entry<String, Column> entry : table.getColumnsIncludePrimaryKey().entrySet()) {
            Map<String, String> test = new HashMap<>();
            test.put("test", StringHelperUtils.slide2Camel(entry.getKey()) + " !=null");
            Element ifDom = ifDom(test, entry.getKey() + ",");
            trim.appendChild(ifDom);
        }
        return trim;
    }

    public Element insertValuesTrim() {

        Map<String, String> attribute = new HashMap<>();
        attribute.put("prefix", "values (");
        attribute.put("suffix", ")");
        attribute.put("suffixOverrides", ",");

        Element trim = trim(attribute);
        for (Map.Entry<String, Column> entry : table.getColumnsIncludePrimaryKey().entrySet()) {
            String test = StringHelperUtils.slide2Camel(entry.getKey()) + " != null";
            String testNode = String.format("#{%s,jdbcType=%s},", StringHelperUtils.slide2Camel(entry.getKey()), entry.getValue().getType_name());
            Element ifDom = ifDom(test, testNode);
            trim.appendChild(ifDom);
        }
        return trim;
    }


    private Element loopAdd(Map<String, Column> map, Element e) {
        if (map != null && map.size() > 0) {
            for (Map.Entry<String, Column> entry : map.entrySet()) {
                Element child = resultMapChild(entry.getValue());
                e.appendChild(child);
            }
        }
        return e;
    }

    public Element ifDom(Map<String, String> attribute, String testNode) {
        Element ifDom = document.createElement("if");
        for (Map.Entry<String, String> entry : attribute.entrySet()) {
            ifDom.setAttribute(entry.getKey(), entry.getValue());
        }

        ifDom.appendChild(document.createTextNode(testNode));
        return ifDom;
    }

    public Element ifDom(String testAttribute, String testNode) {
        Element ifDom = document.createElement("if");
        ifDom.setAttribute("test", testAttribute);

        ifDom.appendChild(document.createTextNode(testNode));
        return ifDom;
    }

    public Element trim(Map<String, String> attribute) {


        Element trim = document.createElement("trim");
        for (Map.Entry<String, String> entry :
                attribute.entrySet()) {
            trim.setAttribute(entry.getKey(), entry.getValue());
        }
//        trim.setAttribute("prefix", "(");
//        trim.setAttribute("suffix", ")");
//        trim.setAttribute("suffixOverrides", ",");
        return trim;
    }

    public Element select(SelectAttribute attribute) {

        Element select = document.createElement("select");
        Map<String, String> attr = (Map<String, String>) JSON.parse(JSON.toJSONString(attribute));
        for (Map.Entry<String, String> entry : attr.entrySet()) {
            select.setAttribute(entry.getKey(), entry.getValue());
        }
        return select;
    }

    public Element select(Map<String, String> attribute, Node... nodes) {
        return createElement("select", attribute, nodes);

    }

    public Element set(Map<String, String> attribute, Element... children) {
        return createElement("set", attribute, children);
    }

    public Element include() {
        Element include = document.createElement("include");
        include.setAttribute("refid", Base_Column_List);
        return include;
    }

    public Element include(Map<String, String> attribute, Element... children) {
        return createElement("include", attribute, children);
    }

    public Element update() {
        Element update = document.createElement("update");
        String id = String.format("delete%sByPrimaryKey", table.getHierarchyName().commonName());
        Map<String, PrimaryKey> primaryKeyMap = table.getPrimarykeys();
        String parameterType = "";
        for (Map.Entry<String, PrimaryKey> entry : primaryKeyMap.entrySet()) {
            parameterType += entry.getValue().getType_name();
        }

        return update;
    }

    public Element update(Map<String, String> attribute, Node... nodes) {
        return createElement("update", attribute, nodes);
    }

    public Element insert() {
        Element insert = document.createElement("insert");
        String id = String.format("insert%sSelective", table.getHierarchyName().commonName());
        String parameterType = String.format(table.getHierarchyName().getFullEntity_name());
        insert.setAttribute("id", id);
        insert.setAttribute("parameterType", parameterType);
        return insert;
    }

    public Element selectByPrimaryKey() {
        String id = String.format("select%sByPrimaryKey", table.getHierarchyName().commonName());
        String resultMap = BaseResultMap;
        String parameterType = "java.lang.Long";

        Text s1 = document.createTextNode("\n\tselect");

        Map<String,String> attribute = new HashMap<>();
        attribute.put("id",id);
        attribute.put("resultMap",resultMap);
        attribute.put("parameterType",parameterType);
        return select(attribute,s1,include(),from(),wherePrimaryKey());
    }

    public Element where(Node... nodes){
        Element where = document.createElement("where");
        if(nodes !=null){
            for(Node node : nodes){
                where.appendChild(node);
            }
        }
        return where;
    }

    public Element wherePrimaryKey(){
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String,PrimaryKey> primaryKeyMap : table.getPrimarykeys().entrySet()){
            if(sb.length() > 0){
                sb.append("\n\t and ");
            }
            sb.append(String.format(" %s =  #{%s,jdbcType=%s",primaryKeyMap.getKey(),primaryKeyMap.getValue().getJavaFieldName(),primaryKeyMap.getValue().getType_name()));
        }
        return where(document.createTextNode(sb.toString()));
    }

    public Element deleteByPrimaryKey() {
        Map<String, PrimaryKey> primaryKeyMap = table.getPrimarykeys();


        Element delete = update();
        StringBuilder sb = new StringBuilder();
        sb.append("\n\tupdate ");
        sb.append(table.getTable_name());
        sb.append("\n\t");
        sb.append("set is_deleted = 1,update_at = CURRENT_TIMESTAMP\n\t");
        sb.append("where\n\t   ");

        StringBuilder where = new StringBuilder();
        for (Map.Entry<String, PrimaryKey> entry : primaryKeyMap.entrySet()) {
            where.trimToSize();
            if (where.length() == 0) {
                sb.append(entry.getKey());
                sb.append(" = #{");
                sb.append(StringHelperUtils.slide2Camel(entry.getValue().getColumn_name()));
                sb.append(",jdbcType=");
                sb.append(entry.getValue().getType_name());
                sb.append("}\n");
            }
        }
        sb.append(where.toString());
        delete.appendChild(document.createTextNode(sb.toString()));
        return delete;
    }

    public Node from(){
        String from = String.format("\n\tfrom %s",table.getTable_name());
        return document.createTextNode(from);
    }

    public Element createElement(String name, Map<String, String> attribute, String testNode) {
        Element e = document.createElement(name);
        for (Map.Entry<String, String> entry : attribute.entrySet()) {
            e.setAttribute(entry.getKey(), entry.getValue());
        }
        e.appendChild(document.createTextNode(testNode));
        return e;
    }

    public Element createElement(String name, Map<String, String> attribute, Node... nodes) {
        Element e = document.createElement(name);
        if (attribute != null) {
            for (Map.Entry<String, String> entry : attribute.entrySet()) {
                e.setAttribute(entry.getKey(), entry.getValue());
            }
        }

        if (nodes != null) {
            for (Node node : nodes) {
                e.appendChild(node);
            }
        }
        return e;
    }

    public Element appenChildren(Element root,Element... children){
        if(root == null || children == null) return root;
        for(Element child : children){
            root.appendChild(child);
        }
        return root;
    }


    public static <T> List<Field> getNotNullFields(T t, Class<T> cls) {
        try {
            Field[] fields = cls.getDeclaredFields();
            List<Field> fieldList = new ArrayList<>();
            if (fields.length > 0) {
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object value = field.get(t);
                    if (value != null) {
                        fieldList.add(field);
                    }
                }
            }
            return fieldList.size() == 0 ? null : fieldList;
        } catch (Exception e) {
            System.err.println("getNotNullFields error\n" + e);
            return null;
        }
    }


}
