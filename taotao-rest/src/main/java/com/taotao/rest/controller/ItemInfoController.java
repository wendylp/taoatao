package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TaotaoResult;
import com.taotao.rest.service.ItemInfoService;

@Controller
public class ItemInfoController {
	
	@Autowired
	private ItemInfoService itemInfoService;
	
	@RequestMapping("/item/info/{itemId}")
	@ResponseBody
	private TaotaoResult getItemInfo(@PathVariable long itemId) {
		TaotaoResult itemInfo = itemInfoService.getItemInfo(itemId);
		return itemInfo;
	}
	
	@RequestMapping("/item/desc/{itemId}")
	@ResponseBody
	public TaotaoResult getItemDesc(@PathVariable long itemId) {
		TaotaoResult itemDesc = itemInfoService.getItemDesc(itemId);
		return itemDesc;
	}
	
	
	@RequestMapping("/item/param/{itemId}")
	@ResponseBody
	public TaotaoResult getItemParam(@PathVariable long itemId) {
		TaotaoResult itemParam = itemInfoService.getItemParam(itemId);
		return itemParam;
	}

}
