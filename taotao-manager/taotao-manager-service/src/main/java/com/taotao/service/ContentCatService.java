package com.taotao.service;

import java.util.List;

import com.taotao.pojo.EasyUITreeNode;
import com.taotao.pojo.TaotaoResult;

public interface ContentCatService {

	List<EasyUITreeNode> getContentCatList(long parentId);
	
	TaotaoResult insertContentCat(long parentId, String name);
	
	TaotaoResult deleteContentCat(long parentId, long id);

	TaotaoResult updateContentCat(long id, String name);
}
