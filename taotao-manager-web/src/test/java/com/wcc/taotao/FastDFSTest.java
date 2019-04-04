package com.wcc.taotao;

import com.wcc.taotao.utils.FastDFSClient;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;

import java.io.IOException;

/**
 * @Description: 图片服务器测试
 * @ClassName: FastDFSTest
 * @Auther: changchun_wu
 * @Date: 2018/12/27 0:47
 * @Version: 1.0
 **/
public class FastDFSTest {
    /**
     * @Author: changchun_wu
     * @Date: 2018/12/27 1:27
     * @Description: 文件上传测试
     **/
    @Test
    public void testFastDFS() throws IOException, MyException {
        // 1.创建一个配置文件,文件名任意,内容就是tracker服务器的地址
        // fast_dfs.conf
        // 2.使用全局对象加载配置文件
        ClientGlobal.init("D:\\WEB Project\\taotao-parent\\taotao-manager-web\\src\\main\\resources\\conf\\fast_dfs.conf");
        // 3.创建一个TrackClient对象
        TrackerClient trackerClient = new TrackerClient();
        // 4.通过TrackerClient获得一个TrackerServer对象
        TrackerServer trackerServer = trackerClient.getConnection();
        // 5.创建一个StorageServer的引用,可以是null
        StorageServer storageServer = null;
        // 6.创建一个StorageClient,参数需要TrackerServer和StorageServer
        StorageClient storageClient = new StorageClient(trackerServer,storageServer);
        // 7.使用StorageClient上传文件
        String[] arry = storageClient.upload_file("C:\\Users\\changchun_wu\\Pictures\\images\\4.jpg", "jpg", null);
        for (String s : arry) {
            System.out.println(s);
        }
    }

    /**
     * @Author: changchun_wu
     * @Date: 2018/12/27 1:27
     * @Description: 文件上传工具类测试
     **/
    @Test
    public void testFastDFSClient() throws Exception {
        FastDFSClient fastDFSClient = new FastDFSClient("F:\\IDEA\\taotao-parent\\taotao-manager-web\\src\\main\\resources\\conf\\fast_dfs.conf");
        String file = fastDFSClient.uploadFile("C:\\Users\\changchun_wu\\Pictures\\images\\5.jpg");
        System.out.println(file);
    }
}
