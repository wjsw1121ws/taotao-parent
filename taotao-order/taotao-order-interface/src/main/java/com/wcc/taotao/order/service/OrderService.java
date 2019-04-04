package com.wcc.taotao.order.service;

import com.wcc.taotao.order.pojo.OrderInfo;
import com.wcc.taotao.utils.E3Result;

/**
 * @Description: 订单接口
 * @ClassName: OrderService
 * @Auther: changchun_wu
 * @Date: 2019/3/10 15:45
 * @Version: 1.0
 **/
public interface OrderService {
    /**
     * 创建订单
     * @param orderInfo 订单详情
     * @return
     */
    E3Result createOrder(OrderInfo orderInfo);
}
