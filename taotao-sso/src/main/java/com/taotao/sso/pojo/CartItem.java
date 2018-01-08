package com.taotao.sso.pojo;

/**
 * 
 * @ClassName: CartItem
 * @Description: 购物车商品信息
 * @author LiuPeng
 * @date 2017年12月16日 下午12:34:05
 *
 */
public class CartItem {
	private long id;
	private String title;
	private long price;
	private String image;
	private int num; // 商品数量

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
