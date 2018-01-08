package com.taotao.portal.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.LoginService;
import com.taotao.utils.HttpClientUtil;

@Service
public class LoginServiceImpl implements LoginService {

	@Value("${SSO_BASE_URL}")
	public String SSO_BASE_URL;

	@Value("${TOKEN_URL}")
	private String TOKEN_URL;
	
	@Value("${LOGIN_URL}")
	public String LOGIN_URL;

	@Override
	public TbUser getUserByToken(String token) {
		try {
			// 调用taotao-sso服务
			String resultStr = HttpClientUtil.doGet(SSO_BASE_URL + TOKEN_URL + token);
			if (StringUtils.isNotBlank(resultStr)) {
				TaotaoResult result = TaotaoResult.formatToPojo(resultStr, TbUser.class);
				TbUser user = (TbUser) result.getData();
				return user;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
