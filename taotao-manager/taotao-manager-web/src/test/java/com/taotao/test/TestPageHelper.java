package com.taotao.test;


import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;

class TestPageHelper {

	@Test
	void testPageHelper() {
		
		ApplicationContext ioc = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		
		TbItemMapper itemMapper = ioc.getBean(TbItemMapper.class);
		
		TbItemExample itemExample = new TbItemExample();
		
		//进行分页处理
		PageHelper.startPage(2, 10);  //第一个参数：当前的页码   第二个参数：煤业显示的条数
		List<TbItem> list = itemMapper.selectByExample(itemExample);
		
		for(TbItem item : list) {
			String title = item.getTitle();
			System.out.println(title);
		}
		
		
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		
		System.out.println("总记录数：" + total);
		
		int pageNum = pageInfo.getPageNum();
		System.out.println("当前页码：" + pageNum);
		
		int pageSize = pageInfo.getPageSize();
		System.out.println("每页数据：" + pageSize);
		
		
	}
	
	@Test
	public void testJUnit() {
		
		System.out.println(123);
	}

}
