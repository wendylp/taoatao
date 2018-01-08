package com.taotao.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.ItemSearchService;

@Service
public class ItemSearchServiceImpl implements ItemSearchService {

	@Autowired
	private SearchDao searchDao;

	@Override
	public SearchResult itemSearch(String queryString, int currentPage, int rows) throws Exception {
		SolrQuery solrQuery = new SolrQuery();
		// 如果查询条件为空 查询所有
		if (StringUtils.isEmpty(queryString)) {
			solrQuery.setQuery("*:*");
		} else {
			solrQuery.setQuery(queryString);
		}
		
		//设置起始页 每页条数
		solrQuery.setStart((currentPage -1) * rows);
		solrQuery.setRows(rows);
		
		//设置高亮
		solrQuery.setHighlight(true);
		//高亮显示区域
		solrQuery.addHighlightField("item_title");
		//高亮显示前缀
		solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
		solrQuery.setHighlightSimplePre("</em>");
		//设置默认搜索域
		solrQuery.set("df", "item_keywords");
		
		SearchResult result = searchDao.itemSearch(solrQuery);
	
		result.setCurrentPage(currentPage);
		//设置返回结果分页
		long totalRecord = result.getTotalRecord();
		long totalPage = totalRecord / rows;
		if(totalRecord % rows > 0) {
			totalPage++;
		}
		result.setTotalPage(totalPage);
	
		return result;
	}

}
