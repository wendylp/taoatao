package com.taotao.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.UserAddress;

public interface UserAddressService {
	TaotaoResult insertUserAddress(UserAddress userAddress, HttpServletRequest request);
	List<UserAddress> getUserAddressByUserId(String userId, HttpServletRequest request);
}
