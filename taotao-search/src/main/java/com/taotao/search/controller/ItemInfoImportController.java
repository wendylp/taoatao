package com.taotao.search.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TaotaoResult;
import com.taotao.search.service.ItemInfoImportService;
import com.taotao.utils.ExceptionUtil;

@Controller
public class ItemInfoImportController {

	@Autowired
	private ItemInfoImportService itemInfoImportService;

	@RequestMapping("/import/item")
	@ResponseBody
	public TaotaoResult importItemInfoToSolr() {
		/**
		 * 异常需要处理  不能再抛
		 */
		TaotaoResult result = null;
		try {

			result = itemInfoImportService.importItemInfoToSolr();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		
	}

}
