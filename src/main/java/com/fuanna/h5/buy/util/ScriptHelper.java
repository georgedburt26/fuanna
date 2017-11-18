package com.fuanna.h5.buy.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ScriptHelper {

	public static void alert(String msg) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("<div class='am-alert am-alert-success' data-am-alert>")
		.append("<div class='am-alert am-alert-success data-am-alert>")
		.append("<button type='button' class='am-close'>&times;</button>")
		.append("<p>" + msg + "</p>")
		.append("</div>")
		.append("<script>$('.am-alert').alert()</script>");
		PrintWriter out = null;
		try{
		HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
        out = response.getWriter();
        out.print(stringBuffer.toString());
        //释放资源
  //      out.flush();
 //       out.close();
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			if (out != null) {
//				out.close();
			}
		}
	}
	
	public static void formAlert(String errorCode, String errorMsg) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		request.setAttribute("errorCode", errorCode);
		request.setAttribute("errorMsg", errorMsg);
	}
}
