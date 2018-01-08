package com.taotao.order.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.order.dao.RedisDao;

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
		String result = redis.set(key, value);
		redis.close();
		return result;
	}

	@Override
	public String get(String key) {
		Jedis redis = jedisPool.getResource();
		String result = redis.get(key);
		redis.close();
		return result;
	}

	@Override
	public Long hset(String key, String field, String value) {
		Jedis redis = jedisPool.getResource();
		Long result = redis.hset(key, field, value);
		redis.close();
		return result;
	}

	@Override
	public String hget(String key, String field) {
		;
		Jedis redis = jedisPool.getResource();
		String result = redis.hget(key, field);
		redis.close();
		return result;
	}

	@Override
	public Long expire(String key, int seconds) {
		Jedis redis = jedisPool.getResource();
		Long result = redis.expire(key, seconds);
		redis.close();
		return result;
	}

	@Override
	public Long ttl(String key) {
		Jedis redis = jedisPool.getResource();
		Long result = redis.ttl(key);
		redis.close();
		return result;
	}

	@Override
	public Long del(String key) {
		Jedis redis = jedisPool.getResource();
		Long result = redis.del(key);
		redis.close();
		return result;
	}

	@Override
	public Long hdel(String key, String field) {
		Jedis redis = jedisPool.getResource();
		Long result = redis.hdel(key, field);
		redis.close();
		return result;
	}

	@Override
	public String setex(String key, int seconds, String value) {
		Jedis redis = jedisPool.getResource();
		String result = redis.setex(key, seconds, value);
		redis.close();
		return result;
	}

	@Override
	public Long incr(String key) {
		Jedis redis = jedisPool.getResource();
		Long result = redis.incr(key);
		redis.close();
		return result;
	}

}
