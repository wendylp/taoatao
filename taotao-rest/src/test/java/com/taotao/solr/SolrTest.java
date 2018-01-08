package com.taotao.solr;




import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrTest {

	@Test
	public void testDocument() throws Exception {
		//创建solr服务连接
		SolrServer solrServer = new HttpSolrServer("http://192.168.112.130:8080/solr");
		//创建文档对象
		SolrInputDocument solrInputDocument = new SolrInputDocument();
		
		//设置文档属性
		solrInputDocument.addField("id", "10001");
		solrInputDocument.addField("item_price", 599);
		solrInputDocument.addField("item_sell_point", "商品描述");
		//把文档加入到索引库中
		solrServer.add(solrInputDocument);
		//提交solr服务
		solrServer.commit();
		
	}
	
	@Test
	public void testSolrQuery() throws Exception {
		SolrServer solrServer = new HttpSolrServer("http://192.168.112.130:8080/solr");
		//创建查询对象
		SolrQuery solrQuery = new SolrQuery();
		//设置查询条件
		solrQuery.setQuery("*:*");
		
		//执行查询获取查询结果
		QueryResponse response = solrServer.query(solrQuery);
		SolrDocumentList results = response.getResults();
		
		for (SolrDocument solrDocument : results) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_price"));
			System.out.println(solrDocument.get("item_sell_point"));
		}
		
	}
	
	@Test
	public void deleteDocument () throws Exception {
		SolrServer solrServer = new HttpSolrServer("http://192.168.112.130:8080/solr");
		solrServer.deleteByQuery("*:*");
		solrServer.commit();
		
	}

}
