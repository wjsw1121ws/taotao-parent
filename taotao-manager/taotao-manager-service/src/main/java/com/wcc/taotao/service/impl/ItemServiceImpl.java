package com.wcc.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wcc.taotao.mapper.TbItemDescMapper;
import com.wcc.taotao.mapper.TbItemMapper;
import com.wcc.taotao.pojo.*;
import com.wcc.taotao.service.ItemService;
import com.wcc.taotao.jedis.JedisClient;
import com.wcc.taotao.utils.E3Result;
import com.wcc.taotao.utils.IDUtils;
import com.wcc.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @ClassName: TbItemService
 * @Auther: changchun_wu
 * @Date: 2018/12/24 22:43
 * @Version: 1.0
 **/

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Resource(name = "topicDestination")
    private Destination destination;

    @Value("${ITEM_INFO_KEY}")
    private String ITEM_INFO_KEY;

    @Value("${ITEM_INFO_KEY_EXPIRE}")
    private Integer ITEM_INFO_KEY_EXPIRE;

    @Autowired
    private JedisClient jedisClient;

    /**
     * @Author: changchun_wu
     * @Date: 2018/12/25 21:11
     * @Description: 分页显示所有商品Service实现
     **/
    public EasyUIDataGridResult<TbItem> getItemList(Integer page,Integer rows){
        // 合理化判断
        if (page==null) page = 1;
        if (rows==null) rows = 30;
        // 设置分页参数
        PageHelper.startPage(page,rows);
        TbItemExample tbItemExample = new TbItemExample();
        // 查询
        List<TbItem> items = itemMapper.selectByExample(tbItemExample);
        // 创建PageInfo对象
        PageInfo<TbItem> pageInfo = new PageInfo<>(items);
        // 创建EasyUI返回值对象
        EasyUIDataGridResult<TbItem> result = new EasyUIDataGridResult<>();
        // 设置参数
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        // 返回EasyUI对象
        return result;
    }

    /**
     * @Author: changchun_wu
     * @Date: 2018/12/29 17:27
     * @Description: 商品保存Service实现
     **/
    @Override
    public E3Result addItem(TbItem tbItem, String desc) {
        // 生成商品id
        final long itemId = IDUtils.genItemId();
        // 设置商品的其他属性
        tbItem.setId(itemId);
        // 商品状态，1-正常，2-下架，3-删除
        tbItem.setStatus((byte) 1);
        tbItem.setCreated(new Date());
        tbItem.setUpdated(new Date());
        // 更新商品
        itemMapper.insert(tbItem);
        // 需要使用到商品描述表
        TbItemDesc tbItemDesc = new TbItemDesc();
        // 填充商品描述表中的其他属性
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setCreated(new Date());
        tbItemDesc.setUpdated(new Date());
        int insert = itemDescMapper.insert(tbItemDesc);

        if (insert>0) {
            //添加ActiveMQ的逻辑
            jmsTemplate.send(destination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    //发送消息
                    TextMessage textMessage = session.createTextMessage(itemId + "");
                    return textMessage;
                }
            });
        }

        return E3Result.ok();
    }

    /**
     * @Author: changchun_wu
     * @Date: 2018/12/30 1:09
     * @Description: 根据主键删除商品
     **/
    @Override
    public E3Result deleteItem(List<Long> ids) {
        // 商品状态，1-正常，2-下架，3-删除
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        List<TbItem> items = itemMapper.selectByExample(example);
        for (TbItem item : items) {
            item.setStatus((byte) 3);
            itemMapper.updateByPrimaryKey(item);
        }
        return E3Result.ok();
    }

    /**
     * @Author: changchun_wu
     * @Date: 2018/12/30 17:19
     * @Description: 下架商品
     **/
    @Override
    public E3Result instockItem(List<Long> ids) {
        // 商品状态，1-正常，2-下架，3-删除
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        List<TbItem> items = itemMapper.selectByExample(example);
        for (TbItem item : items) {
            item.setStatus((byte) 2);
            itemMapper.updateByPrimaryKey(item);
        }
        return E3Result.ok();
    }

    /**
     * @Author: changchun_wu
     * @Date: 2018/12/30 17:36
     * @Description: 上架商品
     **/
    @Override
    public E3Result reshelfItem(List<Long> ids) {
        // 商品状态，1-正常，2-下架，3-删除
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        List<TbItem> items = itemMapper.selectByExample(example);
        for (TbItem item : items) {
            item.setStatus((byte) 1);
            itemMapper.updateByPrimaryKey(item);
        }
        return E3Result.ok();
    }

    @Override
    public TbItemDesc editItemDesc(Long id) {
        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(id);
        return itemDesc;
    }

    @Override
    public TbItem getItemById(Long itemId) {
        //注入mapper
        //执行查询
        //添加Jedis缓存
        //判断是否有缓存
        String item_key = null;
        try {
            item_key = ITEM_INFO_KEY+":"+itemId+":BASE";
            String jedisStr = jedisClient.get(item_key);
            if (StringUtils.isNotBlank(jedisStr)){
                //表示缓存存在
                TbItem tbItem = JsonUtils.jsonToPojo(jedisStr, TbItem.class);
                //添加过期时间
                jedisClient.expire(item_key,ITEM_INFO_KEY_EXPIRE);
                return tbItem;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //缓存不存在
        TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
        try {
            jedisClient.set(item_key,JsonUtils.objectToJson(tbItem));
            jedisClient.expire(item_key,ITEM_INFO_KEY_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回结果
        return tbItem;
    }

    @Override
    public TbItemDesc getItemDescById(Long itemId) {
        //开启服务
        //注入mapper
        //执行查询
        //添加Jedis缓存
        //判断是否有缓存
        String item_desc_key = null;
        try {
            item_desc_key = ITEM_INFO_KEY+":"+itemId +":DESC";
            String jedisStr = jedisClient.get(item_desc_key);
            if (StringUtils.isNotBlank(jedisStr)){
                //表示缓存存在
                TbItemDesc itemDesc = JsonUtils.jsonToPojo(jedisStr, TbItemDesc.class);
                //添加过期时间
                jedisClient.expire(item_desc_key,ITEM_INFO_KEY_EXPIRE);
                return itemDesc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //缓存不存在
        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
        try {
            jedisClient.set(item_desc_key,JsonUtils.objectToJson(itemDesc));
            jedisClient.expire(item_desc_key,ITEM_INFO_KEY_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回结果
        return itemDesc;
    }
}
