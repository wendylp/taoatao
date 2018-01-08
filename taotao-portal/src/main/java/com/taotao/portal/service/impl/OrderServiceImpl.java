package com.taotao.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.pojo.Order;
import com.taotao.pojo.TaotaoResult;
import com.taotao.portal.service.OrderService;
import com.taotao.utils.HttpClientUtil;
import com.taotao.utils.JsonUtils;

@Service
public class OrderServiceImpl implements OrderService {

	@Value("${ORDER_BASE_URL}")
	private String ORDER_BASE_URL;
	
	@Value("${ORDER_CREATE_URL}")
	private String ORDER_CREATE_URL;
	
	@Override
	public String createOrder(Order order) {
		//调用taotao-order服务
		String resultStr = HttpClientUtil.doPostJson(ORDER_BASE_URL + ORDER_CREATE_URL, JsonUtils.objectToJson(order));
		TaotaoResult result = TaotaoResult.format(resultStr);
		if(result.getStatus() == 200) {
			Object orderId = result.getData();
			return orderId.toString();
		}
		
		return "";
	}

}
