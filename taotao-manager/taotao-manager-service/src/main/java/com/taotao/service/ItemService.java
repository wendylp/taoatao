package com.taotao.service;

import java.util.List;

import com.taotao.pojo.EasyUIDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

/**
 * 
 * @ClassName: ItemService
 * @Description: 商品信息
 * @author LiuPeng
 * @date 2017年12月3日 上午10:40:16
 *
 */
public interface ItemService {
	
	TbItem getItemById(long itemId);
	
	EasyUIDataGridResult getResult(int total, int rows);
	
	TaotaoResult addItem(TbItem item, String desc, String itemParams);
	
	TaotaoResult deleteItem(List<Long> itemIds);

	TaotaoResult editItem(TbItem item, String desc, String itemParams);

	TaotaoResult getItemDesc(long itemId);

	TaotaoResult getItemParam(long itemId);

	TaotaoResult updateItemStatusInstock(List<Long> itemIds);

	TaotaoResult updateItemStatusReshelf(List<Long> itemIds);
	
}
