package com.taotao.service;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;

/**
 * 
 * @ClassName: ItemParamService
 * @Description: 根据商品分类id查询模板
 * @author LiuPeng
 * @date 2017年12月5日 上午11:05:46
 *
 */
public interface ItemParamService {
	
	TaotaoResult getItemParamByCid(long cid);
	
	TaotaoResult insertItemParam(TbItemParam itemParam);

}
