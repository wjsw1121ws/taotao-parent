package com.wcc.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description:
 * @ClassName: PageController
 * @Auther: changchun_wu
 * @Date: 2018/12/22 18:19
 * @Version: 1.0
 **/
@Controller
public class PageController {
    /**
     * @Author: changchun_wu
     * @Date: 2018/12/23 17:14
     * @Description: 进入首页
     **/
    @RequestMapping(value = "/")
    public String showIndex(){
        return "index";
    }

    /**
     * 使用表达式指定请求路径
     * 当请求和返回时的路径相同,可以使用@PathVariable
     * 当参数名称和表达式名称不同时需要指定value
     * @param page
     * @return
     */
    @RequestMapping("/{pages}")
    public String showPage(@PathVariable(value = "pages") String page){
        return page;
    }
}
