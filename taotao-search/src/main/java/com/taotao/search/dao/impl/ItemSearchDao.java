package com.taotao.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.ItemInfo;
import com.taotao.search.pojo.SearchResult;

@Repository
public class ItemSearchDao implements SearchDao {

	@Autowired
	private SolrServer solrServer;

	@Override
	public SearchResult itemSearch(SolrQuery solrQuery) throws Exception {
		// 根据查询条件查询索引库
		QueryResponse response = solrServer.query(solrQuery);
		// 取商品信息列表
		SolrDocumentList resultList = response.getResults();
		// 总记录数据
		long totalRecord = resultList.getNumFound();

		List<ItemInfo> itemInfoList = new ArrayList<>();

		for (SolrDocument solrDocument : resultList) {
			ItemInfo itemInfo = new ItemInfo();

			itemInfo.setId(Long.valueOf(String.valueOf(solrDocument.get("id"))));
			// 取高亮显示
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String title = "";
			if (!CollectionUtils.isEmpty(list)) {
				title = list.get(0);
			} else {
				title = (String) solrDocument.get("item_title");
			}
			
			itemInfo.setTitle(title);
			itemInfo.setPrice(Long.valueOf(String.valueOf(solrDocument.get("item_price"))));
			itemInfo.setSellPoint((String) solrDocument.get("item_sell_point"));
			itemInfo.setImage((String) solrDocument.get("item_image"));
			itemInfo.setCategoryName((String) solrDocument.get("item_category-name"));

			itemInfoList.add(itemInfo);
		}

		SearchResult result = new SearchResult();
		result.setItemInfoList(itemInfoList);
		result.setTotalRecord(totalRecord);

		return result;
	}

}
