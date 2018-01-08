package com.taotao.search.pojo;

/**
 * 
 * @ClassName: ItemInfo
 * @Description: 导入索引库封装的pojo
 * @author LiuPeng
 * @date 2017年12月12日 下午1:39:46
 *
 */
public class ItemInfo {

	private long id;
	private String title;
	private long price;
	private String sellPoint;
	private String image;
	private String categoryName;
	private String itemDesc;

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

	public String getSellPoint() {
		return sellPoint;
	}

	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

}
