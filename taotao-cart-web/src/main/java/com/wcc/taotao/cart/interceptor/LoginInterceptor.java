package com.wcc.taotao.cart.interceptor;

import com.wcc.taotao.cart.service.CartService;
import com.wcc.taotao.pojo.TbItem;
import com.wcc.taotao.pojo.TbUser;
import com.wcc.taotao.sso.service.UserLoginService;
import com.wcc.taotao.utils.CookieUtils;
import com.wcc.taotao.utils.E3Result;
import com.wcc.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Description: 用户登陆拦截器
 * @ClassName: LoginInterceptor
 * @Auther: changchun_wu
 * @Date: 2019/3/7 1:31
 * @Version: 1.0
 **/
public class LoginInterceptor implements HandlerInterceptor {

    @Value("${COOKIE_NAME}")
    private String COOKIE_NAME;
    @Value("${CART_COOKIE_NAME}")
    private String CART_COOKIE_NAME;
    @Autowired
    private UserLoginService loginService;
    @Autowired
    private CartService cartService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //1.从cookie中取用户登陆token
        String cookieValue = CookieUtils.getCookieValue(request, COOKIE_NAME);
        //2.如果token不存在-用户未登录
        if (StringUtils.isEmpty(cookieValue)) {
            return true;
        }
        //3.如果token存在-从token中取用户信息
        E3Result e3Result = loginService.getUserByToken(cookieValue);
        //4.如果没有取到用户信息-登陆过期
        if (e3Result.getStatus() != 200) {
            return true;
        }
        //5.取到用户信息-登陆状态
        TbUser user = (TbUser) e3Result.getData();
        //6.将cookie中的订单信息放入redis中
        String cookieCart = CookieUtils.getCookieValue(request, CART_COOKIE_NAME, true);
        if (StringUtils.isNotBlank(cookieCart)) {
            List<TbItem> tbItems = JsonUtils.jsonToList(cookieCart, TbItem.class);
            for (TbItem tbItem : tbItems) {
                cartService.addCartItem(user.getId(), tbItem.getId(), tbItem.getNum());
            }
            //7.删除cookie中的订单信息
            CookieUtils.deleteCookie(request, response, CART_COOKIE_NAME);
            //8.将用户信息放到request域中
            request.setAttribute("user", user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
