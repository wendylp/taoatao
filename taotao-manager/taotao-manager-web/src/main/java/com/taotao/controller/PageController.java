package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	
	@RequestMapping("/")
	public String gotoIndex() {
		
		return "/index";
	}
	
	@RequestMapping("/{page}")
	public String gotoPage(@PathVariable String page) {
		
		return "/" + page;
	}
	
	@RequestMapping("/rest/page/item-edit")
	public String gotoEditPage() {
		
		return "/item-edit";
		
	}

}
