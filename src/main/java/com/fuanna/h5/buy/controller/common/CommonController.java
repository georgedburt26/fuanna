package com.fuanna.h5.buy.controller.common;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

@Controller
public class CommonController {

	private static final Logger logger = Logger
			.getLogger(CommonController.class);

	@Autowired
	Producer captchaProducer;

	@RequestMapping({ "/imageCode.do" })
	public String imageCode(HttpServletRequest request,
			HttpServletResponse response) {
		ServletOutputStream out = null;
		try {
			String page = request.getParameter("page");
			HttpSession session = request.getSession();
			response.setDateHeader("Expires", 0);
			response.setHeader("Cache-Control",
					"no-store, no-cache, must-revalidate");
			response.addHeader("Cache-Control", "post-check=0, pre-check=0");
			response.setHeader("Pragma", "no-cache");
			response.setContentType("image/jpeg");

			// 生成验证码文本
			String capText = captchaProducer.createText();
			session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
			// 利用生成的字符串构建图片
			BufferedImage bi = captchaProducer.createImage(capText);
			out = response.getOutputStream();
			ImageIO.write(bi, "jpg", out);
			out.flush();
			out.close();
			session.setAttribute(page + "_imageCode", capText);
		}catch(Exception e) {
			logger.error("生成验证码失败", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
