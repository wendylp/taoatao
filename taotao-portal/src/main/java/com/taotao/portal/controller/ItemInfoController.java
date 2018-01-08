package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TbItemDesc;
import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.service.ItemInfoService;

@Controller
@RequestMapping("/item")
public class ItemInfoController {
	
	@Autowired
	private ItemInfoService itemInfoService;
	
	@RequestMapping("/{itemId}")
	private String getItemInfo(@PathVariable long itemId, Model model) {
		ItemInfo intemInfo = itemInfoService.getIntemInfo(itemId);
		model.addAttribute("item", intemInfo);
		return "/item";
	}
	
	@RequestMapping(value="/desc/{itemId}", produces=MediaType.TEXT_HTML_VALUE + ";charset=UTF-8")
	@ResponseBody
	public String  getItemDesc(@PathVariable long itemId) {
		TbItemDesc itemDesc = itemInfoService.getItemDesc(itemId);
		return itemDesc.getItemDesc();
	}
	
	@RequestMapping(value="/param/{itemId}", produces=MediaType.TEXT_HTML_VALUE + ";charset=UTF-8")
	@ResponseBody
	public String  getItemParam(@PathVariable long itemId) {
		String itemParam = itemInfoService.getItemParam(itemId);
		return itemParam;
	}

}
