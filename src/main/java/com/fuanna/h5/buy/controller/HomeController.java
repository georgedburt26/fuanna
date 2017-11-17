package com.fuanna.h5.buy.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fuanna.h5.buy.model.Category;
import com.fuanna.h5.buy.service.ProductService;
import com.fuanna.h5.buy.service.UserService;

@Controller
@RequestMapping
public class HomeController{
	
	private static final Logger logger = Logger.getLogger(HomeController.class);
	
	private static final Random random = new Random();
	
	private static final Set<Long> idSets = new HashSet<Long>();
	
	@Autowired
	UserService userService;
	
	@Autowired
	ProductService productService;

	@RequestMapping({"/home.do"})
	public String homeInit(HttpServletRequest request, HttpServletResponse response) {
		idSets.clear();
		List<Category> categoryList = productService.listCategories();
		int productCount = productService.countProductSku();
		List<Long> ids = new ArrayList<Long>();
		int count = productCount <= 5 ? productCount : 5;
		for (int i = 1; i < count + 1; i++) {
			ids.add(new Long(i));
		}
		List<Map<String, String>> productList = productService.listProduct(ids);
		request.setAttribute("categoryList", categoryList);
		request.setAttribute("productList", productList);
		return "/front/home";
	}
}
