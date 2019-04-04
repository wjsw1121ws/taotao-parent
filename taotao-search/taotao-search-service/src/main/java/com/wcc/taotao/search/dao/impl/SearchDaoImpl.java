package com.wcc.taotao.search.dao.impl;

import com.wcc.taotao.pojo.SearchItem;
import com.wcc.taotao.pojo.SearchResult;
import com.wcc.taotao.search.dao.SearchDao;
import com.wcc.taotao.search.mapper.SearchMapper;
import com.wcc.taotao.utils.E3Result;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @ClassName: SearchDaoImpl
 * @Auther: changchun_wu
 * @Date: 2019/1/28 1:59
 * @Version: 1.0
 **/
@Repository
public class SearchDaoImpl implements SearchDao {

    @Autowired
    private HttpSolrClient solrClient;
    //private HttpSolrClient solrClient;

    @Autowired
    private SearchMapper mapper;

    @Override
    public SearchResult getSearchResult(SolrQuery solrQuery) throws Exception {
        //1.HttpSolrClient.builder由spring创建,直接注入
        //2.创建HttpSolrClient对象
        //HttpSolrClient build = builder.build();
        //3.创建SolrQuery对象
        //SolrQuery query = new SolrQuery();
        //4.执行查询
        QueryResponse response = solrClient.query(solrQuery);
        //5.获取结果集
        SolrDocumentList solrDocuments = response.getResults();
        //获取高亮
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        //6.遍历结果集并将结果集放入SearchItem中
        List<SearchItem> searchItemList = new ArrayList<>();
        for (SolrDocument solrDocument : solrDocuments) {
            SearchItem searchItem = new SearchItem();
            searchItem.setId(Long.parseLong(solrDocument.get("id").toString()));
            searchItem.setSell_point(solrDocument.get("item_sell_point").toString());
            //设置title高亮显示
            Map<String, List<String>> map = highlighting.get(solrDocument.get("id"));
            List<String> list = map.get("item_title");
            String highting = "";
            if (list!=null&&list.size()!=0){
                highting = list.get(0);
            }else {
                highting = solrDocument.get("item_title").toString();
            }
            searchItem.setTitle(highting);
            searchItem.setPrice(Long.parseLong(solrDocument.get("item_price").toString()));
            searchItem.setImage(solrDocument.get("item_image").toString());
            searchItem.setCategory_name(solrDocument.get("item_category_name").toString());
            searchItemList.add(searchItem);
        }
        //7.创建SearchResult对象
        SearchResult searchResult = new SearchResult();
        searchResult.setItemList(searchItemList);
        searchResult.setTotalCount(solrDocuments.getNumFound());
        //7.返回SearchResult
        return searchResult;
    }

    /**
     * 根据商品id查询商品
     * @param itemId
     * @return
     * @throws Exception
     */
    @Override
    public E3Result getSearchItemById(Long itemId) throws Exception {
        //调用mapper,根据商品id查询商品
        SearchItem item = mapper.getSearchItemById(itemId);
        //创建SolrInputDocument对象
        SolrInputDocument document = new SolrInputDocument();
        //向document中添加域
        document.addField("id",item.getId());
        document.addField("item_title",item.getTitle());
        document.addField("item_sell_point",item.getSell_point());
        String[] split = item.getImage().split(",");
        document.addField("item_price",item.getPrice());
        document.addField("item_image",split[0]);
        document.addField("item_category_name",item.getCategory_name());
        document.addField("item_desc",item.getItem_desc());
        //向索引库中添加文档
        solrClient.add(document);
        solrClient.commit();
        return E3Result.ok();
    }
}
