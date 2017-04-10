package com.modds.generator;

import com.modds.generator.entity.DataBase;
import com.modds.generator.entity.Table;
import com.modds.generator.genera.XMLGenerator;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;

import static com.modds.generator.genera.XMLGenerator.document;

/**
 * Created by xiejh on 2016/12/26.
 */
public class GeneratorUtils {

    public static <T> void generateService(T t) {

        if (t instanceof DataBase) {
            System.out.println("生成所有的Service");
        } else if (t instanceof Table) {
            System.out.println("生成" + ((Table) t).getTable_name() + "Service");
        }

    }

    public static void generateDao() {

    }

    public static void generateXml(String tableName) {
//generateXml();
    }

    public static void generateXml(Table table) throws Exception {

        long start_ms = System.currentTimeMillis();

        XMLGenerator xmlGenerator = new XMLGenerator(table);
        //get root element
        Element rootElement =xmlGenerator.generateXml();
        document.appendChild(rootElement);

        generateFile("D:\\ideaWorkspace\\generator\\src\\main\\resources\\mapper\\" + table.generateXmlName()+".xml", document);
        document.removeChild(rootElement);
        System.out.println("System.currentTimeMillis()-start_ms = " + (System.currentTimeMillis() - start_ms));
    }


    public static void generateFile(String path, String content) throws Exception {
        FileWriter out = null;
        try {
            out = new FileWriter(path);
            if (StringUtils.isNotEmpty(content)) {
                out.write(content);
            }
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public static void generateFile(String path, Document document) throws Exception {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(path));
            transformer.transform(source, result);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

}
