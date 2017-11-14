package com.fuanna.h5.buy.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

public class JsonUtils {

	public static void printJson(HttpServletResponse response, Object rspObject) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		try {
			PrintWriter out = response.getWriter();
			out.write(JSONObject.fromObject(rspObject).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
