package com.wcc.taotao.search.controller;

import com.wcc.taotao.pojo.SearchResult;
import com.wcc.taotao.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description: 检索Controller
 * @ClassName: SearchController
 * @Auther: changchun_wu
 * @Date: 2019/1/29 2:12
 * @Version: 1.0
 **/

@Controller
public class SearchController{
    @Autowired
    private SearchService searchService;

    @Value("${SEARCH_PAGESIZE}")
    private Integer SEARCH_PAGESIZE;

    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public String getSearchResult(@RequestParam(value = "q") String queryString, @RequestParam(defaultValue = "1") Integer page, Model model) throws Exception{
        //测试全局异常处理
        //int a = 1/0;
        queryString = new String(queryString.getBytes("ISO-8859-1"),"utf-8");
        SearchResult searchResult = searchService.getSearchResult(queryString, page, SEARCH_PAGESIZE);
        model.addAttribute("query",queryString);
        model.addAttribute("totalPages",searchResult.getPageCount());
        model.addAttribute("itemList",searchResult.getItemList());
        model.addAttribute("page",page);
        System.out.println(searchResult.getItemList().toArray());
        System.out.println(1);
        return "search";
    }
}
