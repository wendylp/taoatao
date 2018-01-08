package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.EasyUITreeNode;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.ContentCatService;

@Service
public class ContentCatServiceImpl implements ContentCatService {

	@Autowired
	private TbContentCategoryMapper contentCatMapper;

	/**
	 * 
	 * @Title: getContentCatList
	 * @Description: 根据parentId查询内容列表
	 * @param parentId
	 * @return
	 * @see com.taotao.service.ContentCatService#getContentCatList(long)
	 */
	@Override
	public List<EasyUITreeNode> getContentCatList(long parentId) {
		// 根据parentId查询内容列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);

		List<TbContentCategory> list = contentCatMapper.selectByExample(example);
		List<EasyUITreeNode> resultList = new ArrayList<>();
		for (TbContentCategory contentCat : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(contentCat.getId());
			node.setText(contentCat.getName());
			node.setState(contentCat.getIsParent() ? "closed" : "open");

			resultList.add(node);
		}
		return resultList;
	}

	/**
	 * 
	 * @Title: insertContentCat
	 * @Description: 添内容分类
	 * @param parentId
	 * @param name
	 * @return
	 * @see com.taotao.service.ContentCatService#insertContentCat(long,
	 *      java.lang.String)
	 */
	@Override
	public TaotaoResult insertContentCat(long parentId, String name) {
		// 获取有自增主键
		TbContentCategory contentCat = new TbContentCategory();
		contentCat.setParentId(parentId);
		contentCat.setName(name);
		contentCat.setStatus(1);
		contentCat.setSortOrder(1);
		contentCat.setIsParent(false); // 新加入的节点一定不是父节点
		contentCat.setCreated(new Date());
		contentCat.setUpdated(new Date());

		contentCatMapper.insert(contentCat);
		// 插入子节点后修改父节点状态
		TbContentCategory parentContentCat = contentCatMapper.selectByPrimaryKey(parentId);
		if (!parentContentCat.getIsParent()) { // 如果父节点状态不是父节点 修改其状态
			parentContentCat.setIsParent(true);

			contentCatMapper.updateByPrimaryKey(parentContentCat);
		}
		return TaotaoResult.ok(contentCat);
	}

	/**
	 * 
	 * @Title: deleteContentCat
	 * @Description: 删除内容分类
	 * @param parentId
	 * @param id
	 * @return
	 * @see com.taotao.service.ContentCatService#deleteContentCat(long, long)
	 */
	@Override
	public TaotaoResult deleteContentCat(long parentId, long id) {
		// 根据id删除内容分类
		contentCatMapper.deleteByPrimaryKey(id);
		// 根据parentId查询父类节点下面是否有子节点 修改is_parent
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);

		int count = contentCatMapper.countByExample(example);
		if (count == 0) {
			TbContentCategory contentCat = new TbContentCategory();
			contentCat.setId(parentId);
			contentCat.setIsParent(false);
			contentCatMapper.updateByPrimaryKeySelective(contentCat);
		}

		return TaotaoResult.ok();
	}

	/**
	 * 
	 * @Title: updateContentCat
	 * @Description: 更新内容分类
	 * @param id
	 * @param name
	 * @return 
	 * @see com.taotao.service.ContentCatService#updateContentCat(long, java.lang.String)
	 */
	@Override
	public TaotaoResult updateContentCat(long id, String name) {
		TbContentCategory contentCat = new TbContentCategory();
		contentCat.setId(id);
		contentCat.setName(name);
		contentCat.setUpdated(new Date());
		
		contentCatMapper.updateByPrimaryKeySelective(contentCat);
		return TaotaoResult.ok();
	}

}
