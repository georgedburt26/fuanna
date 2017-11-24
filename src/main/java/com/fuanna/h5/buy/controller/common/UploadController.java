package com.fuanna.h5.buy.controller.common;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fuanna.h5.buy.base.BaseConfig;
import com.fuanna.h5.buy.constraints.ErrorCode;
import com.fuanna.h5.buy.model.RstResult;
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
public class UploadController {

	private static final Logger logger = Logger.getLogger(UploadController.class);
	
	private static String accessKey = null;
	private static String secretKey = null;
	private static String bucket = null;
	private static String host = null;
	private static UploadManager uploadManager = null;
	private static Auth auth = null;


	static {
		// ...生成上传凭证
		accessKey = BaseConfig.getQiNiuConfig("accessKey");
		secretKey = BaseConfig.getQiNiuConfig("secretKey");
		bucket = BaseConfig.getQiNiuConfig("bucket");
		host = BaseConfig.getQiNiuConfig("host");
		uploadManager = new UploadManager(new Configuration(Zone.zone1()));
		auth = Auth.create(accessKey, secretKey);
	}

	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	@RequestMapping("/qiNiuUpload.do")
	public @ResponseBody RstResult uploadFileToQiNiu(@RequestParam("file") CommonsMultipartFile file,
			HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {
		String fileUrl = "";
		// 默认不指定key的情况下，以文件内容的hash值作为文件名
		StringBuffer sb = new StringBuffer();
		String fileName = file.getOriginalFilename();
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		String pre = "jpg,png,jpeg,gif,bmp".contains(suffix) ? "image/" : "";
		String key = sb.append(pre).append(sdf.format(new Date())).append(RandomUtil.getRandomCode(6)).append(".")
				.append(suffix).toString();
		try (InputStream is = file.getInputStream()) {
			String upToken = auth.uploadToken(bucket, key);
			Response response = uploadManager.put(is, key, upToken, null, null);
			// 解析上传成功的结果
			DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
			fileUrl = host + "/" + putRet.key;
			logger.info("文件上传成功路径:" + fileUrl);
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof QiniuException) {
				Response r = ((QiniuException) e).response;
				logger.error(r.toString());
				try {
					logger.error(r.bodyString());
				} catch (QiniuException ex2) {
					ex2.printStackTrace();
				}
			}
			return new RstResult(ErrorCode.SB, "上传文件失败");
		}
		return new RstResult(ErrorCode.CG, "上传文件成功", fileUrl);
	}
}
