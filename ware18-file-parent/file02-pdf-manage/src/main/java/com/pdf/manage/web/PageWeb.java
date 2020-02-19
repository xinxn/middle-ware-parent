package com.pdf.manage.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageWeb {

    @RequestMapping("/pdf_page_one")
    public String index (ModelMap map){
        return "pdf_page_one";
    }
}
