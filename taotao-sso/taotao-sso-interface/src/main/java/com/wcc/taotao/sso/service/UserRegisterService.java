package com.wcc.taotao.sso.service;

import com.wcc.taotao.pojo.TbUser;
import com.wcc.taotao.utils.E3Result;

/**
 * @Description: 单点登录系统接口
 * @ClassName: UserRegisterService
 * @Auther: changchun_wu
 * @Date: 2019/3/1 23:54
 * @Version: 1.0
 **/
public interface UserRegisterService {
    /**
     * 用户注册校验
     * @param param 校验的参数
     * @param type 校验的数据类型
     * @return
     */
    E3Result checkData(String param,Integer type);

    /**
     * 用户注册
     * @param tbUser 注册的用户系信息
     * @return
     */
    E3Result register(TbUser tbUser);
}
