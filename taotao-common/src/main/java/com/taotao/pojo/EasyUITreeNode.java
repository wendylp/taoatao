package com.taotao.pojo;

/**
 * 
 * @ClassName: EasyUITreeNode
 * @Description: 添加商品时商品分类条目
 * @author LiuPeng
 * @date 2017年12月3日 上午10:33:42
 *
 */
public class EasyUITreeNode {

	private long id;
	private String text;
	private String state;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
