package com.fuanna.h5.buy.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fuanna.h5.buy.mapper.ProductMapper;
import com.fuanna.h5.buy.model.Category;
import com.fuanna.h5.buy.service.ProductService;

@Component
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductMapper productMapper;

	@Override
	public List<Category> listCategories() {
		return productMapper.listCategories();
	}

	@Override
	public List<Map<String, String>> listProduct(List<Long> ids) {
		List<Map<String, String>> list = productMapper.listProduct(ids);
		for (Map<String, String> map : list) {
			formatProductName(map);
		}
		return list;
	}

	@Override
	public int countProductSku() {
		return productMapper.countProductSku();
	}

	private Map<String, String> formatProductName(Map<String, String> map) {
		if (!map.isEmpty() && StringUtils.isNotBlank(map.get("name"))) {
			String name = map.get("name");
			if (name.length() > 13) {
				map.put("name", name.substring(0, 13) + "...");
			}
		}
		return map;
	}
}
