package com.wcc.taotao.test;

import com.wcc.taotao.content.jedis.JedisClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description: 测试
 * @ClassName: TestJedis2
 * @Auther: changchun_wu
 * @Date: 2019/1/21 21:20
 * @Version: 1.0
 **/
public class TestJedisClient {
    @Test
    public void testJedisPool(){
        //初始化spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
        JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
        jedisClient.set("eee","555");
        System.out.println(jedisClient.get("eee"));
    }
}
