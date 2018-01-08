package com.taotao.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.taotao.pojo.TaotaoResult;
import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.ItemSearchService;
import com.taotao.utils.HttpClientUtil;

@Service
public class ItemSearchServiceImpl implements ItemSearchService{

	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;
	
	@Value("${ITEM_SEARCH_URL}")
	private String ITEM_SEARCH_URL;
	
	
	@Override
	public SearchResult itemSearch(String queryString, int currentPage) {
		//调用taotao-search服务
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("q", queryString);
		paramMap.put("currentPage", currentPage + "");
		
		String resultStr = HttpClientUtil.doGet(SEARCH_BASE_URL + ITEM_SEARCH_URL, paramMap);
		
		if(!StringUtils.isEmpty(resultStr)) {
			TaotaoResult result = TaotaoResult.formatToPojo(resultStr, SearchResult.class);
			if(result.getStatus() == 200) {
				SearchResult searchResult = (SearchResult) result.getData();
				return searchResult;
			}
		}
		
		return null;
	}

}
