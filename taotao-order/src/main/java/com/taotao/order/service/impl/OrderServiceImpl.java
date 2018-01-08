package com.taotao.order.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.dao.impl.JedisPoolDao;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private TbOrderMapper orderMapper;
	
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	
	@Autowired
	private TbOrderShippingMapper orderShippingMapper;
	
	@Value("${ORDER_GEN_ID_KEY}")
	private String ORDER_GEN_ID_KEY;
	
	@Value("${ORDER_INIT_ID}")
	private String ORDER_INIT_ID;
	
	@Value("${ORDER_DETAIL_GEN_ID_KEY}")
	private String ORDER_DETAIL_GEN_ID_KEY;
	
	@Autowired
    private JedisPoolDao jedisPoolDao;
	/**
	 * 
	 * @Title: createOrder
	 * @Description: 创建订单
	 * @param order
	 * @return 
	 * @see com.taotao.order.service.OrderService#createOrder(com.taotao.order.pojo.Order)
	 */
	@Override
	public TaotaoResult createOrder(TbOrder order, List<TbOrderItem> orderItem, TbOrderShipping orderShipping) {
		//先从redis中取orderId
		String string = jedisPoolDao.get(ORDER_GEN_ID_KEY); 
		if(StringUtils.isBlank(string)) {
			jedisPoolDao.set(ORDER_GEN_ID_KEY, ORDER_INIT_ID);
		}
		
		Long orderId = jedisPoolDao.incr(ORDER_GEN_ID_KEY); //每创建一个订单  订单号加一
		//补全order信息
		order.setOrderId(orderId + ""); 
		order.setStatus(1); //状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
		order.setBuyerRate(0);  //0：未评价    1：已评价
		Date date = new Date();
		order.setCreateTime(date);
		order.setUpdateTime(date);
		
		//插入订单
		orderMapper.insert(order);
		
		for (TbOrderItem tbOrderItem : orderItem) { //循环插入订单商品详情
			//补全orderItem
			Long orderItemId = jedisPoolDao.incr(ORDER_DETAIL_GEN_ID_KEY); //redis incr命令  当key没有设置值时默认为0
			tbOrderItem.setId(orderItemId + "");
			tbOrderItem.setOrderId(orderId + "");
			
			//循环插入订单商品信息
			orderItemMapper.insert(tbOrderItem);
		}
		
		//补全orderShipping
		orderShipping.setOrderId(orderId + "");
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		
		orderShippingMapper.insert(orderShipping);
		//返回订单号
		return TaotaoResult.ok(orderId);
	}

}
