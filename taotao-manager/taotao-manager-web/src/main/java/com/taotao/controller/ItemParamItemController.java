package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.service.ItemParamItemService;

@Controller
public class ItemParamItemController {
	
	@Autowired
	private ItemParamItemService itemParamItemService;
	
	@RequestMapping("/showItem/{itemId}")
	public String showItemParams(@PathVariable long itemId, Model model) {
		
		String itemParam = itemParamItemService.getItemParamItemByItemId(itemId);
		model.addAttribute("itemParam", itemParam);
		return "/itemParamItem";
	}

}
