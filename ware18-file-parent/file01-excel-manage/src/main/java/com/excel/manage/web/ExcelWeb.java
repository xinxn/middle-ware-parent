package com.excel.manage.web;

import com.excel.manage.util.ExcelUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

@RestController
public class ExcelWeb {

    @RequestMapping("/web/outExcel")
    public void outExcel (HttpServletResponse response) throws Exception {
        String exportName = "2020-01-user-data" ;
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename="+
                             URLEncoder.encode(exportName, "UTF-8") + ".xlsx");
        List<List<Object>> dataList = ExcelUtil.readExcel("F:\\file-type\\user-excel.xlsx") ;
        String[] headList = new String[]{"用户ID", "用户名", "手机号"} ;
        ExcelUtil.exportExcel(headList,dataList,response.getOutputStream()) ;
    }
}
