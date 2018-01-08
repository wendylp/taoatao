package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.EasyUIDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.pojo.TbItemParamItemExample.Criteria;
import com.taotao.service.ItemService;
import com.taotao.utils.IDUtils;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper tbItemMapper;
	
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	@Override
	public TbItem getItemById(long itemId) {

		return tbItemMapper.selectByPrimaryKey(itemId);
	}

	/**
	 * 
	 * @Title: getResult
	 * @Description: 商品列表展示 
	 * @param page
	 * @param rows
	 * @return 
	 * @see com.taotao.service.ItemService#getResult(int, int)
	 */
	@Override
	public EasyUIDataGridResult getResult(int page, int rows) {

		TbItemExample tbItemExample = new TbItemExample();

		PageHelper.startPage(page, rows); //先进行分页
		List<TbItem> list = tbItemMapper.selectByExample(tbItemExample);

		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();

		EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
		easyUIDataGridResult.setTotal(total);
		easyUIDataGridResult.setRows(list);

		return easyUIDataGridResult;
	}

	@Override
	public TaotaoResult addItem(TbItem item, String desc, String paramData) {
		//补全TbItem中的属性
		long itemId = IDUtils.genItemId();
		item.setId(itemId);
		item.setStatus((byte) 1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		
		tbItemMapper.insert(item);
		//插入商品描述
		TaotaoResult result = insertItemDesc(itemId, desc);
		if(result.getStatus() != 200) {
			throw new RuntimeException();
		}
		
		//插入商品规格参数
		result = insertItemParamItem(itemId, paramData);
		if(result.getStatus() != 200) {
			throw new RuntimeException();
			
		}
		return TaotaoResult.ok();
	}
	
	/**
	 * 
	 * @Title: insertItemDesc 
	 * @Description: 插入商品描述
	 * @param itemId
	 * @param desc    
	 * void    
	 * @throws
	 */
	private TaotaoResult insertItemDesc(long itemId, String desc) {
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		
		tbItemDescMapper.insert(itemDesc);
		
		return TaotaoResult.ok();
		
	}
	
	/**
	 * 
	 * @Title: insertItemParamItem 
	 * @Description: 插入商品规格参数
	 * @param itemId
	 * @param paramData
	 * @return    
	 * TaotaoResult    
	 * @throws
	 */
	private TaotaoResult insertItemParamItem(long itemId, String paramData) {
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(paramData);
		itemParamItem.setCreated(new Date());
		itemParamItem.setUpdated(new Date());
		
		itemParamItemMapper.insert(itemParamItem);
		
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteItem(List<Long> itemIds) {
		//根据商品id删除商品  循环删除
		if(!CollectionUtils.isEmpty(itemIds)) {
			
			for (Long itemId : itemIds) {
				// 删除商品
				tbItemMapper.deleteByPrimaryKey(itemId);
				// 删除商品描述
				tbItemDescMapper.deleteByPrimaryKey(itemId);

				// 删除商品参数
				TbItemParamItemExample example = new TbItemParamItemExample();
				Criteria criteria = example.createCriteria();
				criteria.andItemIdEqualTo(itemId);

				itemParamItemMapper.deleteByExample(example);

				return TaotaoResult.ok();

			}
		}
		return null;
	}

	@Override
	public TaotaoResult editItem(TbItem item, String desc, String itemParams) {
		//编辑商品  需补全pojo
		item.setCreated(new Date());
		item.setUpdated(new Date());
		tbItemMapper.updateByPrimaryKeySelective(item);
		//编辑商品描述
		TaotaoResult result = editItemDesc(item.getId(), desc);
		if(result.getStatus() != 200) {
			throw new RuntimeException();
		}
		
		result = editItemParam(item.getId(), itemParams);
		if(result.getStatus() != 200 ) {
			throw new RuntimeException();
		}
		return TaotaoResult.ok();
	}
	
	//更新商品描述表
	private TaotaoResult editItemDesc(long itemId, String desc) {
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		
		tbItemDescMapper.updateByPrimaryKeyWithBLOBs(itemDesc);
		return TaotaoResult.ok();
		
	}
	
	//更新规格参数
	private TaotaoResult editItemParam(long itemId, String itemParams) {
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParams);
		itemParamItem.setCreated(new Date());
		itemParamItem.setUpdated(new Date());
		
		itemParamItemMapper.updateByPrimaryKeyWithBLOBs(itemParamItem);
		return TaotaoResult.ok();
		
		
	}

	
	@Override
	public TaotaoResult getItemDesc(long itemId) {
		
		TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
		if(itemDesc != null) {
			return TaotaoResult.ok(itemDesc);
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult getItemParam(long itemId) {
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if(!CollectionUtils.isEmpty(list)) {
			return TaotaoResult.ok(list.get(0));
		}
		return TaotaoResult.ok();
	}

	/**
	 * 
	 * @Title: updateItemStatusInstock
	 * @Description: 下架商品
	 * @param ids
	 * @return 
	 * @see com.taotao.service.ItemService#updateItemStatusInstock(java.util.List)
	 */
	@Override
	public TaotaoResult updateItemStatusInstock(List<Long> itemIds) {
		if(!CollectionUtils.isEmpty(itemIds)) {
			tbItemMapper.updateItemStatusInstock(itemIds);
			return TaotaoResult.ok();
		}
		return null;
	}

	/**
	 * 
	 * @Title: updateItemStatusReshelf
	 * @Description: 上架商品
	 * @param ids
	 * @return 
	 * @see com.taotao.service.ItemService#updateItemStatusReshelf(java.util.List)
	 */
	@Override
	public TaotaoResult updateItemStatusReshelf(List<Long> itemIds) {
		if(!CollectionUtils.isEmpty(itemIds)) {
			tbItemMapper.updateItemStatusReshelf(itemIds);
			return TaotaoResult.ok();
		}
		return null;
	}

}
