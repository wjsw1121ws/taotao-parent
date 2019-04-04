package com.wcc.taotao.sso.controller;

import com.wcc.taotao.sso.service.UserLogoutService;
import com.wcc.taotao.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 用户安全退出controller层
 * @ClassName: UserLogoutController
 * @Auther: changchun_wu
 * @Date: 2019/3/4 12:21
 * @Version: 1.0
 **/

@Controller
public class UserLogoutController {
    @Autowired
    private UserLogoutService userLogoutService;

    @RequestMapping(value = "/user/logout/{token}",method = RequestMethod.GET)
    @ResponseBody
    public E3Result userLogout(@PathVariable String token){
        //1.注入服务
        //2.引入服务
        //3.调用service方法
        E3Result e3Result = userLogoutService.userLogout(token);
        return e3Result;
    }
}
