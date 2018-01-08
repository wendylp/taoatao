package com.taotao.search.service;


import com.taotao.pojo.TaotaoResult;

public interface ItemInfoImportService {
	
	TaotaoResult importItemInfoToSolr() throws Exception;

}
