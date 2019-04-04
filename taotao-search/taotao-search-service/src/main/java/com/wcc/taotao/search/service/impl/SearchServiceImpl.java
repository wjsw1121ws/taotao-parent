package com.wcc.taotao.search.service.impl;

import com.wcc.taotao.pojo.SearchItem;
import com.wcc.taotao.pojo.SearchResult;
import com.wcc.taotao.search.SearchService;
import com.wcc.taotao.search.dao.SearchDao;
import com.wcc.taotao.search.mapper.SearchMapper;
import com.wcc.taotao.utils.E3Result;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: Solr检索service实现
 * @ClassName: SearchServiceImpl
 * @Auther: changchun_wu
 * @Date: 2019/1/24 0:12
 * @Version: 1.0
 **/

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private SearchMapper searchMapper;

    @Autowired
    //private CloudSolrClient solrClient;
    private HttpSolrClient solrClient;

    @Autowired
    private SearchDao searchDao;
    /**
     * @Author: changchun_wu
     * @Date: 2019/1/24 0:13
     * @Description: 导入所有类目到索引库Service实现
     **/
    @Override
    public E3Result importAllSearchItem() throws Exception{
        //1.注入mapper
        //2.调用mapper中的方法进行查询
        List<SearchItem> list = searchMapper.getSearchItemList();
        //3.将查询结果写入索引库
        //3.1引入HttpSolrClient.builder对象
        //3.2创建HttpSolrClient
        //HttpSolrClient build = builder.build();
        //3.3创建SolrInputDocument对象
        SolrInputDocument document = null;
        //3.4添加域到文档中
        for (SearchItem searchItem : list) {
            document = new SolrInputDocument();
            document.addField("id",searchItem.getId());
            document.addField("item_title",searchItem.getTitle());
            document.addField("item_sell_point",searchItem.getSell_point());
            String[] split = searchItem.getImage().split(",");
            document.addField("item_price",searchItem.getPrice());
            document.addField("item_image",split[0]);
            document.addField("item_category_name",searchItem.getCategory_name());
            document.addField("item_desc",searchItem.getItem_desc());
            //3.5将文档添加到索引库
            solrClient.add(document);
        }
        return E3Result.ok();
    }

    @Override
    public SearchResult getSearchResult(String queryString, Integer page, Integer rows) throws Exception {
        //1.创建SolrQuery对象
        SolrQuery solrQuery = new SolrQuery();
        //2.设置查询条件
        //设置主查询条件
        if (!StringUtils.isEmpty(queryString)){
            solrQuery.setQuery(queryString);
        }else
            solrQuery.setQuery("*:*");
        //设置分页
        if (page==null) page=1;
        if (rows==null) rows=60;
        solrQuery.setStart((page-1)*rows);
        solrQuery.setRows(rows);
        //设置默认域
        solrQuery.set("df","item_keywords");
        //设置高亮
        solrQuery.setHighlight(true);   //开启高亮
        solrQuery.setHighlightSimplePre("<em style=\"color: red\">");
        solrQuery.setHighlightSimplePost("</em>");
        solrQuery.addHighlightField("item_title");
        //3.调用Dao层
        SearchResult searchResult = searchDao.getSearchResult(solrQuery);
        //设置总页数
        searchResult.setPageCount(rows%page==0?rows/page:(rows/page)+1);
        //4.返回结果集
        return searchResult;
    }

    @Override
    public E3Result getSearchItemById(Long itemId) throws Exception {
        return searchDao.getSearchItemById(itemId);
    }
}
