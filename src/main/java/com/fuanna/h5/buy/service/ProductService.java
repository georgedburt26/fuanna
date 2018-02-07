package com.fuanna.h5.buy.service;

import java.util.List;
import java.util.Map;

import com.fuanna.h5.buy.model.Category;
import com.fuanna.h5.buy.model.ProductSku;

import net.sf.json.JSONArray;

public interface ProductService {

	public Map<String, String> findProductByBarCode(String barcode, Long companyId);
	
	public List<Category> listCategories();
	
	public List<Map<String, String>> listProduct(List<Long> ids);
	
	public int countProductSku();
	
	public List<ProductSku> stockIn(List<ProductSku> productSkus);
	
	public List<Map<String, Object>> listProductSkuByBarcode(String barcode, String name, String category, Long companyId, Integer offset, Integer limit);
	
	public int countProductSkuByBarcode(String barcode, String name, String category, Long companyId);
	}
