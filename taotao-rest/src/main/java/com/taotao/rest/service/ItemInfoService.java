package com.taotao.rest.service;

import com.taotao.pojo.TaotaoResult;

public interface ItemInfoService {
	
	TaotaoResult getItemInfo(long itemId);
	TaotaoResult getItemDesc(long itemId);
	TaotaoResult getItemParam(long itemId);

}
