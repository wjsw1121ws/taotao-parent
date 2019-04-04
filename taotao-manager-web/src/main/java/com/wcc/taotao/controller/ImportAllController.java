package com.wcc.taotao.controller;

import com.wcc.taotao.search.SearchService;
import com.wcc.taotao.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 批量导入controller层
 * @ClassName: ImportAllController
 * @Auther: changchun_wu
 * @Date: 2019/1/24 21:50
 * @Version: 1.0
 **/

@Controller
public class ImportAllController{
    @Autowired
    private SearchService searchService;
    @RequestMapping(value = "/importAll",method = RequestMethod.GET)
    @ResponseBody
    public E3Result importAllSearchItem() throws Exception{
        return searchService.importAllSearchItem();
    }
}
