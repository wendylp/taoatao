package com.taotao.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.pojo.TaotaoResult;
import com.taotao.rest.dao.impl.JedisPoolDao;
import com.taotao.rest.service.CacheSynService;

@Service
public class CacheSynServiceImpl implements CacheSynService{
	
	@Autowired
	private JedisPoolDao jedisPoolDao;
	
	@Value("${CONTENT_REDIS_POOL_KEY}")
	private String CONTENT_REDIS_POOL_KEY;
	
	@Override
	public TaotaoResult CacheSyn(long categoryId) {
		try {
			jedisPoolDao.hdel(CONTENT_REDIS_POOL_KEY, categoryId + "");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok();
	}

}
