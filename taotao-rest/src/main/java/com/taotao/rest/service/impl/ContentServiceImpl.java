package com.taotao.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.rest.dao.impl.JedisPoolDao;
import com.taotao.rest.service.ContentService;
import com.taotao.utils.JsonUtils;

@Service
public class ContentServiceImpl implements ContentService{

	@Autowired
	private TbContentMapper contentMapper;
	
	@Autowired
	private JedisPoolDao jedisPoolDao;
	
	@Value("${CONTENT_REDIS_POOL_KEY}")
	private String CONTENT_REDIS_POOL_KEY ;

	/**
	 * 
	 * @Title: getContentList
	 * @Description: 根据内容分类查询内容列表
	 * @param categoryId
	 * @return 
	 * @see com.taotao.rest.service.ContentService#getContentList(long)
	 */
	@Override
	public TaotaoResult getContentList(long categoryId) {
		
		//从redis中取
		try {
			
			String value = jedisPoolDao.hget(CONTENT_REDIS_POOL_KEY, categoryId + "");
			if(!StringUtils.isEmpty(value)) {
				List<TbContent> list = JsonUtils.jsonToList(value, TbContent.class);
				return TaotaoResult.ok(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		
		List<TbContent> list = contentMapper.selectByExample(example);
		
		//加入redis缓存
		try {
			String listStr = JsonUtils.objectToJson(list);
			jedisPoolDao.hset(CONTENT_REDIS_POOL_KEY, categoryId + "", listStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return TaotaoResult.ok(list);
	}
	
	
	
}
