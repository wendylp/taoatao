package com.taotao.search.service;

import com.taotao.search.pojo.SearchResult;

public interface ItemSearchService {
	
	SearchResult itemSearch(String queryString, int currentPage, int rows) throws Exception;

}
