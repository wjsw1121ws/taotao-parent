package com.wcc.taotao.order.pojo;

import com.wcc.taotao.pojo.TbOrder;
import com.wcc.taotao.pojo.TbOrderItem;
import com.wcc.taotao.pojo.TbOrderShipping;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 订单详情pojo
 * @ClassName: OrderInfo
 * @Auther: changchun_wu
 * @Date: 2019/3/10 15:13
 * @Version: 1.0
 **/
public class OrderInfo extends TbOrder implements Serializable {
    private List<TbOrderItem> orderItems;
    private TbOrderShipping orderShipping;

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }
}
