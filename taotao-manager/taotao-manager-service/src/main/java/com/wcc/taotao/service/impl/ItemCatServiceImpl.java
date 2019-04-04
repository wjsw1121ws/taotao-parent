package com.wcc.taotao.service.impl;

import com.wcc.taotao.mapper.TbItemCatMapper;
import com.wcc.taotao.pojo.EasyUiTreeNodeResult;
import com.wcc.taotao.pojo.TbItemCat;
import com.wcc.taotao.pojo.TbItemCatExample;
import com.wcc.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 商品分类Service实现
 * @ClassName: ItemCatServieImpl
 * @Auther: changchun_wu
 * @Date: 2018/12/26 0:29
 * @Version: 1.0
 **/

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    /**
     * @Author: changchun_wu
     * @Date: 2018/12/26 0:41
     * @Description: 根据parentId查询所有分类Service实现
     **/
    @Override
    public List<EasyUiTreeNodeResult> getItemCatList(Long patentId) {
        // 根据ParentId查询节点
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(patentId);
        // 执行查询
        List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
        // 创建结果集对象
        List<EasyUiTreeNodeResult> results = new ArrayList<>();
        // 遍历TbItemCat
        for (TbItemCat tbItemCat : list) {
            // 创建EasyUiTreeNodeResult
            EasyUiTreeNodeResult result = new EasyUiTreeNodeResult();
            // 设置EasyUiTreeNodeResult参数
            result.setId(tbItemCat.getId());
            result.setText(tbItemCat.getName());
            result.setState(tbItemCat.getIsParent()?"closed":"open");
            // 将参数添加到结果集
            results.add(result);
        }
        // 返回结果集对象
        return results;
    }
}
