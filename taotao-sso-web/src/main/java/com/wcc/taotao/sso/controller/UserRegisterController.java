package com.wcc.taotao.sso.controller;

import com.wcc.taotao.pojo.TbUser;
import com.wcc.taotao.sso.service.UserRegisterService;
import com.wcc.taotao.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 单点登录Controller层
 * @ClassName: UserRegisterController
 * @Auther: changchun_wu
 * @Date: 2019/3/2 0:07
 * @Version: 1.0
 **/

@Controller
public class UserRegisterController {

    @Autowired
    private UserRegisterService userRegisterService;

    /**
     * 用户注册数据校验
     * @param param 校验的参数
     * @param type 校验的参数类型
     * @return
     */
    @RequestMapping(value = "/user/check/{param}/{type}", method = RequestMethod.GET)
    @ResponseBody
    public E3Result checkData(@PathVariable String param, @PathVariable Integer type) {
        //1.引入服务
        //2.注入服务
        //3.调用服务中的方法
        return userRegisterService.checkData(param, type);
    }
    /**
     * 请求方法	POST
     * URL	http://sso.taotao.com/user/register
     */
    /**
     * 用户注册
     * @param tbUser 注册的用户
     * @return
     */
    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    @ResponseBody
    public E3Result register(TbUser tbUser){
        return userRegisterService.register(tbUser);
    }
}
