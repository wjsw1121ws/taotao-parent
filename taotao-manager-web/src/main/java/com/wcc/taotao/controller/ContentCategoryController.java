package com.wcc.taotao.controller;

import com.wcc.taotao.content.service.ContentCategoryService;
import com.wcc.taotao.pojo.EasyUiTreeNodeResult;
import com.wcc.taotao.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description:
 * @ClassName: ContentCategoryController
 * @Auther: changchun_wu
 * @Date: 2019/1/6 18:54
 * @Version: 1.0
 **/

@Controller
public class ContentCategoryController {
    @Autowired
    private ContentCategoryService service;

    /**
     * @Author: changchun_wu
     * @Date: 2019/1/6 19:01
     * @Description: 查询所有分类Controller
     **/
    @RequestMapping(value = "/content/category/list",method = RequestMethod.GET)
    @ResponseBody
    public List<EasyUiTreeNodeResult> getContentCategoryList(@RequestParam(value = "id",defaultValue = "0") Long parentId){
        //1.引入服务
        //2.调用service执行查询
        List<EasyUiTreeNodeResult> contentCategoryList = service.getContentCategoryList(parentId);
        //3.返回结果集
        return contentCategoryList;
    }

    /**
     * @Author: changchun_wu
     * @Date: 2019/1/6 21:54
     * @Description: 新增分类
     * 参数: parentId:node.parentId,name:node.text
     * 返回值: E3Result
     **/
    @RequestMapping(value = "/content/category/create",method = RequestMethod.POST)
    @ResponseBody
    public E3Result addContentCategory(Long parentId,String name){
        E3Result e3Result = service.addContentCategory(parentId, name);
        return e3Result;
    }

    /**
     * @Author: changchun_wu
     * @Date: 2019/1/6 21:54
     * @Description: 更新分类
     * 参数: parentId:node.parentId,name:node.text
     * 返回值: E3Result
     **/
    /*@RequestMapping(value = "/content/category/update",method = RequestMethod.POST)
    public E3Result updateContentCategory(Long parentId,String name){

        return null;
    }*/
}
