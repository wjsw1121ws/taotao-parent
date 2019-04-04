package com.wcc.taotao.controller;

import com.wcc.taotao.content.service.ContentService;
import com.wcc.taotao.pojo.EasyUIDataGridResult;
import com.wcc.taotao.pojo.TbContent;
import com.wcc.taotao.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 内容管理Controller
 * @ClassName: ContentController
 * @Auther: changchun_wu
 * @Date: 2019/1/7 22:45
 * @Version: 1.0
 **/

@Controller
public class ContentController {
    @Autowired
    private ContentService contentService;

    /**
     * @Author: changchun_wu
     * @Date: 2019/1/7 23:00
     * @Description: 分页查询内容列表Controller
     **/
    @RequestMapping(value = "/content/query/list",method = RequestMethod.GET)
    @ResponseBody
    public EasyUIDataGridResult getContentList(Long categoryId, Integer page,Integer rows){
        EasyUIDataGridResult result = contentService.getContentList(categoryId,page,rows);
        return result;
    }

    /**
     * @Author: changchun_wu
     * @Date: 2019/1/7 23:30
     * @Description: 实现内容的添加
     **/
    @RequestMapping(value = "/content/save",method = RequestMethod.POST)
    @ResponseBody
    public E3Result addContent(TbContent tbContent){
        return contentService.addContent(tbContent);
    }
}
