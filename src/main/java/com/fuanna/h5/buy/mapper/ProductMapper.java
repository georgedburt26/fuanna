package com.fuanna.h5.buy.mapper;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.fuanna.h5.buy.model.Category;

public interface ProductMapper {

	@Insert({ "insert into f_category(name, createTime) values(#{name}, #{createTime})" })
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
	
	@Select({"select sku.barcode, p.name, sku.skuAttr, c.name as category, skup.normalPrice from f_product_sku as sku left join f_product as p on sku.productId = p.id "
			+ " left join f_category as c on p.category = c.id "
			+ " left join f_sku_price_inventory as skup on skup.barcode = sku.barcode "
			+ " where sku.barcode = #{0} and skup.companyId = #{1}"})
	@ResultType(HashMap.class)
	public Map<String, String> findProductByBarCode(String barcode, Long companyId);
	
	@Select({"select name from f_sku_type where type = #{0}"})
	@ResultType(HashMap.class)
	public Map<String, String> findSkuTypeName(int type);
	
	@Insert({ "INSERT INTO f_product_sku (barcode, updateTime) VALUES (#{barcode}, #{updateTime}) ON DUPLICATE KEY UPDATE updateTime = #{updateTime}" })
	public long addProductSkuBarCode(@Param("barcode")String barcode, @Param("updateTime")Date updateTime);
	
	@Update({"INSERT INTO f_sku_price_inventory (barcode, inventory, companyId, updateTime) VALUES (#{0}, #{1}, #{2}, now()) ON DUPLICATE KEY UPDATE inventory = inventory + #{1}"})
	public long stockIn(String barcode, Integer num, Long companyId);
}
