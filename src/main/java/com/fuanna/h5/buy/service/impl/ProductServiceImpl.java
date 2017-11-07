package com.fuanna.h5.buy.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fuanna.h5.buy.mapper.ProductMapper;
import com.fuanna.h5.buy.model.Category;
import com.fuanna.h5.buy.service.ProductService;

@Component
public class ProductServiceImpl implements ProductService{

	@Autowired
	ProductMapper productMapper;
	
	@Override
	public List<Category> listCategories() {
		return productMapper.listCategories();
	}

	@Override
	public List<Map<String, String>> listProduct(List<Long> ids) {
		return productMapper.listProduct(ids);
	}

	@Override
	public int countProductSku() {
		return productMapper.countProductSku();
	}

}
