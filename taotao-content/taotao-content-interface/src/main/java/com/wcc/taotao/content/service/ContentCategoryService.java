package com.wcc.taotao.content.service;

import com.wcc.taotao.pojo.EasyUiTreeNodeResult;
import com.wcc.taotao.utils.E3Result;

import java.util.List;

/**
 * @Description:
 * @ClassName: ContentCategoryService
 * @Auther: changchun_wu
 * @Date: 2019/1/6 17:37
 * @Version: 1.0
 **/
public interface ContentCategoryService {
    /**
     * @Author: changchun_wu
     * @Date: 2019/1/6 17:38
     * @Description: 根据parentId查询所有分类接口
     **/
    List<EasyUiTreeNodeResult> getContentCategoryList(Long parentId);

    /**
     * @Author: changchun_wu
     * @Date: 2019/1/6 21:56
     * @Description: 新增分类
     **/
    E3Result addContentCategory(Long parentId,String name);

    /**
     * @Author: changchun_wu
     * @Date: 2019/1/6 22:44
     * @Description: 更新节点
     **/
    //E3Result updateContentCategory(Long parentId,String name);
}
