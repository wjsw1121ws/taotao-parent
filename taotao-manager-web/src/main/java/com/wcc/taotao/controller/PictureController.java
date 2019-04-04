package com.wcc.taotao.controller;

import com.wcc.taotao.utils.FastDFSClient;
import com.wcc.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 图片上传Controller
 * @ClassName: PictureController
 * @Auther: changchun_wu
 * @Date: 2018/12/27 22:19
 * @Version: 1.0
 **/

@Controller
public class PictureController {
    /**
     * @Author: changchun_wu
     * @Date: 2018/12/27 22:19
     * @Description: 实现图片上传
     * 请求路径: /pic/upload
     * 请求参数: uploadFile
     * 返回值类型Map
     * 返回格式(JSON)
     * //成功时
     * {
     *         "error" : 0,
     *         "url" : "http://www.example.com/path/to/file.ext"
     * }
     * //失败时
     * {
     *         "error" : 1,
     *         "message" : "错误信息"
     * }
     **/
    // 取出配置文件内容
    @Value("${IMAGE_SERVER_LOCATION}")
    private String IMAGE_SERVER_LOCATION;

    @RequestMapping(value = "/pic/upload",
            produces = MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")//设置浏览器显示的数据类型
    @ResponseBody
    public String uploadFile(MultipartFile uploadFile){
        try {
            // 上传文件到服务器
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/fast_dfs.conf");
            // 得到图片的地址和文件名
            byte[] fileContent = uploadFile.getBytes();
            String originalFilename = uploadFile.getOriginalFilename();
            int i = originalFilename.lastIndexOf(".")+1;
            String extName = originalFilename.substring(i);
            String url = fastDFSClient.uploadFile(fileContent, extName);
            // 补充为完整的url
            url = IMAGE_SERVER_LOCATION + url;
            // 封装到map中返回
            Map map = new HashMap<>();
            map.put("error",0);
            map.put("url",url);
            String json = JsonUtils.objectToJson(map);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            Map map = new HashMap();
            map.put("error",1);
            map.put("message","上传失败");
            String json = JsonUtils.objectToJson(map);
            return json;
        }
    }
}
