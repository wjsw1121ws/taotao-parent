package com.wcc.taotao.search.dao;

import com.wcc.taotao.pojo.SearchItem;
import com.wcc.taotao.pojo.SearchResult;
import com.wcc.taotao.utils.E3Result;
import org.apache.solr.client.solrj.SolrQuery;

/**
 * @Description: solr检索Dao接口
 * @ClassName: SearchDao
 * @Auther: changchun_wu
 * @Date: 2019/1/28 1:58
 * @Version: 1.0
 **/
public interface SearchDao{
    SearchResult getSearchResult(SolrQuery solrQuery) throws Exception;
    E3Result getSearchItemById(Long id) throws Exception;
}
