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
	public Map<String, String> querySkuTypeName(int type);
	
	@Insert({ "INSERT INTO f_product_sku (barcode, updateTime) VALUES (#{barcode}, #{updateTime}) ON DUPLICATE KEY UPDATE updateTime = #{updateTime}" })
	public long addProductSkuBarCode(@Param("barcode")String barcode, @Param("updateTime")Date updateTime);
	
	@Update({"INSERT INTO f_sku_price_inventory (barcode, inventory, companyId, updateTime) VALUES (#{0}, #{1}, #{2}, now()) ON DUPLICATE KEY UPDATE inventory = inventory + #{1}"})
	public long stockIn(String barcode, Integer num, Long companyId);
	
	@Select({ "<script>"
			+ "select ifnull(p.name,'') as name, sku.barcode, ifnull(c.`name`,'') as category, ifnull(sku.skuAttr,'') as attribute, ifnull(skup.normalPrice,'') as price, ifnull(skup.inventory, 0) as inventory  from f_product_sku as sku LEFT JOIN f_product as p on p.id = sku.productId left join f_category as c on p.category = c.id left join f_sku_price_inventory as skup on sku.barcode = skup.barcode where 1 = 1 "
			+ "<if test='barcode != null'>" + " and sku.barcode = #{barcode} " + "</if>"
			+ "<if test='name != null'>" + " and p.name like CONCAT('%',#{name},'%') " + "</if>"
			+ "<if test='category != null'>" + " and c.id = #{category} " + "</if>"
			+ "<if test='companyId != null'>" + " and skup.companyId = #{companyId} " + "</if>"
			+ " order by sku.id desc " + "<if test='offset != null and limit != null'>"
			+ " limit #{offset}, #{limit} " + "</if>" + "</script>" })
	public List<Map<String, Object>> listProductSkuByBarcode(@Param("barcode")String barcode, @Param("name")String name, @Param("category")String category, @Param("companyId")Long companyId,
			@Param("offset")Integer offset, @Param("limit")Integer limit);
	
	@Select({ "<script>"
			+ "select count(sku.id) from f_product_sku as sku LEFT JOIN f_product as p on p.id = sku.productId left join f_category as c on p.category = c.id left join f_sku_price_inventory as skup on sku.barcode = skup.barcode where 1 = 1 "
			+ "<if test='barcode != null'>" + " and sku.barcode = #{barcode} " + "</if>"
			+ "<if test='name != null'>" + " and p.name like CONCAT('%',#{name},'%') " + "</if>"
			+ "<if test='category != null'>" + " and c.name like CONCAT('%',#{category},'%') " + "</if>"
			+ "<if test='companyId != null'>" + " and skup.companyId = #{companyId} " + "</if>"
			+ " order by sku.id desc "
			+ "</script>" })
	public int countProductSkuByBarcode(@Param("barcode")String barcode, @Param("name")String name, @Param("category")String category, @Param("companyId")Long companyId);
}
