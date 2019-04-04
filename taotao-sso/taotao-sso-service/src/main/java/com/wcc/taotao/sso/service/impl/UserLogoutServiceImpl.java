package com.wcc.taotao.sso.service.impl;

import com.wcc.taotao.sso.jedis.JedisClient;
import com.wcc.taotao.sso.service.UserLogoutService;
import com.wcc.taotao.utils.E3Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Description: 用户安全退出service
 * @ClassName: UserLogoutServiceImpl
 * @Auther: changchun_wu
 * @Date: 2019/3/4 12:07
 * @Version: 1.0
 **/

@Service
public class UserLogoutServiceImpl implements UserLogoutService {
    @Autowired
    private JedisClient jedisClient;

    @Value("${TOKEN_INFO}")
    private String TOKEN_INFO;

    @Value("${CLEAN_EXPIRE}")
    private int CLEAN_EXPIRE;

    /**
     * 用户安全退出
     * @param token 退出的用户token
     * @return
     */
    @Override
    public E3Result userLogout(String token) {
        //1.注入服务
        //2.注入jedisClient
        String tokenJson = jedisClient.get(TOKEN_INFO + ":" + token);
        //3.查询token是否在过期时间
        if (StringUtils.isNotBlank(tokenJson)){
            //token没过期
            //4.设置token的有效性
            jedisClient.expire(TOKEN_INFO + ":" + token,CLEAN_EXPIRE);
        }
        return E3Result.ok();
    }
}
