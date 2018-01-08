package com.taotao.search.pojo;

import java.util.List;

/**
 * 
 * @ClassName: SearchResult
 * @Description: 封装搜索结果
 * @author LiuPeng
 * @date 2017年12月12日 下午5:57:21
 *
 */
public class SearchResult {
	// 商品信息
	private List<ItemInfo> itemInfoList;
	// 总的记录条数
	private long totalRecord;
	// 当前页码
	private long currentPage;
	// 总页数
	private long totalPage;

	public List<ItemInfo> getItemInfoList() {
		return itemInfoList;
	}

	public void setItemInfoList(List<ItemInfo> itemInfoList) {
		this.itemInfoList = itemInfoList;
	}

	public long getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}

}
