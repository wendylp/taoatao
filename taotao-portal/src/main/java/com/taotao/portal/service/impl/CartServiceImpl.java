package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.portal.dao.impl.JedisPoolDao;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.service.CartService;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.HttpClientUtil;
import com.taotao.utils.JsonUtils;

@Service
public class CartServiceImpl implements CartService {

	@Value("${ITEM_INFO_URL}")
	private String ITEM_INFO_URL;

	@Value("${SSO_BASE_URL}")
	private String SSO_BASE_URL;

	@Value("${TOKEN_URL}")
	private String TOKEN_URL;

	@Value("${CART_ITEM_KEY}")
	private String CART_ITEM_KEY;

	@Autowired
	private JedisPoolDao jedisPoolDao;

	/**
	 * 
	 * @Title: addCartItem
	 * @Description: 添加购物车
	 * @param itemId
	 * @param num
	 * @param request
	 * @param response
	 * @return
	 * @see com.taotao.portal.service.CartService#addCartItem(long, int,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public TaotaoResult addCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response) {
		// 从cookie中取用户信息
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");

		if (StringUtils.isBlank(token)) { // 用户未登录
			// 先从cookie取商品信息列表
			List<CartItem> cartItemList = getCartItemList(request);

			if (CollectionUtils.isNotEmpty(cartItemList)) {
				for (CartItem cartItem : cartItemList) {
					if (cartItem.getId() == itemId) { // 购物车中商品已经存在
						cartItem.setNum(cartItem.getNum() + num); // 修改数量
						// 更新cookie
						CookieUtils.setCookie(request, response, "TT_CART_ITEM", JsonUtils.objectToJson(cartItemList),
								true);
						return TaotaoResult.ok(); // 直接结束
					}
				}
				// 循环完则证明没有相同商品
				// 调用taotao-rest查询商品加入cookie
				String resultStr = HttpClientUtil.doGet(ITEM_INFO_URL + itemId);
				TaotaoResult result = TaotaoResult.formatToPojo(resultStr, TbItem.class);
				TbItem item = (TbItem) result.getData();
				// 转化为CartItem
				CartItem cartItem = new CartItem();
				cartItem.setId(item.getId());
				cartItem.setTitle(item.getTitle());
				cartItem.setPrice(item.getPrice());
				cartItem.setNum(num);

				// 处理图片
				ItemInfo itemInfo = new ItemInfo();
				itemInfo.setImage(item.getImage());
				cartItem.setImage(itemInfo.getImages()[0]);

				cartItemList.add(cartItem);
				// 更新cookie
				CookieUtils.setCookie(request, response, "TT_CART_ITEM", JsonUtils.objectToJson(cartItemList), true);
				return TaotaoResult.ok();

			} else {
				// 调用taotao-rest查询商品加入cookie
				String resultStr = HttpClientUtil.doGet(ITEM_INFO_URL + itemId);
				TaotaoResult result = TaotaoResult.formatToPojo(resultStr, TbItem.class);
				TbItem item = (TbItem) result.getData();
				// 转化为CartItem
				CartItem cartItem = new CartItem();
				cartItem.setId(item.getId());
				cartItem.setTitle(item.getTitle());
				cartItem.setPrice(item.getPrice());
				cartItem.setNum(num);

				// 处理图片
				ItemInfo itemInfo = new ItemInfo();
				itemInfo.setImage(item.getImage());
				cartItem.setImage(itemInfo.getImages()[0]);

				// 创建新的集合
				cartItemList = new ArrayList<>();
				cartItemList.add(cartItem);
				// 直接加入cookie
				CookieUtils.setCookie(request, response, "TT_CART_ITEM", JsonUtils.objectToJson(cartItemList), true);

				return TaotaoResult.ok();
			}

		} else { // 用戶已登录
			// 先从redis中取购物车商品信息
			String redisCart = jedisPoolDao.get(CART_ITEM_KEY);
			if (StringUtils.isNotBlank(redisCart)) { //redis中丢数据

				List<CartItem> cartItemList = JsonUtils.jsonToList(redisCart, CartItem.class);
				for (CartItem cartItem : cartItemList) {
					if (cartItem.getId() == itemId) { // 购物车中商品已经存在
						cartItem.setNum(cartItem.getNum() + num); // 修改数量
						// 更新redis
						jedisPoolDao.set(CART_ITEM_KEY, JsonUtils.objectToJson(cartItemList));
						return TaotaoResult.ok(); // 直接结束
					}
				}
				// 循环完则证明没有相同商品
				// 调用taotao-rest查询商品加入redis
				String resultStr = HttpClientUtil.doGet(ITEM_INFO_URL + itemId);
				TaotaoResult result = TaotaoResult.formatToPojo(resultStr, TbItem.class);
				TbItem item = (TbItem) result.getData();
				// 转化为CartItem
				CartItem cartItem = new CartItem();
				cartItem.setId(item.getId());
				cartItem.setTitle(item.getTitle());
				cartItem.setPrice(item.getPrice());
				cartItem.setNum(num);

				// 处理图片
				ItemInfo itemInfo = new ItemInfo();
				itemInfo.setImage(item.getImage());
				cartItem.setImage(itemInfo.getImages()[0]);

				cartItemList.add(cartItem);
				// 更新redis
				jedisPoolDao.set(CART_ITEM_KEY, JsonUtils.objectToJson(cartItemList));
			}
			return TaotaoResult.ok();
		}
	}

	/**
	 * 
	 * @Title: getCartItemList @Description: 从cookie中获取商品信息 @param request @param
	 * response @return List<CartItem> @throws
	 */
	private List<CartItem> getCartItemList(HttpServletRequest request) {
		// 判断用户是否登录
		// 从cookie中取用户信息
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		if (StringUtils.isBlank(token)) {
			String resultStr = CookieUtils.getCookieValue(request, "TT_CART_ITEM", true);
			if (StringUtils.isNotBlank(resultStr)) {
				List<CartItem> result = JsonUtils.jsonToList(resultStr, CartItem.class);
				return result;
			}
			return null;
		} else {
			// 从redis中取购物车商品信息
			String resultStr = jedisPoolDao.get(CART_ITEM_KEY);
			List<CartItem> result = JsonUtils.jsonToList(resultStr, CartItem.class);
			return result;
		}
	}

	/**
	 * 
	 * @Title: getAllCartItem
	 * @Description: 获取购物车中商品信息列表
	 * @param request
	 * @return
	 * @see com.taotao.portal.service.CartService#getAllCartItem(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public List<CartItem> getAllCartItem(HttpServletRequest request) {
		return getCartItemList(request);
	}

	/**
	 * 
	 * @Title: deleteCartItem
	 * @Description: 生出购物车的某个商品
	 * @param itemId
	 * @param request
	 * @param response
	 * @see com.taotao.portal.service.CartService#deleteCartItem(long,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public TaotaoResult deleteCartItem(long itemId, HttpServletRequest request, HttpServletResponse response) {
		// 从cookie或redis中取购物车商品信息
		List<CartItem> cartItemList = getCartItemList(request);
		for (CartItem cartItem : cartItemList) {
			if (cartItem.getId() == itemId) {
				cartItemList.remove(cartItem);// 删除当前商品
				break; // 直接结束循环
			}
		}
		// 判断用户是否登录
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		if (StringUtils.isBlank(token)) { // 未登录放cookie
			// 循环完成之后再放入cookie
			CookieUtils.setCookie(request, response, "TT_CART_ITEM", JsonUtils.objectToJson(cartItemList), true);
		} else { // 已登录放redis
			jedisPoolDao.set(CART_ITEM_KEY, JsonUtils.objectToJson(cartItemList));
		}
		return TaotaoResult.ok();
	}

	/**
	 * 
	 * @Title: updateCartItem
	 * @Description: 直接修改购物车中的商品
	 * @param itemId
	 * @param num
	 * @param request
	 * @param response
	 * @return
	 * @see com.taotao.portal.service.CartService#updateCartItem(long, int,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public TaotaoResult updateCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response) {
		// 从cookie或redis中取出商品
		List<CartItem> cartItemList = getCartItemList(request);
		for (CartItem cartItem : cartItemList) {
			if (cartItem.getId() == itemId) {
				// 修改数量
				cartItem.setNum(cartItem.getNum() + num);
				break; // 结束循环
			}
		}

		// 判断用户是否登录
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		if (StringUtils.isBlank(token)) { // 未登录放cookie
			// 循环完成之后再放入cookie
			CookieUtils.setCookie(request, response, "TT_CART_ITEM", JsonUtils.objectToJson(cartItemList), true);
		} else { // 已登录放redis
			jedisPoolDao.set(CART_ITEM_KEY, JsonUtils.objectToJson(cartItemList));
		}
		return TaotaoResult.ok();
	}

}
