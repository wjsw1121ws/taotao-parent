package com.wcc.taotao.sso.service.impl;

import com.wcc.taotao.mapper.TbUserMapper;
import com.wcc.taotao.pojo.TbUser;
import com.wcc.taotao.pojo.TbUserExample;
import com.wcc.taotao.sso.jedis.JedisClient;
import com.wcc.taotao.sso.service.UserLoginService;
import com.wcc.taotao.utils.E3Result;
import com.wcc.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;

/**
 * @Description: 用户service层
 * @ClassName: UserLoginServiceImpl
 * @Auther: changchun_wu
 * @Date: 2019/3/4 2:33
 * @Version: 1.0
 **/

@Service
public class UserLoginServiceImpl implements UserLoginService {
    @Autowired
    private TbUserMapper userMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${TOKEN_INFO}")
    private String TOKEN_INFO;

    @Value("${EXPIRE_TOKEN}")
    private Integer EXPIRE_TOKEN;

    /**
     * 用户
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @Override
    public E3Result userLogin(String username, String password) {
        //1.注入mapper
        //2.注入服务
        //3.判断用户名密码是否为空
        if (StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
            //用户名和密码为空
            /**
             * 失败返回值
             * status:400
             * msg: "错误信息"
             * data: null
             */
            return E3Result.build(400,"请输入用户名和密码");
        }
        //4.判断用户是否存在
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> tbUsers = userMapper.selectByExample(example);
        if (tbUsers==null&&tbUsers.size()==0){
            //用户名不存在
            return E3Result.build(400,"用户名或密码错误");
        }
        TbUser user = tbUsers.get(0);
        //5.判断密码是否正确
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!md5Password.equals(user.getPassword())){
            //密码错误
            return E3Result.build(400,"用户名或密码错误");
        }
        //6.将用户信息存入到redis缓存中
        String token = UUID.randomUUID().toString();
        //设置密码为空
        user.setPassword(null);
        jedisClient.set(TOKEN_INFO+":"+token,JsonUtils.objectToJson(user));
        //7.设置token的过期时间
        jedisClient.expire(TOKEN_INFO+":"+token,EXPIRE_TOKEN);
        //8.返回
        /**
         * 成功的返回值
         * status: 200
         * msg: "OK"
         * data: "fe5cb546aeb3ce1bf37abcb08a40493e"//登录成功，返回token
         */
        return E3Result.ok(TOKEN_INFO+":"+token);
    }

    /**
     * 根据token查询用户信息
     * @param token 用户的token
     * @return
     */
    @Override
        public E3Result getUserByToken(String token) {
        //1.注入mapper
        //2.注入服务
        //3.根据token查询用户信息
        String tokenJson = jedisClient.get(token);
        TbUser user = JsonUtils.jsonToPojo(tokenJson, TbUser.class);
        if (user==null){
            return E3Result.build(400,"用户已过期");
        }
        //4.设置过期时间
        jedisClient.expire(token,EXPIRE_TOKEN);
        //5.返回用户信息
        return E3Result.ok(user);
    }

}
