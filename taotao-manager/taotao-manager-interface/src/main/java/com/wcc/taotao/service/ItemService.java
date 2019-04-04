package com.wcc.taotao.service;

import com.wcc.taotao.pojo.EasyUIDataGridResult;
import com.wcc.taotao.pojo.TbItem;
import com.wcc.taotao.pojo.TbItemDesc;
import com.wcc.taotao.utils.E3Result;

import java.util.List;

/**
 * @Description: 商品列表service
 * @ClassName: TbItemService
 * @Auther: changchun_wu
 * @Date: 2018/12/24 22:47
 * @Version: 1.0
 **/
public interface ItemService{
    /**
     * @Author: changchun_wu
     * @Date: 2018/12/25 21:10
     * @Description: 分页显示所有商品
     **/
    EasyUIDataGridResult<TbItem> getItemList(Integer page, Integer rows);

    /**
     * 添加商品
     * @param tbItem
     * @param desc
     * @return
     */
    E3Result addItem(TbItem tbItem,String desc);

    /**
     * 删除商品
     * @param ids
     * @return
     */
    E3Result deleteItem(List<Long> ids);
    /**
     * 下架商品
     * @param ids
     * @return
     */
    E3Result instockItem(List<Long> ids);
    /**
     * 上架商品
     * @param ids
     * @return
     */
    E3Result reshelfItem(List<Long> ids);

    /**
     * 获取商品描述信息
     * @param id
     * @return
     */
    TbItemDesc editItemDesc(Long id);

    /**
     * 通过商品id获取商品信息
     * @param itemId
     * @return
     */
    TbItem getItemById(Long itemId);

    /**
     * 通过商品id查询商品描述信息
     * @param itemId
     * @return
     */
    TbItemDesc getItemDescById(Long itemId);
}
