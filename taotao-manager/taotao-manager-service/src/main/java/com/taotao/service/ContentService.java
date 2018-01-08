package com.taotao.service;

import java.util.List;

import com.taotao.pojo.EasyUIDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {
	
	EasyUIDataGridResult getContentList(long categoryId, int page, int rows);

	TaotaoResult insertContent(TbContent content);

	TaotaoResult editContent(TbContent content);

	TaotaoResult deleteContents(List<Long> ids);
}
