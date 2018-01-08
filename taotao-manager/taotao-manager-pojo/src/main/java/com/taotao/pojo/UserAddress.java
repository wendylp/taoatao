package com.taotao.pojo;

import java.util.Date;

/**
 * 
 * @ClassName: UserAddress
 * @Description: 用户信息
 * @author LiuPeng
 * @date 2017年12月21日 上午12:15:54
 *
 */
public class UserAddress {
	private long id;
	private String userId;
	private String username;
	private String phone;
	private String address;
	private byte isDefault;
	private Date created;
	private Date updated;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public byte getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(byte isDefault) {
		this.isDefault = isDefault;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

}
