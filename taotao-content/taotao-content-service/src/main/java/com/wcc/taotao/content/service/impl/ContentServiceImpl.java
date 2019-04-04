package com.wcc.taotao.content.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wcc.taotao.content.jedis.JedisClient;
import com.wcc.taotao.content.service.ContentService;
import com.wcc.taotao.mapper.TbContentMapper;
import com.wcc.taotao.pojo.EasyUIDataGridResult;
import com.wcc.taotao.pojo.TbContent;
import com.wcc.taotao.pojo.TbContentExample;
import com.wcc.taotao.utils.E3Result;
import com.wcc.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import java.util.Date;
import java.util.List;

/**
 * @Description: 内容管理Service实现
 * @ClassName: ContentServiceImpl
 * @Auther: changchun_wu
 * @Date: 2019/1/7 22:44
 * @Version: 1.0
 **/
@Controller
public class ContentServiceImpl implements ContentService {
    @Autowired
    private TbContentMapper tbContentMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${CONTEXT_KEY}")
    private String CONTEXT_KEY;

    /**
     * @Author: changchun_wu
     * @Date: 2019/1/7 23:00
     * @Description: 分页查询内容列表service实现
     **/
    @Override
    public EasyUIDataGridResult<TbContent> getContentList(Long categoryId, Integer page, Integer rows) {
        //1.创建分页对象
        PageHelper.startPage(page,rows);
        //2.执行查询
        TbContentExample example = new TbContentExample();
        example.createCriteria().andCategoryIdEqualTo(categoryId);
        List<TbContent> tbContents = tbContentMapper.selectByExample(example);
        //3.创建pageInfo对象
        PageInfo<TbContent> pageInfo = new PageInfo<>(tbContents);
        //4.将结果集放入EasyUIDataGridResult对象中
        EasyUIDataGridResult result = new EasyUIDataGridResult<>();
        result.setRows(pageInfo.getList());
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    /**
     * @Author: changchun_wu
     * @Date: 2019/1/7 23:37
     * @Description: 添加内容
     **/
    @Override
    public E3Result addContent(TbContent tbContent) {
        tbContent.setCreated(new Date());
        tbContent.setUpdated(tbContent.getCreated());
        tbContentMapper.insert(tbContent);
        //添加内容时添加缓存
        try {
            jedisClient.hdel(CONTEXT_KEY,tbContent.getCategoryId()+"");
            System.out.println("删除缓存");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return E3Result.ok();
    }

    /**
     * @Author: changchun_wu
     * @Date: 2019/1/8 23:33
     * @Description: 首页大广告位service实现
     **/
    @Override
    public List<TbContent> getContentByCategoryId(Long categoryId) {
        //或者redis缓存中的json
        //添加缓存不能影响正常的业务逻辑,有异常需要获取
        try {
            String context_key = jedisClient.hget(CONTEXT_KEY, categoryId + "");
            //如果存在缓存
            if (StringUtils.isNotBlank(context_key)){
                return JsonUtils.jsonToList(context_key,TbContent.class);
            }
            System.out.println("有redis缓存");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //1.创建example对象
        TbContentExample example = new TbContentExample();
        example.createCriteria().andCategoryIdEqualTo(categoryId);
        //2.执行查询
        List<TbContent> tbContents = tbContentMapper.selectByExample(example);
        //3.返回数据

        //操作jedis,添加缓存不能影响正常的业务逻辑
        try {
            jedisClient.hset(CONTEXT_KEY,categoryId+"",JsonUtils.objectToJson(tbContents));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("没有redis缓存");
        return tbContents;
    }
}
