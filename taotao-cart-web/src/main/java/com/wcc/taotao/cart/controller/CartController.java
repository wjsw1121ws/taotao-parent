package com.wcc.taotao.cart.controller;

import com.wcc.taotao.cart.service.CartService;
import com.wcc.taotao.pojo.TbItem;
import com.wcc.taotao.pojo.TbUser;
import com.wcc.taotao.service.ItemService;
import com.wcc.taotao.sso.service.UserLoginService;
import com.wcc.taotao.utils.CookieUtils;
import com.wcc.taotao.utils.E3Result;
import com.wcc.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 购物车Controller
 * @ClassName: CartController
 * @Auther: changchun_wu
 * @Date: 2019/3/6 2:16
 * @Version: 1.0
 **/

@Controller
public class CartController {

    @Value("${CART_COOKIE_NAME}")

    private String CART_COOKIE_NAME;
    @Value("${CART_COOKIE_EXPIRE}")

    private int CART_COOKIE_EXPIRE;
    @Value("${COOKIE_NAME}")

    private String COOKIE_NAME;

    @Autowired
    private ItemService itemService;
    @Autowired

    private CartService cartService;
    @Autowired

    private UserLoginService loginService;
    /**
     * 添加商品到购物车
     * @param itemId 商品ID
     * @param num 商品数目
     * @param request
     * @param response
     */
    @RequestMapping(value = "/cart/add/{itemId}",method = RequestMethod.GET)
    public String addItemCart(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num,
                              HttpServletRequest request, HttpServletResponse response){
        //1.引入服务
        //2.注入服务
        //判断用户是否登陆
        TbUser user = (TbUser) request.getAttribute("user");
        //如果登陆,将购物车放到redis中持久化
        if (user!=null){
            cartService.addCartItem(user.getId(),itemId,num);
            return "cartSuccess";
        }else {
            //如果未登录,将购物车放入cookie中

            //3.判断商品在cookie中是否存在
            List<TbItem> itemList = getItemListByCookie(request);
            boolean flag = true;
            for (TbItem tbItem : itemList) {
                if (tbItem.getId() == itemId.longValue()) {
                    //4.存在,从cookie中取出商品信息,并且商品的数量相加
                    //表示商品存在
                    //更新商品的数量
                    tbItem.setNum(tbItem.getNum() + num);
                    flag = false;
                    break;
                }
            }
            //5.不存在,通过商品id查询到商品,设置商品数量,将商品添加到cookie中
            if (flag == true) {
                TbItem item = itemService.getItemById(itemId);
                item.setNum(num);
                //取商品图片
                if (StringUtils.isNotBlank(item.getImage())) {
                    item.setImage(item.getImage().split(",")[0]);
                }
                //将商品添加到购物车
                itemList.add(item);
            }
            //将购物车添加到cookie
            CookieUtils.setCookie(request, response, CART_COOKIE_NAME, JsonUtils.objectToJson(itemList), CART_COOKIE_EXPIRE, true);
        }
        //6.返回逻辑视图
        return "cartSuccess";
    }

    /**
     * 获取购车中的商品
     * @param request
     * @return
     */
    private List<TbItem> getItemListByCookie(HttpServletRequest request){
        //获取cookie中的商品信息
        String cookieValue = CookieUtils.getCookieValue(request, CART_COOKIE_NAME,true);
        if (StringUtils.isEmpty(cookieValue)){
            //cookie没有取到-该商品未加入购物车
            return new ArrayList<>();
        }
        //cookie中有该商品的信息
        //从cookie取出商品
        List<TbItem> list = JsonUtils.jsonToList(cookieValue, TbItem.class);
        return list;
    }

    /**
     * 将cookie中的商品信息传输到页面上
     * @param request
     * @return
     */
    @RequestMapping(value = "/cart/cart")
    public String getCartItem(HttpServletRequest request,HttpServletResponse response){
        //如果未登录从cookie中取购物车列表,如果登陆,将cookie中的购物车放到redis中,并清除cookie中的购物车
        //如果登陆了,将cookie中的购物车列表存到redis然后清除cookie中的购物车
        //1.判断用户是否登陆
        String cookieValue = CookieUtils.getCookieValue(request, COOKIE_NAME);
        if (StringUtils.isNotBlank(cookieValue)) {
            E3Result token = loginService.getUserByToken(cookieValue);
            //用户登陆了
            if (token.getStatus() == 200) {
                //2.调用service中的方法获取购物车
                TbUser user = (TbUser) token.getData();
                //从cookie中取得购物车
                List<TbItem> cartList = cartService.getCartItem(user.getId());
                request.setAttribute("cartList", cartList);
            }
        }else {
            //用户未登录
            //从cookie中获取购物车
            List<TbItem> cartList = getItemListByCookie(request);
            //将参数传递给页面
            request.setAttribute("cartList", cartList);
        }
        //返回
        return "cart";
    }

    /**
     * @Description: 更新购物车中的商品数目
     * @Param: itemId
     * @Param: num
     * @Return: com.wcc.taotao.utils.E3Result
     **/
    @RequestMapping(value = "/cart/update/num/{itemId}/{num}",method = RequestMethod.POST)
    @ResponseBody
    public E3Result updateItemNum(@PathVariable Long itemId,@PathVariable Integer num,
                                  HttpServletRequest request,HttpServletResponse response){
        //1.判断用户是否登陆
        String cookieValue = CookieUtils.getCookieValue(request, COOKIE_NAME);
        if (StringUtils.isNotBlank(cookieValue)) {
            E3Result token = loginService.getUserByToken(cookieValue);
            if (token.getStatus() == 200) {
                //用户登陆
                //2.调用service方法更新商品
                TbUser user = (TbUser) token.getData();
                cartService.updateItemNum(user.getId(),itemId, num);
            }
        }else {
            //1.从cookie中获取商品信息
            List<TbItem> itemList = getItemListByCookie(request);
            //2.遍历商品列表,获取对应的商品
            for (TbItem tbItem : itemList) {
                //找到这个商品
                if (tbItem.getId() == itemId.longValue()) {
                    //3.更新商品的数目
                    tbItem.setNum(num);
                    break;
                }
            }
            //4.将商品信息写入到cookie
            CookieUtils.setCookie(request, response, CART_COOKIE_NAME, JsonUtils.objectToJson(itemList), CART_COOKIE_EXPIRE, true);
        }
        //5.返回
        return E3Result.ok();
    }

    /**
     * 删除购物车中的商品
     * @param itemId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/cart/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId,
                                 HttpServletRequest request,HttpServletResponse response){
        //1.从token中获取用户信息
        String cookieValue = CookieUtils.getCookieValue(request, COOKIE_NAME);
        if (StringUtils.isNotBlank(cookieValue)) {
            E3Result token = loginService.getUserByToken(cookieValue);
            //判断用户是否登陆
            if (token.getStatus() == 200) {
                //登陆,则调用service中的方法删除
                TbUser user = (TbUser) token.getData();
                if (user != null) {
                    cartService.deleteCartItem(user.getId(), itemId);
                }
            }
        }else {
            //1.从cookie中获取商品信息
            List<TbItem> itemList = getItemListByCookie(request);
            //2.遍历购物车获取对应的商品
            for (TbItem tbItem : itemList) {
                //找到商品
                if (tbItem.getId().longValue() == itemId) {
                    //3.从购物车中删除该商品
                    itemList.remove(tbItem);
                    break;
                }
            }
            //4.将商品添加到cookie
            CookieUtils.setCookie(request, response, CART_COOKIE_NAME, JsonUtils.objectToJson(itemList), CART_COOKIE_EXPIRE, true);
        }
        //5.重定向到当前页
        return "redirect:/cart/cart.html";
    }
}
