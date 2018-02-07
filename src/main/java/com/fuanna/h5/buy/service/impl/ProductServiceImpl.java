package com.fuanna.h5.buy.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fuanna.h5.buy.mapper.ProductMapper;
import com.fuanna.h5.buy.model.Category;
import com.fuanna.h5.buy.model.ProductSku;
import com.fuanna.h5.buy.service.ProductService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component
public class ProductServiceImpl implements ProductService {

	private static final Logger logger = Logger
			.getLogger(ProductServiceImpl.class);

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

	@Override
	@Transactional
	public Map<String, String> findProductByBarCode(String barcode,
			Long companyId) {
		productMapper.addProductSkuBarCode(barcode, new Date());
		Map<String, String> productMap = productMapper.findProductByBarCode(
				barcode, companyId);
		if (StringUtils.isNotBlank(productMap.get("name"))
				&& StringUtils.isNotBlank(productMap.get("skuAttr"))) {
			String productName = productMap.get("name");
			JSONArray array = JSONArray.fromObject(productMap.get("skuAttr"));
			Iterator itr = array.iterator();
			while (itr.hasNext()) {
				JSONObject object = (JSONObject) itr.next();
				productName += " " + object.getString("value");
			}
			productMap.put("name", productName);
		}
		return productMap;
	}

	@Override
	public List<ProductSku> stockIn(List<ProductSku> productSkus) {
		List<ProductSku> fails = new ArrayList<ProductSku>();
		for (ProductSku productSku : productSkus) {
			if (productMapper.stockIn(productSku.getBarcode(),
					productSku.getInventory(), productSku.getCompanyId()) <= 0) {
				fails.add(productSku);
			}
		}
		return fails;
	}

	@Override
	public List<Map<String, Object>> listProductSkuByBarcode(String barcode,
			String name, String category, Long companyId, Integer offset,
			Integer limit) {
		List<Map<String, Object>> rows = productMapper.listProductSkuByBarcode(
				barcode, name, category, companyId, offset, limit);
		for (Map<String, Object> row : rows) {
			String attribute = row.get("attribute") + "";
			StringBuffer attributeValues = new StringBuffer();
			if (StringUtils.isNotBlank(attribute)) {
				JSONArray array = JSONArray.fromObject(attribute);
				Iterator<JSONObject> itr = array.iterator();
				while (itr.hasNext()) {
					JSONObject object = itr.next();
					String typeName = productMapper.querySkuTypeName(
							object.getInt("type")).get("name");
					String seperate = StringUtils.isBlank(attributeValues.toString()) ? "" : "|";
					attributeValues.append(seperate).append(typeName).append(":").append(object.getString("value"));
				}
			}
			row.put("attribute", attributeValues.toString());
		}
		return rows;
	}

	@Override
	public int countProductSkuByBarcode(String barcode, String name,
			String category, Long companyId) {
		return productMapper.countProductSkuByBarcode(barcode, name, category,
				companyId);
	}
}
