package com.wcc.taotao.service;

import com.wcc.taotao.pojo.EasyUiTreeNodeResult;

import java.util.List;

/**
 * @Description: 上平分类Service接口
 * @ClassName: ItemCatService
 * @Auther: changchun_wu
 * @Date: 2018/12/26 0:27
 * @Version: 1.0
 **/
public interface ItemCatService{
    /**
     * @Author: changchun_wu
     * @Date: 2018/12/26 0:42
     * @Description: 根据parentId查询所有分类Service接口
     **/
    List<EasyUiTreeNodeResult> getItemCatList(Long patentId);
}
