package com.taotao.rest.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.UserAddressMapper;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.UserAddress;
import com.taotao.rest.service.UserAddressService;

@Service
public class UserAddressServiceImpl implements UserAddressService {

	@Autowired
	private UserAddressMapper userAddressMapper;
	
	@Override
	public TaotaoResult addUserAddress(UserAddress userAddress, HttpServletRequest request) {
		//补全pojo
		userAddress.setCreated(new Date());
		userAddress.setUpdated(new Date());
		//插入收货人地址
		userAddressMapper.insert(userAddress);
		return TaotaoResult.ok();
	}

	@Override
	public List<UserAddress> getUserAddressByUserId(String userId) {
		
		return userAddressMapper.selectAddressByUserId(userId);
	}

}
