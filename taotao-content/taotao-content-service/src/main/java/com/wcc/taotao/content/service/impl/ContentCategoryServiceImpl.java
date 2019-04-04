package com.wcc.taotao.content.service.impl;

import com.wcc.taotao.content.service.ContentCategoryService;
import com.wcc.taotao.mapper.TbContentCategoryMapper;
import com.wcc.taotao.pojo.EasyUiTreeNodeResult;
import com.wcc.taotao.pojo.TbContentCategory;
import com.wcc.taotao.pojo.TbContentCategoryExample;
import com.wcc.taotao.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @ClassName: ContentCategoryServiceImpl
 * @Auther: changchun_wu
 * @Date: 2019/1/6 17:36
 * @Version: 1.0
 **/

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Autowired
    private TbContentCategoryMapper mapper;
    /**
     * @Author: changchun_wu
     * @Date: 2019/1/6 17:39
     * @Description: 根据parentId查询所有分类service实现
     **/
    @Override
    public List<EasyUiTreeNodeResult> getContentCategoryList(Long parentId) {
        //1.注入mapper
        //2.创建example对象
        TbContentCategoryExample example = new TbContentCategoryExample();
        //3.设置查询条件
        example.createCriteria().andParentIdEqualTo(parentId);
        //4.执行查询
        List<TbContentCategory> tbContentCategories = mapper.selectByExample(example);
        //5.将查询结果放到List<EasyUiTreeNodeResult>中
        List<EasyUiTreeNodeResult> list = new ArrayList();
        for (TbContentCategory tbContentCategory : tbContentCategories) {
            EasyUiTreeNodeResult result = new EasyUiTreeNodeResult();
            result.setId(tbContentCategory.getId());
            result.setState(tbContentCategory.getIsParent()?"closed":"open");
            result.setText(tbContentCategory.getName());
            list.add(result);
        }
        return list;
    }

    /**
     * @Author: changchun_wu
     * @Date: 2019/1/6 22:43
     * @Description: 新增节点
     **/
    @Override
    public E3Result addContentCategory(Long parentId, String name) {
        TbContentCategory category = new TbContentCategory();
        category.setCreated(new Date());
        category.setIsParent(false);
        category.setUpdated(new Date());
        category.setParentId(parentId);
        category.setName(name);
        category.setSortOrder(1);
        category.setStatus(1);
        mapper.insert(category);
        //如果更新的节点的父节点本身就是叶子节点,需要将其更新为父节点
        TbContentCategory category1 = mapper.selectByPrimaryKey(parentId);
        if (category1.getIsParent()==false){
            category1.setIsParent(true);
        }
        mapper.updateByPrimaryKey(category1);
        return E3Result.ok(category);
    }

    /**
     * @Author: changchun_wu
     * @Date: 2019/1/6 22:44
     * @Description: 更新节点
     **/
    /*@Override
    public E3Result updateContentCategory(Long parentId, String name) {
        TbContentCategory category = mapper.selectByPrimaryKey(parentId);
        category.setName(name);
        category.setUpdated(new Date());
        mapper.updateByPrimaryKey(category);
        return E3Result.ok(category);
    }*/
}

