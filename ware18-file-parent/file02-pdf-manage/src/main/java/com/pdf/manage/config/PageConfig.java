package com.pdf.manage.config;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class PageConfig {
    private static final String DEST = "F:\\file-type\\HTML页面2020-01-15.pdf";
    private static final String HTML = "/pdf_page_one.html";
    private static final String FONT = "C:/Windows/Fonts/simsun.ttc";

    private static Configuration freemarkerCfg = null ;
    static {
        freemarkerCfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        //freemarker的模板目录
        try {
            String path = "TODO:模板路径{自定义}" ;
            freemarkerCfg.setDirectoryForTemplateLoading(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 创建文档
     */
    private static void createPdf(String content,String dest) throws Exception {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
        fontImp.register(FONT);
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new ByteArrayInputStream(content.getBytes()), null, Charset.forName("UTF-8"), fontImp);
        document.close();
    }
    /**
     * 页面渲染
     */
    private static String freeMarkerRender(Map<String, Object> data, String htmlTmp) throws Exception {
        Writer out = new StringWriter();
        Template template = freemarkerCfg.getTemplate(htmlTmp,"UTF-8");
        template.process(data, out);
        out.flush();
        out.close();
        return out.toString();
    }

    public static void main(String[] args) throws Exception {
        Map<String,Object> data = new HashMap<> ();
        data.put("name","smile");
        data.put("author","知了") ;
        String content = PageConfig.freeMarkerRender(data,HTML);
        PageConfig.createPdf(content,DEST);
    }
}
