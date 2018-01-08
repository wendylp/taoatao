package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.EasyUIDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

/**
 * 
 * @ClassName: ItemController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author LiuPeng
 * @date 2017年12月1日 下午10:19:42
 *
 */
@Controller
public class ItemController {
	
	
	@Autowired
	private ItemService itemService;
	
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getTbItem(@PathVariable long itemId) {
		
		return itemService.getItemById(itemId);
	}
	
	/**
	 * 
	 * @Title: getItemList 
	 * @Description: TODO
	 * @param page
	 * @param rows
	 * @return    
	 * EasyUIDataGridResult    
	 * @throws
	 */
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(int page, int rows) {
		return itemService.getResult(page, rows);
	}
	
	
	@RequestMapping(value = "/item/save", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult addItem(TbItem item, String desc, String itemParams) {
		
		TaotaoResult result = itemService.addItem(item, desc, itemParams);
		return result;
	}
	
	/**
	 * 
	 * @Title: deleteItem 
	 * @Description: 删除商品
	 * @param ids
	 * @return    
	 * TaotaoResult    
	 * @throws
	 */
	@RequestMapping(value = "/rest/item/delete", method=RequestMethod.POST)
	public TaotaoResult deleteItem(@RequestParam List<Long> ids) {
		
		TaotaoResult result = itemService.deleteItem(ids);
		return result;
		
	}
	
	@RequestMapping(value="/rest/item/update", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult editItem(TbItem item, String desc, String itemParams) {
		
		TaotaoResult result = itemService.editItem(item, desc, itemParams);
		return result;
	}
	
	/**
	 * 
	 * @Title: getItemDesc 
	 * @Description: 编辑商品时用于加载商品描述用于回显
	 * @param itemId
	 * @return    
	 * TaotaoResult    
	 * @throws
	 */
	@RequestMapping("/rest/item/query/item/desc/{itemId}")
	@ResponseBody
	public TaotaoResult getItemDesc(@PathVariable long itemId) {
		
		TaotaoResult result = itemService.getItemDesc(itemId);
		return result;
	}
	
	/**
	 * 
	 * @Title: getItemParam 
	 * @Description: 编辑商品时重新加载规格参数用于回显
	 * @param itemId
	 * @return    
	 * TaotaoResult    
	 * @throws
	 */
	@RequestMapping("/rest/item/param/item/query/{itemId}")
	@ResponseBody
	public TaotaoResult getItemParam(@PathVariable long itemId) {
		TaotaoResult result = itemService.getItemParam(itemId);
		return result;
	}
	
	/**
	 * 
	 * @Title: instock 
	 * @Description: 下架商品
	 * @param ids
	 * @return    
	 * TaotaoResult    
	 * @throws
	 */
	@RequestMapping(value="/rest/item/instock", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult updateItemStatusInstock(@RequestParam List<Long> ids) {
		TaotaoResult result = itemService.updateItemStatusInstock(ids);
		return result;
	}
	
	/**
	 * 
	 * @Title: updateItemStatusReshelf 
	 * @Description: 上架商品
	 * @param ids
	 * @return    
	 * TaotaoResult    
	 * @throws
	 */
	@RequestMapping(value="/rest/item/reshelf", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult updateItemStatusReshelf(@RequestParam List<Long> ids) {
		TaotaoResult result = itemService.updateItemStatusReshelf(ids);
		return result;
		
	}

}
