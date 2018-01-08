package com.taotao.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TaotaoResult;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.ItemSearchService;
import com.taotao.utils.ExceptionUtil;

@Controller
public class ItemSearchController {

	@Autowired
	private ItemSearchService itemSearchService;

	@RequestMapping("/query/item")
	@ResponseBody
	public TaotaoResult itemSearch(@RequestParam("q") String queryString, 
								   @RequestParam(defaultValue = "1") int currentPage,
								   @RequestParam(defaultValue = "60") int rows) {

		if(StringUtils.isEmpty(queryString)) {
			return TaotaoResult.build(400, "查询参数不能为空");
		}
		 
		try {
			queryString = new String(queryString.getBytes("ISO8859-1"), "utf-8");
			SearchResult result = itemSearchService.itemSearch(queryString, currentPage, rows);
			return TaotaoResult.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

}
