package com.wcc.taotao.cart.service;


import com.wcc.taotao.pojo.TbItem;

import java.util.List;

/**
 * @Description: 购物车接口
 * @ClassName: CarService
 * @Auther: changchun_wu
 * @Date: 2019/3/7 2:14
 * @Version: 1.0
 **/
public interface CartService {
    /**
     * 向redis添加购物车
     * @param userId 用户id
     * @param itemId 商品id
     * @param num 商品数目
     * @return
     */
    void addCartItem(Long userId,Long itemId,Integer num);

    /**
     * 从redis中获取购物车列表
     * @param userId
     * @return
     */
    List<TbItem> getCartItem(Long userId);

    /**
     * 更新购物车中商品的数目,写入redis缓存中
     * @param userId 用户id
     * @param itemId 商品id
     * @param num 商品数目
     */
    void updateItemNum(Long userId,Long itemId,Integer num);

    /**
     * 删除购物车中的商品,从redis中同步删除
     * @param userId
     * @param itemId
     */
    void deleteCartItem(Long userId,Long itemId);

}
