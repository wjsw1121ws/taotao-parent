package com.wcc.taotao.search.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;

/**
 * @Description: 测试SolrJ
 * @ClassName: TestSolrJ
 * @Auther: changchun_wu
 * @Date: 2019/1/24 0:19
 * @Version: 1.0
 **/
public class TestSolrJ {
    /**
     * @Author: changchun_wu
     * @Date: 2019/1/28 1:59
     * @Description: 测试添加
     **/
    @Test
    public void testAdd() throws IOException, SolrServerException {
        String baseUrl = "http://192.168.25.133:8080/solr/collection1";
        //1.创建HttpSolrCline.build对象
        HttpSolrClient.Builder builder = new HttpSolrClient.Builder(baseUrl);
        //2.创建HttpSolrClient对象
        HttpSolrClient build = builder.build();
        //3.创建SolrInputDocument对象
        SolrInputDocument document = new SolrInputDocument();
        //4.向文档中添加域
        document.addField("id","test01");
        document.addField("item_title","这是一个测试");
        //5.将文档添加到索引库
        build.add(document);
        //6.提交
        build.commit();
    }
    /**
     * @Author: changchun_wu
     * @Date: 2019/1/28 2:00
     * @Description: 测试查询
     **/
    @Test
    public void testQuery() throws IOException, SolrServerException {
        String baseUrl = "http://192.168.25.133:8080/solr/collection1";
        //1.创建HttpSolrCline.build对象
        HttpSolrClient.Builder builder = new HttpSolrClient.Builder(baseUrl);
        //2.创建HttpSolrClient对象
        HttpSolrClient build = builder.build();
        //3.创建SolrQuery对象
        SolrQuery query = new SolrQuery();
        //4.设置查询条件
        query.setQuery("联想");
        query.setFilterQueries("item_price:[0 TO 60000]");
        query.set("df","item_title");
        //5.执行查询
        QueryResponse response = build.query(query);
        //6.获取结果集
        SolrDocumentList results = response.getResults();
        System.out.println(results.getNumFound());//获取查询总数
        //7.遍历结果集
        for (SolrDocument result : results) {
            System.out.println(result.get("item_sell_point"));
            System.out.println(result.get("item_category_name"));
        }
        //6.提交
        build.commit();
    }
}
