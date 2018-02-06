package com.fuanna.h5.buy.model;

import java.io.Serializable;
import java.util.Date;

public class ProductSku implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private String productName;
	
	private String category;
	
	private Long productId;
	
	private String barcode;
	
	private String skuAttr;
	
	private String description;
	
	private Date updateTime;
	
	private Double normalPrice;
	
	private Double promotionPrice;
	
	private Integer inventory;
	
	private Long companyId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getSkuAttr() {
		return skuAttr;
	}

	public void setSkuAttr(String skuAttr) {
		this.skuAttr = skuAttr;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Double getNormalPrice() {
		return normalPrice;
	}

	public void setNormalPrice(Double normalPrice) {
		this.normalPrice = normalPrice;
	}

	public Double getPromotionPrice() {
		return promotionPrice;
	}

	public void setPromotionPrice(Double promotionPrice) {
		this.promotionPrice = promotionPrice;
	}

	public Integer getInventory() {
		return inventory;
	}

	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
}
