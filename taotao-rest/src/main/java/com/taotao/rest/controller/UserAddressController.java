package com.taotao.rest.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.UserAddress;
import com.taotao.rest.service.UserAddressService;

@Controller
public class UserAddressController {
	
	@Autowired
	private UserAddressService userAddressService;
	
	@RequestMapping(value="/add/user/address", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult addUserAddress(UserAddress userAddress, HttpServletRequest request) {
		TaotaoResult result = userAddressService.addUserAddress(userAddress, request);
		return result;
	}
	
	@RequestMapping("/get/user/address/{userId}")
	@ResponseBody
	public List<UserAddress> getUserAddressByUserId(@PathVariable String userId) {
		List<UserAddress> result = userAddressService.getUserAddressByUserId(userId);
		return result;
	}
}
