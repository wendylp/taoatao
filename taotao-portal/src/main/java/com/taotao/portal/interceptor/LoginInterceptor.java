package com.taotao.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.pojo.TbUser;
import com.taotao.portal.service.impl.LoginServiceImpl;
import com.taotao.utils.CookieUtils;

public class LoginInterceptor implements HandlerInterceptor{
	
	@Autowired
	private LoginServiceImpl loginService;
	
	//handler方法执行之前执行
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//根据token取用户信息
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		//如果用户信息为空则调用sso服务进行登录，登录完之后再返回之前的页面，然后拦截
		TbUser user = loginService.getUserByToken(token);
		if(null == user) {
			response.sendRedirect(loginService.SSO_BASE_URL + loginService.LOGIN_URL + "?redirectUrl=" + request.getRequestURL());
			return false;
		}
		//如果用户不为空则直接放行
		//将用户信息放入request域中
		request.setAttribute("user", user);
		return true;
	}

	//handler方法执行之后ModelAndView之前
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	}

	//handler方法执行之后
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

}
