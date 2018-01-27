package com.fuanna.h5.buy.mapper;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.fuanna.h5.buy.model.Category;

public interface ProductMapper {

	@Insert({ "insert into t_category(name, createTime) values(#{name}, #{createTime})" })
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public long addCategory(Category category);

	@Select({ "select * from f_category" })
	public List<Category> listCategories();

	@Select({ "<script>" +
			"select CONCAT(p.name,' ',ifnull(sku.skuName, '')) as name, IFNULL(sku.promotionPrice,sku.normalPrice) as price from f_product as p left join f_product_sku as sku on sku.productId = p.id where 1 = 1 " +
			"<if test=' ids != null '>" +
			"and p.id in " +
			"<foreach collection='ids' index='index' item='item' open='(' separator=',' close=')'>" +  
            "#{item} " + 
            "</foreach> " +
            "</if>" +
            "group by p.id " + 
			"</script>" })
	@ResultType(LinkedHashMap.class)
	public List<Map<String, String>> listProduct(@Param("ids")List<Long> ids);
	
	@Select({"select count(a.id) from (select p.id from f_product as p left join f_product_sku as sku on sku.productId = p.id group by p.id) as a"})
	public int countProductSku();
	
	@Select({"select sku.barcode, p.name, sku.skuAttr, sku.normalPrice, c.name as category from f_product_sku as sku left join f_product as p on sku.productId = p.id left join f_category as c on p.category = c.id where sku.barcode = #{0}"})
	@ResultType(HashMap.class)
	public Map<String, String> findProductByBarCode(String barcode);
	
	@Select({"select name from f_sku_type where type = #{0}"})
	@ResultType(HashMap.class)
	public Map<String, String> findSkuTypeName(int type);
}
