package com.wcc.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Description: 页面跳转
 * @ClassName: PageController
 * @Auther: changchun_wu
 * @Date: 2019/3/4 18:29
 * @Version: 1.0
 **/

@Controller
public class PageController {

    /**
     * 通过请求url或者页面
     * @param page 要跳转的page
     * @return
     */
    @RequestMapping(value = "/page/{page}",method = RequestMethod.GET)
    public String page(@PathVariable String page,String redirect, Model model){
        model.addAttribute("redirect",redirect);
        return page;
    }
}
