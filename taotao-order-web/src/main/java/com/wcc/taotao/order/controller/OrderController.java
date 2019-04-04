package com.wcc.taotao.order.controller;

import com.wcc.taotao.cart.service.CartService;
import com.wcc.taotao.order.pojo.OrderInfo;
import com.wcc.taotao.order.service.OrderService;
import com.wcc.taotao.pojo.TbItem;
import com.wcc.taotao.pojo.TbOrderItem;
import com.wcc.taotao.pojo.TbUser;
import com.wcc.taotao.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description: 订单Controller
 * @ClassName: OrderController
 * @Auther: changchun_wu
 * @Date: 2019/3/10 0:06
 * @Version: 1.0
 **/

@Controller
public class OrderController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;
    /**
     * 获取订单
     * @return 返回逻辑视图订单页面
     */
    @RequestMapping(value = "/order/order-cart")
    public String getOrder(HttpServletRequest request){
        //1.注入服务
        //2.引入服务
        //3.从request作用域中获取用户信息
        TbUser user = (TbUser) request.getAttribute("user");
        //4.调用服务获取用户的订单信息
        List<TbItem> cartItem = cartService.getCartItem(user.getId());
        //5.将订单放到request作用域
        request.setAttribute("cartList",cartItem);
        //5.返回逻辑视图
        return "order-cart";
    }

    @RequestMapping(value = "/order/create",method = RequestMethod.POST)
    public String createOrder(OrderInfo orderInfo, HttpServletRequest request){
        //1.引入服务
        //2.从作用域中取出用户信息
        TbUser user = (TbUser) request.getAttribute("user");
        //3.补全OrderInfo属性
        orderInfo.setUserId(user.getId());
        orderInfo.setBuyerNick(user.getUsername());
        //4.调用service中的方法
        E3Result e3Result = orderService.createOrder(orderInfo);
        //5.从购物车中删除提交订单中的商品
        //获取订单中的商品id
        List<TbOrderItem> cartList = orderInfo.getOrderItems();
        for (TbOrderItem tbOrderItem : cartList) {
            long itemId = Long.parseLong(tbOrderItem.getItemId());
            cartService.deleteCartItem(user.getId(),itemId);
        }
        //6.将数据放到作用域中
        request.setAttribute("orderId",e3Result.getData());
        request.setAttribute("payment",orderInfo.getPayment());

        //7.返回成功页面
        return "success";
    }
}
