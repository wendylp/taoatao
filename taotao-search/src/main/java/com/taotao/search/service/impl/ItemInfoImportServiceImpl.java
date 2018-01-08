package com.taotao.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.pojo.TaotaoResult;
import com.taotao.search.mapper.ItemInfoMapper;
import com.taotao.search.pojo.ItemInfo;
import com.taotao.search.service.ItemInfoImportService;

@Service
public class ItemInfoImportServiceImpl implements ItemInfoImportService{

	@Autowired
	private ItemInfoMapper itemInfoMapper;
	
	@Autowired
	private SolrServer solrServer;
	
	@Override
	public TaotaoResult importItemInfoToSolr() throws Exception {
		
		//查询数据库获取商品信息列表
		List<ItemInfo> itemInfoList = itemInfoMapper.getItemInfoList();
		//循环添加到solr中
		for (ItemInfo itemInfo : itemInfoList) {
			SolrInputDocument solrInputDocument = new SolrInputDocument();
			
			solrInputDocument.addField("id", itemInfo.getId());
			solrInputDocument.addField("item_title", itemInfo.getTitle());
			solrInputDocument.addField("item_sell_point", itemInfo.getSellPoint());
			solrInputDocument.addField("item_price", itemInfo.getPrice());
			solrInputDocument.addField("item_image", itemInfo.getImage());
			solrInputDocument.addField("item_category_name", itemInfo.getCategoryName());
			
			solrServer.add(solrInputDocument);
		}
		//提交
		solrServer.commit();
		return TaotaoResult.ok();
	}

}
