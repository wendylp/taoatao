package com.taotao.portal.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.pojo.UserAddress;
import com.taotao.portal.service.UserAddressService;

@Controller
public class UserAddressController {
	
	@Autowired
	private UserAddressService userAddressService;
	
	@RequestMapping(value="/user/add", method=RequestMethod.POST)
	public String addUserAddress(UserAddress userAddress, HttpServletRequest request) {
		userAddressService.insertUserAddress(userAddress, request);
		return "/index";
	}
}
