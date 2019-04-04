package com.wcc.taotao.sso.service;

import com.wcc.taotao.utils.E3Result;

/**
 * @Description: 用户安全退出接口
 * @ClassName: UserLogoutService
 * @Auther: changchun_wu
 * @Date: 2019/3/4 12:05
 * @Version: 1.0
 **/

public interface UserLogoutService {
    /**
     * 用户退出
     * @param token 退出的用户token
     * @return
     */
    E3Result userLogout(String token);
}
