package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@RequestMapping("/add/{itemId}")
	public String addCartItem(@PathVariable long itemId, @RequestParam(defaultValue="1") int num, 
									HttpServletRequest request, HttpServletResponse response) {
		cartService.addCartItem(itemId, num, request, response);
		//需要转发   避免重复提交
		return "redirect:/cart/success.html";
	}
	
	@RequestMapping("/success")
	public String toCartSuccessPage() {
		return "/cart_success";
		
	}
	
	@RequestMapping("/cart")
	public String showCartList(HttpServletRequest request, Model model) {
		List<CartItem> allCartItem = cartService.getAllCartItem(request);
		model.addAttribute("cartList", allCartItem);
		return "/cart";
	}
	
	@RequestMapping("/delete/{itemId}")
	public String deleteCartItem(@PathVariable long itemId, HttpServletRequest request, HttpServletResponse response) {
		cartService.deleteCartItem(itemId, request, response);
		return "redirect:/cart/cart.html"; //转发到购物车列表页面
	}
	
	@RequestMapping(value="/update/num/{itemId}/{num}", method=RequestMethod.POST)
	public String updateCartItem(@PathVariable long itemId, @PathVariable int num, HttpServletRequest request, HttpServletResponse response) {
		cartService.updateCartItem(itemId, num, request, response);
		return "redirect:/cart/cart.html"; //转发到购物车列表页面
	}
	
	@RequestMapping("/show")
	public String showCart() {
		return "redirect:/cart/cart.html";
	}
}
