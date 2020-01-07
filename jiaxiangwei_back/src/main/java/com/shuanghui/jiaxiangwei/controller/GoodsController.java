package com.shuanghui.jiaxiangwei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/goods")
public class GoodsController {

    /**
     *
     * @param pageNumber 
     * @param pageSize
     * @param sortType
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(@RequestParam(value = "page",defaultValue = "1") int pageNumber, @RequestParam(value = "page.size", defaultValue = "10") int pageSize, @RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model, String params) {
        model.addAttribute("titlename","商品管理");

        if(!StringUtils.isEmpty(params)){

        }













        return "goods/list";
    }

 

}
