package com.taotao.portal.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.portal.dao.RedisDao;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 
 * @ClassName: JedisPoolDao
 * @Description: 单机版redis
 * @author LiuPeng
 * @date 2017年12月11日 上午11:17:34
 *
 */
public class JedisPoolDao implements RedisDao {
	
	@Autowired
	private JedisPool jedisPool;
	
	
	@Override
	public String set(String key, String value) {
		Jedis redis = jedisPool.getResource();
		redis.close();
		return redis.set(key, value);
	}

	@Override
	public String get(String key) {
		Jedis redis = jedisPool.getResource();
		redis.close();
		return redis.get(key);
	}

	@Override
	public Long hset(String key, String field, String value) {
		Jedis redis = jedisPool.getResource();
		redis.close();
		return redis.hset(key, field, value);
	}

	@Override
	public String hget(String key, String field) {;
		Jedis redis = jedisPool.getResource();
		redis.close();
		return  redis.hget(key, field);
	}

	@Override
	public Long expire(String key, int seconds) {
		Jedis redis = jedisPool.getResource();
		redis.close();
		return redis.expire(key, seconds);
	}

	@Override
	public Long ttl(String key) {
		Jedis redis = jedisPool.getResource();
		redis.close();
		return redis.ttl(key);
	}

	@Override
	public Long del(String key) {
		Jedis redis = jedisPool.getResource();
		redis.close();
		return redis.del(key);
	}

	@Override
	public Long hdel(String key, String field) {
		Jedis redis = jedisPool.getResource();
		redis.close();
		return redis.hdel(key, field);
	}

	@Override
	public String setex(String key, int seconds, String value) {
		Jedis redis = jedisPool.getResource();
		redis.close();
		return redis.setex(key, seconds, value);
	}

}
