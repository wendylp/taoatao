package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.EasyUITreeNode;
import com.taotao.pojo.TaotaoResult;
import com.taotao.service.ContentCatService;

@Controller
@RequestMapping("/content/category")
public class ContentCatController {
	
	@Autowired
	private ContentCatService contentCatService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatList(@RequestParam(value="id", defaultValue="0") long parentId){
		
		List<EasyUITreeNode> contentCatList = contentCatService.getContentCatList(parentId);
		return contentCatList;
		
	}
	
	
	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult addContentCat(long parentId, String name) {
		
		TaotaoResult result = contentCatService.insertContentCat(parentId, name);
		return result;
		
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public TaotaoResult deleteContentCat(@RequestParam(defaultValue = "0") long parentId, long id) {
		TaotaoResult result = contentCatService.deleteContentCat(parentId, id);
		return result;
	}
		
	@RequestMapping("/update")
	@ResponseBody
	public TaotaoResult updateContentCat(long id, String name) {
		
		TaotaoResult result = contentCatService.updateContentCat(id, name);
		return result;
		
	}

}
