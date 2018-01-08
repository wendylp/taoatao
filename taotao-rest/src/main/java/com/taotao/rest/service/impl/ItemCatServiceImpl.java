package com.taotao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.rest.pojo.ItemCat;
import com.taotao.rest.pojo.ItemCatResult;
import com.taotao.rest.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Override
	public ItemCatResult getItemCat() {
		ItemCatResult result = new ItemCatResult();
		result.setData(getItemCatList(0));
		return result;
	}

	private List<?> getItemCatList(long parentId) {
		//查询所有商品分类 根据parentId
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		
		List<TbItemCat> tbItemCatList = itemCatMapper.selectByExample(example);
		List<Object> dataList = new ArrayList<>();
		
		int count = 0; //分类显示条目控制
		for(TbItemCat tbItemCat : tbItemCatList) {
			if(tbItemCat.getIsParent()) {//父类分类节点
				ItemCat itemCat = new ItemCat();
				if(parentId == 0) {  
					itemCat.setName("<a href='/products/"+ tbItemCat.getId() +".html'>"+ tbItemCat.getName()+"</a>");
				} else {
					itemCat.setName(tbItemCat.getName());
				}
				itemCat.setUrl("/products/"+ tbItemCat.getId()+".html");
				//递归调用
				itemCat.setItem(getItemCatList(tbItemCat.getId()));
				dataList.add(itemCat);
			
				count++;
				if(parentId == 0 && count >= 14) {//只显示14条分类
					break;
				}
			} else { //不是父类节点
				String itemCat = "/products/"+ tbItemCat.getId() +".html|"+ tbItemCat.getName();
				dataList.add(itemCat);
			}
			
		}
		return dataList;
	}

}
