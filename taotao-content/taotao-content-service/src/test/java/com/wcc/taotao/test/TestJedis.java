package com.wcc.taotao.test;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description:
 * @ClassName: TestJedis
 * @Auther: changchun_wu
 * @Date: 2019/1/20 15:30
 * @Version: 1.0
 **/
public class TestJedis {
    /**
     * @Author: changchun_wu
     * @Date: 2019/1/20 15:38
     * @Description: 测试redis单机版
     **/
    @Test
    public void testJdeis(){
        //创建jedis对象
        Jedis jedis = new Jedis("192.168.25.133",6379);
        //操作jedis
        jedis.set("aaa","111");
        System.out.println(jedis.get("aaa"));
        //关闭jedis
        jedis.close();
    }

    /**
     * @Author: changchun_wu
     * @Date: 2019/1/20 16:01
     * @Description: 测试连接池
     **/
    @Test
    public void testJedisPool(){
        //获取jedis连接池对象
        JedisPool pool = new JedisPool("192.168.25.133",6379);
        //获取jedis对象
        Jedis jedis = pool.getResource();
        //操作jedis
        jedis.set("bbb","222");
        System.out.println(jedis.get("bbb"));
        //关闭jedis
        jedis.close();
        //关闭连接池
        pool.close();
    }

    /**
     * @Author: changchun_wu
     * @Date: 2019/1/20 16:38
     * @Description: 测试集群版
     **/
    @Test
    public void testJedisCluster() throws IOException {
        Set set = new HashSet();
        set.add(new HostAndPort("192.168.25.133",7000));
        set.add(new HostAndPort("192.168.25.133",7001));
        set.add(new HostAndPort("192.168.25.133",7002));
        set.add(new HostAndPort("192.168.25.133",7003));
        set.add(new HostAndPort("192.168.25.133",7004));
        set.add(new HostAndPort("192.168.25.133",7005));
        JedisCluster cluster = new JedisCluster(set);
        cluster.set("ccc","333");
        System.out.println(cluster.get("ccc"));
        cluster.close();
    }
}
