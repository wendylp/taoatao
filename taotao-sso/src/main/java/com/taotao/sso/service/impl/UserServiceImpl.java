package com.taotao.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.dao.impl.JedisPoolDao;
import com.taotao.sso.pojo.CartItem;
import com.taotao.sso.service.UserService;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.JsonUtils;

/**
 * 
 * @ClassName: UserServiceImpl
 * @Description: 用户服务接口
 * @author LiuPeng
 * @date 2017年12月14日 下午4:57:34
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper userMapper;

	@Autowired
	private JedisPoolDao jedisPoolDao;

	@Value("${USER_REDIS_KEY_TOKEN}")
	private String USER_REDIS_KEY_TOKEN;

	@Value("${TOKEN_EXPIRE_TIME}")
	private String TOKEN_EXPIRE_TIME;
	
	@Value("${CART_ITEM_KEY}")
	private String CART_ITEM_KEY;

	/**
	 * 
	 * @Title: checkData
	 * @Description: 数据检验
	 * @param param
	 * @param type
	 * @return
	 * @see com.taotao.sso.service.UserService#checkData(java.lang.String,
	 *      java.lang.Integer)
	 */
	@Override
	public TaotaoResult checkData(String param, Integer type) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();

		if (1 == type) {
			criteria.andUsernameEqualTo(param);
		} else if (2 == type) {
			criteria.andPhoneEqualTo(param);
		} else {
			criteria.andEmailEqualTo(param);
		}

		// 执行查询
		List<TbUser> userList = userMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(userList)) {
			return TaotaoResult.ok(true); // 数据可用
		}
		return TaotaoResult.ok(false);
	}

	/**
	 * 
	 * @Title: register
	 * @Description: 注册用户
	 * @param user
	 * @return
	 * @see com.taotao.sso.service.UserService#register(com.taotao.pojo.TbUser)
	 */
	@Override
	public TaotaoResult register(TbUser user) {
		// 补全pojo
		user.setCreated(new Date());
		user.setUpdated(new Date());

		// 密码MD5加密处理 Spring框架自带的工具类
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		userMapper.insert(user);
		return TaotaoResult.ok();
	}

	/**
	 * 
	 * @Title: login
	 * @Description: 用户登录
	 * @param username
	 * @param password
	 * @return
	 * @see com.taotao.sso.service.UserService#login(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public TaotaoResult login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		// 只需根据用户名查询数据库
		criteria.andUsernameEqualTo(username);

		List<TbUser> userList = userMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(userList)) {
			return TaotaoResult.build(400, "用户名或密码错误！");
		}

		// 在检查密码
		TbUser user = userList.get(0);  //前面已经根据用户名查询出了用户且用户名不能够重复 实际该集合中只有一个元素  所以可直接取第一个元素来判断
		if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
			return TaotaoResult.build(400, "用户名或密码错误！");
		}

		String token = UUID.randomUUID().toString();//生成token
		// 清空用户密码
		user.setPassword(null);
		// 将用户信息存入redis并设置过期时间 1天
		jedisPoolDao.setex(USER_REDIS_KEY_TOKEN + ":" + token, Integer.valueOf(TOKEN_EXPIRE_TIME),
				JsonUtils.objectToJson(user));
		
		//设置cookie
		CookieUtils.setCookie(request, response, "TT_TOKEN", token);
		
		//用户登录以后需合并cookie中跟数据库中已经购买的商品
		//获取cookie已经添加的商品
		String cookieCart = CookieUtils.getCookieValue(request, "TT_CART_ITEM", true);
		List<CartItem> cookieCartList = JsonUtils.jsonToList(cookieCart, CartItem.class);
		//从redis中取保存的购物车商品
		String redisCart = jedisPoolDao.get(CART_ITEM_KEY);
		if(StringUtils.isBlank(redisCart)) {
			//直接将cookie中购物车存入redis
			jedisPoolDao.set(CART_ITEM_KEY, cookieCart);
		} else {
			
			List<CartItem> redisCartList = JsonUtils.jsonToList(redisCart, CartItem.class);
			//循环cookie中的购车商品
			for (CartItem cookieCartItem : cookieCartList) {
				if(redisCartList.contains(cookieCartItem)) { //cookie中包含reids中的购物车商品
					//直接相加商品数量
					int index = redisCartList.indexOf(cookieCartItem);
					CartItem redisCartItem = redisCartList.get(index);
					redisCartItem.setNum(redisCartItem.getNum() + cookieCartItem.getNum());
				} else { //cookie中不包含redis中的商品
					//直接放入redis中即可
					redisCartList.add(cookieCartItem);
				}
			}
			//循环完之后更新redis
			jedisPoolDao.set(CART_ITEM_KEY, JsonUtils.objectToJson(redisCartList));
		}
		
		//清除cookie中的购物车
		CookieUtils.deleteCookie(request, response, "TT_CART_ITEM");
		
		return TaotaoResult.ok(token);
	}

	/**
	 * 
	 * @Title: getUserByToken
	 * @Description: 根据token取用户的信息
	 * @param token
	 * @return
	 * @see com.taotao.sso.service.UserService#getUserByToken(java.lang.String)
	 */
	@Override
	public TaotaoResult getUserByToken(String token) {

		String result = jedisPoolDao.get(USER_REDIS_KEY_TOKEN + ":" + token);
		if (StringUtils.isEmpty(result)) {
			return TaotaoResult.build(400, "请重新登录！");
		}
		// 更新token过期时间
		jedisPoolDao.expire(USER_REDIS_KEY_TOKEN + ":" + token, Integer.valueOf(TOKEN_EXPIRE_TIME));
		return TaotaoResult.ok(JsonUtils.jsonToPojo(result, TbUser.class));

	}

	/**
	 * 
	 * @Title: logout
	 * @Description: 退出登录   删除用户信息
	 * @param token
	 * @return 
	 * @see com.taotao.sso.service.UserService#logout(java.lang.String)
	 */
	@Override
	public TaotaoResult logout(String token) {
		//根据token删除用户信息
		jedisPoolDao.del(USER_REDIS_KEY_TOKEN + ":" + token);
		return TaotaoResult.ok();
	}
}
