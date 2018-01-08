package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.EasyUITreeNode;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	
	@Autowired
	private TbItemCatMapper tbItemCatMapper;

	@Override
	public List<EasyUITreeNode> getItemCatList(long parentId) {
		//根据parentId查询子节点itemCatList
		TbItemCatExample tbItemCatExample = new TbItemCatExample();
		//设置查询条件
		Criteria criteria = tbItemCatExample.createCriteria();
		//根据parentId查询子节点
		criteria.andParentIdEqualTo(parentId);
		
		List<TbItemCat> itemCatList = tbItemCatMapper.selectByExample(tbItemCatExample);
		
		List<EasyUITreeNode> resultList = new ArrayList<>();
		for (TbItemCat tbItemCat : itemCatList) {
			EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
			easyUITreeNode.setId(tbItemCat.getId());
			easyUITreeNode.setText(tbItemCat.getName());
			easyUITreeNode.setState(tbItemCat.getIsParent() ? "closed" : "open");
			
			resultList.add(easyUITreeNode)
;		}
		return resultList;
	}

}
