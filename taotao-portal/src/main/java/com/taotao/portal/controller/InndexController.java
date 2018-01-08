package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.portal.service.IndexService;

@Controller
public class InndexController {

	@Autowired
	private IndexService indexService;

	@RequestMapping("/index")
	public String gotoIndex(Model model) {
		String indexADList = indexService.getIndexADList();
		model.addAttribute("ad1", indexADList);
		
		return "/index";
	}

}
