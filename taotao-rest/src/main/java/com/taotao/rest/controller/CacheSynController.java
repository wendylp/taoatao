package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TaotaoResult;
import com.taotao.rest.service.CacheSynService;

@Controller
public class CacheSynController {
	
	@Autowired
	private CacheSynService cacheSynService;
	
	@RequestMapping("/content/list/cache/{categoryId}")
	@ResponseBody
	public TaotaoResult ContentListCacheSyn(@PathVariable long categoryId) {
		TaotaoResult result = cacheSynService.CacheSyn(categoryId);
		return result;
	}

}
