package com.fuanna.h5.buy.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import net.sf.json.JSONObject;

public class JsonUtils {

	public static void printString(String rsp) {
		HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		try {
			PrintWriter out = response.getWriter();
			out.write(JSONObject.fromObject(rsp).toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void printObject(Object rspObject) {
		printString(JSONObject.fromObject(rspObject).toString());
	}
}
