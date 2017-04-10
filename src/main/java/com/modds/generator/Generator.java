package com.modds.generator;

import com.modds.generator.bean.Config;
import com.modds.generator.bean.Table;
import com.modds.generator.utils.FileUtils;
import com.modds.generator.utils.StringHelperUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiejh on 2017/1/16.
 */
public class Generator {
    private Table table;
    private static Config config;

    private static Configuration cf;

    static {
        cf = new Configuration(Configuration.VERSION_2_3_22);
        cf.setDefaultEncoding("utf-8");
        cf.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    public Generator() {
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public static void setConfig(Config config) {
        Generator.config = config;
    }

    public Generator(Table table) throws Exception {
        this.table = table;
    }

    public void generate() throws Exception {

        for(Map.Entry<String,String> ftlentity : config.getFtl().entrySet()){
            String ftl  = ftlentity.getValue();
            String outfolder = config.getOut()+"/"+ftlentity.getKey();

            String filepath = "";
            switch(ftlentity.getKey()){
                case "dao" :
                case "service":
                    filepath = table.getEntityName()+ StringHelperUtils.replaceToUpperCase(ftlentity.getKey(),0);
                    break;
                case "entity" :
                    filepath = table.getEntityName();
                    break;
                case "mapper" :
                    filepath = table.getEntityName() + "Dao";
            }
            if(ftlentity.getValue().endsWith("ftlx")){
                filepath +=".xml";
            }else {
                filepath+= config.getExtension().get(ftlentity.getKey());
            }

            generate(ftl,outfolder,filepath);
        }
    }

    public void generate(String ftl,String outfolder, String outFile) throws Exception {

        FileOutputStream f = null;
        try {
            cf.setDirectoryForTemplateLoading(new File(config.getTemplates()));

            Map root = new HashMap();
            root.put("table", table);
            root.put("config", config);

            Template template = cf.getTemplate(ftl);
            FileUtils.createFolder(outfolder);

            f = new FileOutputStream(outfolder + "/" + outFile);
            Writer writer = new OutputStreamWriter(f, "utf-8");
            template.process(root, writer);
        }
        finally{
            if (f != null) {
                f.close();
            }
        }
    }
}
