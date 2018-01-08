package com.taotao.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.pojo.TbItemParamItemExample.Criteria;
import com.taotao.rest.dao.impl.JedisPoolDao;
import com.taotao.rest.service.ItemInfoService;
import com.taotao.utils.JsonUtils;

@Service
public class ItemInfoServiceImpl implements ItemInfoService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	@Autowired
	private JedisPoolDao jedisPoolDao;

	@Value("${ITEM_REDIS_KEY}")
	private String ITEM_REDIS_KEY;

	@Value("${EXPIRE_TIME}")
	private String EXPIRE_TIME; // 过期时间1天 计算后的结果

	@Override
	public TaotaoResult getItemInfo(long itemId) {
		// 先从缓存中取
		try {
			String result = jedisPoolDao.get(ITEM_REDIS_KEY + ":" + "info" + ":" + itemId);
			if (!StringUtils.isEmpty(result)) {
				TaotaoResult.ok(JsonUtils.jsonToPojo(result, TbItem.class));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 查询商品信息返回
		TbItem item = itemMapper.selectByPrimaryKey(itemId);

		// 加入缓存把那个设置过期时间
		try {
			jedisPoolDao.setex(ITEM_REDIS_KEY + ":" + "info" + ":" + itemId, Integer.valueOf(EXPIRE_TIME),
					JsonUtils.objectToJson(item));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok(item);
	}

	@Override
	public TaotaoResult getItemDesc(long itemId) {
		// 先从缓存中取
		String result = jedisPoolDao.get(ITEM_REDIS_KEY + ":" + "desc" + ":" + itemId);
		try {
			if (!StringUtils.isEmpty(result)) {
				return TaotaoResult.ok(JsonUtils.jsonToPojo(result, TbItemDesc.class));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 查询商品描述信息
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		// 加入缓存
		try {
			jedisPoolDao.setex(ITEM_REDIS_KEY + ":" + "desc" + ":" + itemId, Integer.valueOf(EXPIRE_TIME),
					JsonUtils.objectToJson(itemDesc));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok(itemDesc);
	}

	@Override
	public TaotaoResult getItemParam(long itemId) {

		// 先从缓存中取
		String result = jedisPoolDao.get(ITEM_REDIS_KEY + ":" + "param" + ":" + itemId);
		try {
			if (!StringUtils.isEmpty(result)) {
				return TaotaoResult.ok(JsonUtils.jsonToPojo(result, TbItemParamItem.class));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 查询商品描述信息
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);

		List<TbItemParamItem> itemParamItems = itemParamItemMapper.selectByExampleWithBLOBs(example);
		TbItemParamItem itemParamItem = null;
		if (!CollectionUtils.isEmpty(itemParamItems)) {
			itemParamItem = itemParamItems.get(0);
			try {
				jedisPoolDao.setex(ITEM_REDIS_KEY + ":" + "param" + ":" + itemId, Integer.valueOf(EXPIRE_TIME),
						JsonUtils.objectToJson(itemParamItem));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return TaotaoResult.ok(itemParamItem);
	}

}
