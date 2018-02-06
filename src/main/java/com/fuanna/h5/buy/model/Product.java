package com.fuanna.h5.buy.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Product implements Serializable{

	/**
	 * 
	 */	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String name;
	private String description;
	private long category;
	private int status;//1上架 2下架
	private String material;
	private Date createTime;
	private List<ProductSku> productSkus = new ArrayList<ProductSku>();
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getCategory() {
		return category;
	}
	public void setCategory(long category) {
		this.category = category;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public List<ProductSku> getProductSkus() {
		return productSkus;
	}
	public void setProductSkus(List<ProductSku> productSkus) {
		this.productSkus = productSkus;
	}
	
}
