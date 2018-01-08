package com.taotao.portal.service;

import com.taotao.portal.pojo.SearchResult;

/**
 * 
 * @ClassName: ItemSearchService
 * @Description: 商品搜索
 * @author LiuPeng
 * @date 2017年12月13日 上午11:39:56
 *
 */
public interface ItemSearchService {
	
	SearchResult itemSearch(String queryString, int currentPage);

}
