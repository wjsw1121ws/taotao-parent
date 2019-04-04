package com.wcc.taotao.search.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 全局异常处理
 * @ClassName: GlobalExceptionHandler
 * @Auther: changchun_wu
 * @Date: 2019/2/13 22:01
 * @Version: 1.0
 **/
public class GlobalExceptionHandler implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object obj, Exception exception) {
        //1.将异常信息写入到日志
        System.out.println(exception.getMessage());
        exception.printStackTrace();
        //2.通知开发人员
        System.out.println("出异常了");
        //3.返回错误视图
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error/exception");
        mav.addObject("message","您的网络短线了,请重试!");
        return mav;
    }
}
