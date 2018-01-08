package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.EasyUIDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;
import com.taotao.utils.HttpClientUtil;

@Service
public class ContentServiceImpl implements ContentService{
	
	@Autowired
	private TbContentMapper contentMapper;
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	
	@Value("${REQUEST_URL}")
	private String REQUEST_URL;

	/**
	 * 
	 * @Title: getContentList
	 * @Description: 根据分类Id展示展示
	 * @param categoryId
	 * @param total 分页参数
	 * @param rows  分页参数
	 * @return 
	 * @see com.taotao.service.ContentService#getContentList(long, int, int)
	 */
	@Override
	public EasyUIDataGridResult getContentList(long categoryId, int page, int rows) {
		//根据categoryId查询内容列表
		
		//先进行分页
		PageHelper.startPage(page, rows);
		List<TbContent> list = contentMapper.selectByCategoryId(categoryId);
		
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		
		//分页结果返回值
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal(total);
		result.setRows(list);
		
		return result;
	}

	@Override
	public TaotaoResult insertContent(TbContent content) {
		content.setCreated(new Date());
		content.setUpdated(new Date());
		
		contentMapper.insert(content);
		
		//缓存同步
		HttpClientUtil.doGet(REST_BASE_URL + REQUEST_URL + content.getCategoryId());
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult editContent(TbContent content) {
		
		contentMapper.updateByPrimaryKeySelective(content);
		//缓存同步
		HttpClientUtil.doGet(REST_BASE_URL + REQUEST_URL + content.getCategoryId());
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteContents(List<Long> ids) {
		//缓存同步
	    long categoryId = contentMapper.selectById(ids.get(0));
	    contentMapper.deleteContentsByIds(ids);
	    //缓存同步
	  	HttpClientUtil.doGet(REST_BASE_URL + REQUEST_URL + categoryId);
		return TaotaoResult.ok();
	}

}
