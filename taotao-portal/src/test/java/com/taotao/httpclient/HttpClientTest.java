package com.taotao.httpclient;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.taotao.utils.HttpClientUtil;

public class HttpClientTest {

	@Test
	public void testHttpClient() {
		
		String doGet = HttpClientUtil.doGet("http://www.baidu.com");
		System.out.println(doGet);
	}
	
	@Test
	public void testStringUtils() {
		System.out.println(StringUtils.isEmpty(null));
		System.out.println(StringUtils.isEmpty(""));
		System.out.println("".length());
		System.out.println(StringUtils.isEmpty(" "));
		System.out.println(" ".length());
		
		//侧重于内容  
		System.out.println(StringUtils.isBlank(null));
		System.out.println(StringUtils.isBlank(""));
		System.out.println(StringUtils.isBlank(" "));
	}
	
	@Test
	public void testCollectionUtils() {
		System.out.println(CollectionUtils.isEmpty(null));
		System.out.println(CollectionUtils.isEmpty(new ArrayList()));
		List<String> list = new ArrayList<>();
		list.add("");
		System.out.println(CollectionUtils.isEmpty(list));
	}

}
