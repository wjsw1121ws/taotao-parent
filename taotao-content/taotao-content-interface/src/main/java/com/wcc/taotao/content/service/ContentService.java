package com.wcc.taotao.content.service;

import com.wcc.taotao.pojo.EasyUIDataGridResult;
import com.wcc.taotao.pojo.TbContent;
import com.wcc.taotao.utils.E3Result;

import java.util.List;

/**
 * @Description: 内容管理接口
 * @ClassName: ContentService
 * @Auther: changchun_wu
 * @Date: 2019/1/7 22:43
 * @Version: 1.0
 **/
public interface ContentService {
    /**
     * @Author: changchun_wu
     * @Date: 2019/1/7 22:59
     * @Description: 分页查询内容列表接口
     **/
    EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows);

    /**
     * @Author: changchun_wu
     * @Date: 2019/1/8 23:26
     * @Description: 添加内容
     **/
    E3Result addContent(TbContent tbContent);

    /**
     * @Author: changchun_wu
     * @Date: 2019/1/8 23:26
     * @Description: 首页轮播图接口
     **/
    List<TbContent> getContentByCategoryId(Long CategoryId);
}
