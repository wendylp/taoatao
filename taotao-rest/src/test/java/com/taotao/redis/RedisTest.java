package com.taotao.redis;


import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotao.rest.dao.impl.JedisPoolDao;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class RedisTest {

	@Test
	public void testRedisSingle() {
		Jedis jedis = new Jedis("192.168.112.130", 6379);
		String value = jedis.get("k2");
		jedis.close();
		
		System.out.println(value);
		
	}
	
	
	@Test
	public void testRedisPool() {
		
		JedisPool jedisPool = new JedisPool("192.168.112.130", 6379);
		Jedis jedis = jedisPool.getResource();
		String value = jedis.get("k3");
		
		jedis.close();
		jedisPool.close();
		
		System.out.println(value);
	}
	
	
	@Test
	public void testRedisCluster() {
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.112.130", 7001));
		nodes.add(new HostAndPort("192.168.112.130", 7002));
		nodes.add(new HostAndPort("192.168.112.130", 7003));
		nodes.add(new HostAndPort("192.168.112.130", 7004));
		nodes.add(new HostAndPort("192.168.112.130", 7005));
		nodes.add(new HostAndPort("192.168.112.130", 7006));
		
		JedisCluster jedisCluster = new JedisCluster(nodes);
		String value = jedisCluster.get("k2");
		jedisCluster.close();
		
		System.out.println(value);
	}
	
	@Test
	public void testSpringRedisSingle() {
		ApplicationContext ioc = new ClassPathXmlApplicationContext("spring/applicationContext-redis.xml");
		JedisPool jedisPool = (JedisPool) ioc.getBean("jedisPool");
		
		Jedis jedis = jedisPool.getResource();
		String value = jedis.get("k1");
		
		jedis.close();
		jedisPool.close();
		
		System.out.println(value);
		
	}
	
	
	@Test
	public void testSpringRedisCluster() {
		ApplicationContext ioc = new ClassPathXmlApplicationContext("spring/applicationContext-redis.xml");
		JedisCluster jedisCluster = (JedisCluster) ioc.getBean("jedisCluster");
		
		String value = jedisCluster.get("k1");
		System.out.println(value);
		jedisCluster.close();
		
	}

	@Test
	public void testJedisPoolDao() {
		ApplicationContext ioc = new ClassPathXmlApplicationContext("spring/applicationContext-redis.xml");
		JedisPoolDao jedisPoolDao = (JedisPoolDao) ioc.getBean("jedisPoolDao");
		
		String value = jedisPoolDao.hget("CONTENT_REDIS_POOL_KEY", 89 + "");
		System.out.println(value);
	}
}
