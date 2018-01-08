package com.taotao.rest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.rest.pojo.ItemCatResult;
import com.taotao.rest.service.ItemCatService;
import com.taotao.utils.JsonUtils;

@Controller
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping(value="/itemcat/all", produces=MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
	@ResponseBody
	public String getItemCat(String callback) {
		ItemCatResult result = itemCatService.getItemCat();
		String json = JsonUtils.objectToJson(result);
		
		String resultStr = callback + "("+ json+ ");";
		return resultStr;
		
	}

}
