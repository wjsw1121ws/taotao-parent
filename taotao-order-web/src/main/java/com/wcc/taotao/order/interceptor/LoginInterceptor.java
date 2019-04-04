package com.wcc.taotao.order.interceptor;

import com.wcc.taotao.pojo.TbUser;
import com.wcc.taotao.sso.service.UserLoginService;
import com.wcc.taotao.utils.CookieUtils;
import com.wcc.taotao.utils.E3Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 订单系统用户登陆拦截器
 * @ClassName: LoginInterceptor
 * @Auther: changchun_wu
 * @Date: 2019/3/10 1:12
 * @Version: 1.0
 **/
public class LoginInterceptor implements HandlerInterceptor {
    @Value("${COOKIE_NAME}")
    private String COOKIE_NAME;
    @Autowired
    private UserLoginService userLoginService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //1.判断用户是否登陆
        //从cookie中获取token
        String cookieValue = CookieUtils.getCookieValue(request, COOKIE_NAME,true);
        if (StringUtils.isNotBlank(cookieValue)) {
            //从token中获取用户信息
            E3Result token = userLoginService.getUserByToken(cookieValue);
            if (token.getStatus()!=200){
                //登陆过期
                response.sendRedirect(" http://localhost:8088/page/login?redirect="+"http://localhost:8092"+request.getRequestURI());
                return false;
            }
            TbUser user = (TbUser) token.getData();
            if (user != null) {
                //2.用户登陆了
                //将用户信息放入到作用域
                request.setAttribute("user", user);
                //放行
                return true;
            }
        }
        //3.用户未登录,则进入到登陆界面,登陆成功后跳重定向到当前页
        response.sendRedirect("http://localhost:8088/page/login?redirect="+"http://localhost:8092"+request.getRequestURI());
        System.out.println(request.getRequestURI());
        //调用sso系统跳转到登陆界面
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
