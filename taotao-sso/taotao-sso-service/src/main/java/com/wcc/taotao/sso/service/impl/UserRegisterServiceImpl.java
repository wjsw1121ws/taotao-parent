package com.wcc.taotao.sso.service.impl;

import com.wcc.taotao.mapper.TbUserMapper;
import com.wcc.taotao.pojo.TbUser;
import com.wcc.taotao.pojo.TbUserExample;
import com.wcc.taotao.sso.service.UserRegisterService;
import com.wcc.taotao.utils.E3Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import java.util.Date;
import java.util.List;

/**
 * @Description: 单点登录service实现
 * @ClassName: UserRegisterServiceImpl
 * @Auther: changchun_wu
 * @Date: 2019/3/1 23:55
 * @Version: 1.0
 **/

@Service
public class UserRegisterServiceImpl implements UserRegisterService {
    @Autowired
    private TbUserMapper userMapper;
    /**
     * 用户注册数据校验
     * @param param 校验的参数
     * @param type 校验的数据类型
     * @return
     */
    @Override
    public E3Result checkData(String param, Integer type) {
        //1.注入服务
        //2.注入mapper
        //3.创建example
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        //4.判断注册的数据类型
        if (type==1){
            //注册使用的是username
            if (StringUtils.isEmpty(param)){
                return E3Result.ok(false);
            }
            criteria.andUsernameEqualTo(param);
        }else if (type==2){
            //注册使用的是phone
            criteria.andPhoneEqualTo(param);
        }else  if (type==3){
            //注册使用的是email
            criteria.andEmailEqualTo(param);
        }else {
            //参数错误
            E3Result.build(400,"参数错误");
        }
        //5.执行查询
        List<TbUser> tbUsers = userMapper.selectByExample(example);
        if (tbUsers!=null&&tbUsers.size()>0){
            //数据已经注册过了
            return E3Result.ok(false);
        }
        //6.返回数据
        return E3Result.ok(true);
    }

    /**
     * 用户注册
     * @param tbUser 注册的用户系信息
     * @return
     */
    @Override
    public E3Result register(TbUser tbUser) {
        /*
        参数说明
        username 不能为空   并且 唯一
        password 不能为空  可以重复  （加密存储）
        phone    可以为空  不能重复 （如果不为空，就不能重复）
        email    可以为空   不能重复 （如果不为空，就不能重复）
         */
        //1.注入mapper
        //2.数据合理性校验
        if (StringUtils.isEmpty(tbUser.getUsername())){
            //用户名为空
            return E3Result.build(400,"注册失败. 请校验数据后请再提交数据.");
        }
        E3Result e3Result = checkData(tbUser.getUsername(), 1);
        if (!(Boolean) e3Result.getData()) {
            //用户存在
            return E3Result.build(400, "注册失败. 请校验数据后请再提交数据.");
        }
        if (StringUtils.isEmpty(tbUser.getPassword())){
            //密码为空
            return E3Result.build(400,"注册失败. 请校验数据后请再提交数据.");
        }
        E3Result e3Result2 = checkData(tbUser.getPhone(), 2);
        if (StringUtils.isNotBlank(tbUser.getPhone())){
            //phone不为空
            if (!(boolean)e3Result2.getData()){
                //phone注册过
                return E3Result.build(400,"注册失败. 请校验数据后请再提交数据.");
            }
        }
        if (StringUtils.isNotBlank(tbUser.getEmail())){
            //email不为空
            E3Result e3Result3 = checkData(tbUser.getEmail(), 3);
            //email注册过
            if (!(boolean)e3Result3.getData())
                return E3Result.build(400,"注册失败. 请校验数据后请再提交数据.");
            }
        //3.填充其他数据
        tbUser.setCreated(new Date());
        tbUser.setUpdated(tbUser.getCreated());
        //4.密码使用MD5处理
        String md5Password = DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes());
        tbUser.setPassword(md5Password);
        //5.将数据保存到数据库
        userMapper.insertSelective(tbUser);
        //6.返回
        return E3Result.build(200,"ok");

    }
}
