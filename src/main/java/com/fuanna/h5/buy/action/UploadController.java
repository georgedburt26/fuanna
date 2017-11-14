package com.fuanna.h5.buy.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fuanna.h5.buy.base.BaseConfig;
import com.fuanna.h5.buy.util.JsonUtils;
import com.fuanna.h5.buy.util.RandomUtil;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

@Controller
@RequestMapping
public class UploadController {

	private static final Logger logger = Logger
			.getLogger(UploadController.class);

	public static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	@RequestMapping({ "/upload.do" })
	public String uploadFile(@RequestParam("file") CommonsMultipartFile file,
			HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		Map<String, String> rspMap = new HashMap<String, String>();
		// 构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone1());
		// ...其他参数参考类注释
		UploadManager uploadManager = new UploadManager(cfg);
		// ...生成上传凭证，然后准备上传
		String accessKey = BaseConfig.getQiNiuConfig("accessKey");
		String secretKey = BaseConfig.getQiNiuConfig("secretKey");
		String bucket = BaseConfig.getQiNiuConfig("bucket");
		// 默认不指定key的情况下，以文件内容的hash值作为文件名
		StringBuffer sb = new StringBuffer();
		String key = sb
				.append(sdf.format(new Date()))
				.append(RandomUtil.getRandomCode(6))
				.append(".")
				.append(file.getOriginalFilename().contains(".") ? file
						.getOriginalFilename().split("\\.")[1] : "").toString();
		try (InputStream is = file.getInputStream()) {
			Auth auth = Auth.create(accessKey, secretKey);
			String upToken = auth.uploadToken(bucket, key);
			Response response = uploadManager.put(is, key, upToken, null, null);
			// 解析上传成功的结果
			DefaultPutRet putRet = new Gson().fromJson(response.bodyString(),
					DefaultPutRet.class);
			String fileUrl = BaseConfig.getQiNiuConfig("host") + "/"
					+ putRet.key;
			logger.info("文件上传成功路径:" + fileUrl);
			rspMap.put("errorCode", "0000");
			rspMap.put("errorMsg", "上传文件成功");
			rspMap.put("data", fileUrl);
			JsonUtils.printJson(httpResponse, rspMap);
			return null;
		} catch (QiniuException ex) {
			Response r = ex.response;
			logger.error(r.toString());
			try {
				logger.error(r.bodyString());
			} catch (QiniuException ex2) {
				ex2.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rspMap.put("errorCode", "9999");
		rspMap.put("errorMsg", "上传文件失败");
		JsonUtils.printJson(httpResponse, rspMap);
		return null;
	}
}
