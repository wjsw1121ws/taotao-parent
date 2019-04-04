package com.wcc.taotao.sso.service;

import com.wcc.taotao.utils.E3Result;

/**
 * @Description: 用户接口
 * @ClassName: UserLoginService
 * @Auther: changchun_wu
 * @Date: 2019/3/4 2:31
 * @Version: 1.0
 **/
public interface UserLoginService {
    /**
     * 用户
     * @param username 用户名
     * @param password 密码
     * @return
     */
    E3Result userLogin(String username,String password);

    /**
     * 根据token查询用户信息
     * @param token 用户的token
     * @return
     */
    E3Result getUserByToken(String token);
}
