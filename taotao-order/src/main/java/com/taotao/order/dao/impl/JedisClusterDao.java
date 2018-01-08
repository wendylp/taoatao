package com.taotao.order.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.order.dao.RedisDao;

import redis.clients.jedis.JedisCluster;

/**
 * 
 * @ClassName: JedisClusterDao
 * @Description: 集群版redis
 * @author LiuPeng
 * @date 2017年12月11日 上午11:30:56
 *
 */
public class JedisClusterDao implements RedisDao{

	@Autowired
	private JedisCluster jedisCluster;
	
	@Override
	public String set(String key, String value) {
		return jedisCluster.set(key, value);
	}

	@Override
	public String get(String key) {
		return jedisCluster.get(key);
	}

	@Override
	public Long hset(String key, String field, String value) {
		return jedisCluster.hset(key, field, value);
	}

	@Override
	public String hget(String key, String field) {
		return jedisCluster.hget(key, field);
	}

	@Override
	public Long expire(String key, int seconds) {
		return jedisCluster.expire(key, seconds);
	}

	@Override
	public Long ttl(String key) {
		return jedisCluster.ttl(key);
	}

	@Override
	public Long del(String key) {
		return jedisCluster.del(key);
	}

	@Override
	public Long hdel(String key, String field) {
		return jedisCluster.hdel(key, field);
	}

	@Override
	public String setex(String key, int seconds, String value) {
		return jedisCluster.setex(key, seconds, value);
	}

	@Override
	public Long incr(String key) {
		return jedisCluster.incr(key);
	}

}
