package com.wcc.taotao.cart.service.impl;

import com.wcc.taotao.cart.jedis.JedisClient;
import com.wcc.taotao.cart.service.CartService;
import com.wcc.taotao.mapper.TbItemMapper;
import com.wcc.taotao.pojo.TbItem;
import com.wcc.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @Description: 购物车service层
 * @ClassName: CartServiceImpl
 * @Auther: changchun_wu
 * @Date: 2019/3/7 2:15
 * @Version: 1.0
 **/
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private JedisClient jedisClient;

    @Value("${ITEM_CART}")
    private String ITEM_CART;

    @Autowired
    private TbItemMapper itemMapper;
    /**
     * 向购物车中添加商品
     * @param userId 用户id
     * @param itemId 商品id
     * @param num 商品数目
     */
    @Override
    public void addCartItem(Long userId, Long itemId,Integer num) {
        //1.判断购物车中该商品的是否在redis缓存中
        String value = jedisClient.hget(ITEM_CART + ":" + userId, itemId + "");
        //2.存在,则商品的数目相加
        if (num==null) num=1;
        if (StringUtils.isNotBlank(value)){
            //获取商品信息
            TbItem item = JsonUtils.jsonToPojo(value, TbItem.class);
            //设置商品数目
            item.setNum(item.getNum()+num);
            //将购物车放入redis

            jedisClient.hset(ITEM_CART + ":" + userId,itemId+"",JsonUtils.objectToJson(item));
        }
        else {
            //3.如果不存,将购物车放入redis
            //获取商品信息
            TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
            //设置商品数目
            tbItem.setNum(num);
            //获取商品图片
            if (StringUtils.isEmpty(tbItem.getImage())) {
                tbItem.setImage(tbItem.getImage().split(",")[0]);
            }
            //将购物车放入redis缓存中
            jedisClient.hset(ITEM_CART + ":" + userId, itemId + "", JsonUtils.objectToJson(tbItem));

        }
    }

    /**
     * 从redis中获取购物车列表
     * @param userId
     * @return
     */
    @Override
    public List<TbItem> getCartItem(Long userId) {
        //注入mapper
        //1.获取购物车中的所有商品
        Map<String, String> map = jedisClient.hgetAll(ITEM_CART + ":" + userId + "");
        //2.创建itemList
        List<TbItem> itemList = new ArrayList<>();
        if (map!=null) {//表示购物车中有商品
            for (Map.Entry<String, String> entry : map.entrySet()) {
                //获商品信息
                String value = entry.getValue();
                TbItem tbItem = JsonUtils.jsonToPojo(value, TbItem.class);
                //3.将商品信息放入到list中
                itemList.add(tbItem);
            }
        }
        return itemList;
    }

    /**
     * 更新购物车中商品的数目,写入redis缓存中
     * @param userId 用户id
     * @param itemId 商品id
     * @param num 商品数目
     */
    @Override
    public void updateItemNum(Long userId, Long itemId, Integer num) {
        //1.获取redis中的商品信息
        String itemStr = jedisClient.hget(ITEM_CART + ":" + userId, itemId + "");
        //2.更新商品的数目
        if (StringUtils.isNotBlank(itemStr)) {
            TbItem tbItem = JsonUtils.jsonToPojo(itemStr, TbItem.class);
            if (tbItem!=null)
                tbItem.setNum(num);
            jedisClient.hset(ITEM_CART + ":" + userId,itemId+"",JsonUtils.objectToJson(tbItem));
        }
    }

    /**
     * 删除购物车中的商品,从redis中同步删除
     * @param userId
     * @param itemId
     */
    @Override
    public void deleteCartItem(Long userId, Long itemId) {
        //1.清除缓存中的商品信息
        jedisClient.hdel(ITEM_CART + ":" + userId, itemId + "");
    }
}
