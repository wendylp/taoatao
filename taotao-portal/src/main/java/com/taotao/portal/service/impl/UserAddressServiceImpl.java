package com.taotao.portal.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.UserAddress;
import com.taotao.portal.service.UserAddressService;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.HttpClientUtil;
import com.taotao.utils.JsonUtils;

@Service
public class UserAddressServiceImpl implements UserAddressService {

	@Value("${USER_ADDRESS_BASE_URL}")
	private String USER_ADDRESS_BASE_URL;
	
	@Value("${ADD_ADRESS_URL}")
	private String ADD_ADRESS_URL;
	
	@Value("${GET_USER_ADDRESS}")
	private String GET_USER_ADDRESS;

	@Override
	public TaotaoResult insertUserAddress(UserAddress userAddress, HttpServletRequest request) {

		//从cookie中获取登录用户的信息
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		
		Map<String, String> param = new HashMap<>();
		param.put("userId", token);
		param.put("username", userAddress.getUsername());
		param.put("phone", userAddress.getPhone());
		param.put("address", userAddress.getAddress());
		param.put("isDefault", userAddress.getIsDefault() + "");
		
		HttpClientUtil.doPost(USER_ADDRESS_BASE_URL + ADD_ADRESS_URL, param);
		return TaotaoResult.ok();
	}

	@Override
	public List<UserAddress> getUserAddressByUserId(String userId, HttpServletRequest request) {
		String resultStr = HttpClientUtil.doGet(USER_ADDRESS_BASE_URL + GET_USER_ADDRESS + userId);
		List<UserAddress> result = JsonUtils.jsonToList(resultStr, UserAddress.class);
		return result;
	}
	

}
