package com.wcc.taotao.order.service.impl;

import com.wcc.taotao.mapper.TbOrderItemMapper;
import com.wcc.taotao.mapper.TbOrderMapper;
import com.wcc.taotao.mapper.TbOrderShippingMapper;
import com.wcc.taotao.jedis.JedisClient;
import com.wcc.taotao.order.pojo.OrderInfo;
import com.wcc.taotao.order.service.OrderService;
import com.wcc.taotao.pojo.TbOrderItem;
import com.wcc.taotao.pojo.TbOrderShipping;
import com.wcc.taotao.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Description: 订单service层
 * @ClassName: OrderServiceImpl
 * @Auther: changchun_wu
 * @Date: 2019/3/10 15:45
 * @Version: 1.0
 **/

@Service
public class OrderServiceImpl implements OrderService {

    @Value("${ORDER_ID}")
    private String ORDER_ID;
    @Value("${ORDER_DETAIL_ID}")
    private String ORDER_DETAIL_ID;
    @Autowired
    private TbOrderMapper orderMapper;
    @Autowired
    private TbOrderItemMapper orderItemMapper;
    @Autowired
    private TbOrderShippingMapper orderShippingMapper;

    @Autowired
    private JedisClient jedisClient;
    /**
     * 创建订单
     * @param orderInfo 订单详情
     * @return
     */
    @Override
    public E3Result createOrder(OrderInfo orderInfo) {
        //1.注入mapper
        //2.注入服务
        //3.补全订单信息
        //生成订单id
        String orderId = jedisClient.incr(ORDER_ID).toString();
        orderInfo.setOrderId(orderId);
        //状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
        orderInfo.setStatus(1);
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(orderInfo.getCreateTime());
        //4.将订单详情插入到订单表中
        orderMapper.insert(orderInfo);
        //5.补全订单明细表
        List<TbOrderItem> orderItemList = orderInfo.getOrderItems();
        for (TbOrderItem orderItem : orderItemList) {
            String orderDetailId = jedisClient.incr(ORDER_DETAIL_ID).toString();
            orderItem.setId(orderDetailId);
            orderItem.setOrderId(orderId);
            //6.将订单明细表放入到订单详情中
            orderItemMapper.insert(orderItem);
        }
        //7.补全物流表
        TbOrderShipping orderShipping = orderInfo.getOrderShipping();
        orderShipping.setOrderId(orderId);
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(orderShipping.getCreated());
        //8.将物流信息放入到订单详情中
        orderShippingMapper.insert(orderShipping);
        //9.返回-将订单信息返回
        return E3Result.ok(orderId);
    }

}
