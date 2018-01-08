package com.taotao.sso.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转的Controller
 * @ClassName: PageController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author LiuPeng
 * @date 2017年12月15日 下午3:33:35
 *
 */
@Controller
public class PageController {
	
	@RequestMapping("/user/showLogin")
	public String showLogin(String redirectUrl, Map<String, String> map) {
		map.put("redirect", redirectUrl);
		return "/login";
	}

}
