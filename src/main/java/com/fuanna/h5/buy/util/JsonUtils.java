package com.fuanna.h5.buy.util;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class JsonUtils {

	private static final JsonConfig jsonConfig = new JsonConfig();
	
	static {
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
	}
	
	public static void printString(String rsp) {
		HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		try {
			PrintWriter out = response.getWriter();
			out.write(rsp);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void printObject(Object rspObject) {
		printString(JSONObject.fromObject(rspObject, jsonConfig).toString());
	}
	
	public static void printArray(List<?> list) {
		printString(JSONArray.fromObject(list, jsonConfig).toString());
	}
}
