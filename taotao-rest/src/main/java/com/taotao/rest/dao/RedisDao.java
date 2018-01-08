package com.taotao.rest.dao;

public interface RedisDao {
	
	String set(String key, String value);
	String get(String key);
	String setex(String key, int seconds, String value);
	
	Long hset(String key, String field, String value);
	String hget(String key, String field);
	
	Long expire(String key, int seconds);
	Long ttl(String key);
	
	Long del(String key);
	Long hdel(String key, String field);
	
}
