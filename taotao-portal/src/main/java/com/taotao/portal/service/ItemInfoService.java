package com.taotao.portal.service;

import com.taotao.pojo.TbItemDesc;
import com.taotao.portal.pojo.ItemInfo;

public interface ItemInfoService {
	
	ItemInfo getIntemInfo(long itemId); //注意使用ItemInfo而不用TbItem 多张图片image属性的处理
	TbItemDesc getItemDesc(long itemId);
	String getItemParam(long itemId);

}
