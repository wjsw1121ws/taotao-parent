package com.wcc.taotao.search;

import com.wcc.taotao.pojo.SearchResult;
import com.wcc.taotao.utils.E3Result;

/**
 * @Description: solr检索Service
 * @ClassName: SearchService
 * @Auther: changchun_wu
 * @Date: 2019/1/24 0:05
 * @Version: 1.0
 **/
public interface SearchService {
    /**
     * @Author: changchun_wu
     * @Date: 2019/1/24 0:15
     * @Description: 导入类目到索引库service接口
     **/
    E3Result importAllSearchItem() throws Exception;

    /**
     *
     * @param queryString 查询条件
     * @param page 总页数
     * @param rows 每页显示行数
     * @return 搜索结果
     * @throws Exception
     */
    SearchResult getSearchResult(String queryString,Integer page,Integer rows) throws Exception;

    E3Result getSearchItemById(Long itemId) throws Exception;
}
