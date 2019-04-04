package com.wcc.taotao.search.solr;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Description: 测试Solr集群
 * @ClassName: TestSolrCloud
 * @Auther: changchun_wu
 * @Date: 2019/2/9 0:41
 * @Version: 1.0
 **/

/*public class TestSolrCloud {

    @Test
    public void testSolrCloud() throws IOException, SolrServerException {
        //1.创建CloudSolrClient.Builder对象,并传入参数
        List<String> zkHosts = new ArrayList<>();
        zkHosts.add("192.168.25.133:2181");
        zkHosts.add("192.168.25.133:2182");
        zkHosts.add("192.168.25.133:2183");
        Optional<String> zkChroot = Optional.of("/");
        CloudSolrClient.Builder builder = new CloudSolrClient.Builder(zkHosts,zkChroot);
        //2.创建CloudSolrClient对象
        CloudSolrClient cloudSolrClient = builder.build();
        //3.设置默认的索引库
        cloudSolrClient.setDefaultCollection("collection2");
        //4.创建SolrInputDocument对象
        SolrInputDocument document = new SolrInputDocument();
        //5.添加域到文档中
        document.addField("id","testCloud");
        document.addField("item_title","你好!");
        //6.提交文档到索引库
        cloudSolrClient.add(document);
        //7.提交文档到索引库
        cloudSolrClient.commit();

    }
}*/
