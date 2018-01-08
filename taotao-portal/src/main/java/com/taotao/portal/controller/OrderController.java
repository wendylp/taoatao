package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.pojo.Order;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.UserAddress;
import com.taotao.portal.dao.impl.JedisPoolDao;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.OrderService;
import com.taotao.portal.service.UserAddressService;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.JsonUtils;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private JedisPoolDao jedisPoolDao;
	
	@Value("${CART_ITEM_KEY}")
	private String CART_ITEM_KEY;
	
	@Autowired
	private UserAddressService userAddressService;
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/order-cart")
	public String toOrderPage(HttpServletRequest request, HttpServletResponse response, Model model) {
		//从redis中取商品信息
		String resultStr = jedisPoolDao.get(CART_ITEM_KEY);
		List<CartItem> cartItemList = JsonUtils.jsonToList(resultStr, CartItem.class);
		
		//根据userId(token)取用户地址
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		List<UserAddress> addressList = userAddressService.getUserAddressByUserId(token, request);
		
		model.addAttribute("cartList", cartItemList);
		model.addAttribute("addressList", addressList);
		return "/order-cart";
	}
	
	@RequestMapping("/user/address")
	public String toAddUserAddress() {
		return "/user_address";
		
	}
	
	
	@RequestMapping("/create")
	public String createOrder(Order order, Model model, HttpServletRequest request) {
		//从request域中取用户信息
		TbUser user = (TbUser) request.getAttribute("user");
		order.setUserId(user.getId());
		order.setBuyerNick(user.getUsername());
		
		String result = orderService.createOrder(order);
		model.addAttribute("orderId", result);
		model.addAttribute("payment", order.getPayment());
		model.addAttribute("date", new DateTime().plusDays(3).toString());
		
		return "/success";
	}

}
