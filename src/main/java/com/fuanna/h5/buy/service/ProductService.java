package com.fuanna.h5.buy.service;

import java.util.List;
import java.util.Map;

import com.fuanna.h5.buy.model.Category;

public interface ProductService {

	public List<Category> listCategories();
	
	public List<Map<String, String>> listProduct(List<Long> ids);
	
	public int countProductSku();
	}
