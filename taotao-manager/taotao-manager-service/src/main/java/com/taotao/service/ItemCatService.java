package com.taotao.service;

import java.util.List;

import com.taotao.pojo.EasyUITreeNode;

/**
 * 
 * @ClassName: ItemCatService
 * @Description: 商品类目(分类)
 * @author LiuPeng
 * @date 2017年12月3日 上午10:39:42
 *
 */
public interface ItemCatService {
	
	List<EasyUITreeNode> getItemCatList(long parentId);

}
