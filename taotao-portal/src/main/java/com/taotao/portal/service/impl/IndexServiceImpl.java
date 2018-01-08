package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.portal.service.IndexService;
import com.taotao.utils.HttpClientUtil;
import com.taotao.utils.JsonUtils;

@Service
public class IndexServiceImpl implements IndexService{
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	
	@Value("${INDEX_AD_URL}")
	private String INDEX_AD_URL;
	
	@Override
	public String getIndexADList() {
		
		//调服务获取大广告位的数据
		String resultStr = HttpClientUtil.doGet(REST_BASE_URL + INDEX_AD_URL);
		//将结果字符串结果转化为TaotaoReslt
		try {
			TaotaoResult list = TaotaoResult.formatToList(resultStr, TbContent.class);
			List<TbContent> data = (List<TbContent>) list.getData();
			//将pojo转化为响应的数据
			List<Map<String, Object>> resultList = new ArrayList<>();
			for (TbContent content : data) {
				Map<String, Object> map = new HashMap<>();
				map.put("src", content.getPic());
				map.put("width", 670);
				map.put("height", 240);
				map.put("srcB", content.getPic2());
				map.put("widthB", 550);
				map.put("height", 240);
				map.put("href", content.getUrl());
				map.put("alt", content.getTitle());
				
				resultList.add(map);
				//将结果转化为json返回
			}
			return JsonUtils.objectToJson(resultList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
