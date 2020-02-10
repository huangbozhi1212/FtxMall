package com.leyou.page.web;


import com.leyou.page.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
public class PageController {
    /**
     * 跳转到商品详情页
     * @param model
     * @param id
     * @return
     */
    @Autowired
    private PageService pageService;

    @GetMapping("item/{id}.html")
    public String toItemPage(Model model, @PathVariable("id")Long id){
        //查询数据模型
        Map<String,Object> attributes = pageService.loadModel(id);
        //准备模型数据
        model.addAllAttributes(attributes);


        return "item";
    }


}









