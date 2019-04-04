package com.wcc.taotao.sso.controller;

import com.wcc.taotao.sso.service.UserLoginService;
import com.wcc.taotao.utils.CookieUtils;
import com.wcc.taotao.utils.E3Result;
import com.wcc.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 用户controller
 * @ClassName: UserLoginController
 * @Auther: changchun_wu
 * @Date: 2019/3/4 3:03
 * @Version: 1.0
 **/
@Controller
public class UserLoginController {
    @Autowired
    private UserLoginService userLoginService;

    @Value("${COOKIE_NAME}")
    private String COOKIE_NAME;

    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @ResponseBody
    public E3Result userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response){
        //1.引入服务
        //2.注入服务
        //3.设置token到session
        E3Result e3Result = userLoginService.userLogin(username, password);
        if (e3Result.getStatus()==200) {
            CookieUtils.setCookie(request, response, COOKIE_NAME, e3Result.getData().toString());
        }
        return e3Result;
    }

    @RequestMapping(value = "/user/token/{token}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getUserByToken(@PathVariable String token,String callback){
        //1.引入服务
        //2.注入服务
        //3.调用service方法
        //这里有个坑
        String token1 = token.substring(1, token.length() - 1);
        E3Result userByToken = userLoginService.getUserByToken(token1);
        if (StringUtils.isEmpty(callback)){
            //没有callback,那么就是发送了json请求
            return JsonUtils.objectToJson(userByToken);
        }
        //有callback数据
        return callback+"("+JsonUtils.objectToJson(userByToken)+");";
    }

}
