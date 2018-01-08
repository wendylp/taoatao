package com.taotao.portal.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.ItemSearchService;

@Controller
public class ItemSearchController {

	@Autowired
	private ItemSearchService itemSearchService;

	@RequestMapping("/search")
	public String itemSearch(@RequestParam("q") String queryString, @RequestParam(value="page", defaultValue = "1") int currentPage,
			Model model) {
		SearchResult result = null;
		if (!StringUtils.isEmpty(queryString)) {
			try {
				queryString = new String(queryString.getBytes("ISO8859-1"), "UTF-8");
				result = itemSearchService.itemSearch(queryString, currentPage);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		model.addAttribute("query", queryString);
		model.addAttribute("totalPages", result.getTotalPage());
		model.addAttribute("itemList", result.getItemInfoList());

		return "/search";
	}

}
