package com.taotao.rest.service;

import com.taotao.pojo.TaotaoResult;

/**
 * 
 * @ClaCacheSynServicehronized
 * @Description: 缓存同步
 * @author LiuPeng
 * @date 2017年12月11日 下午1:36:44
 *
 */
public interface CacheSynService {
	TaotaoResult CacheSyn(long categoryId);
}
