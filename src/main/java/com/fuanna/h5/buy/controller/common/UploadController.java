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
import com.fuanna.h5.buy.util.RandomUtil;
import com.google.gson.Gson;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.request.CreateFolderRequest;
import com.qcloud.cos.request.StatFolderRequest;
import com.qcloud.cos.request.UploadFileRequest;
import com.qcloud.cos.sign.Credentials;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import net.sf.json.JSONObject;

@Controller
public class UploadController {

	private static final Logger logger = Logger.getLogger(UploadController.class);

	private static final String qiniu_accessKey = BaseConfig.getUploadConfig("qiniu_accessKey");
	private static final String qiniu_secretKey = BaseConfig.getUploadConfig("qiniu_secretKey");
	private static final String qiniu_bucket = BaseConfig.getUploadConfig("qiniu_bucket");
	private static final String qiniu_host = BaseConfig.getUploadConfig("qiniu_host");
	private static final UploadManager qiniu_uploadManager = new UploadManager(new Configuration(Zone.zone1()));
	private static final Auth qiniu_auth = Auth.create(qiniu_accessKey, qiniu_secretKey);

	private static final long tencent_appId = Long.parseLong(BaseConfig.getUploadConfig("tencent_appId"));
	private static final String tencent_secretId = BaseConfig.getUploadConfig("tencent_secretId");
	private static final String tencent_secretKey = BaseConfig.getUploadConfig("tencent_secretKey");
	private static final String tencent_region = "bj";
	private static final String tencent_bucketName = "fgxfuanna";
	private static final Credentials cred = new Credentials(tencent_appId, tencent_secretId, tencent_secretKey);
	private static final ClientConfig clientConfig = new ClientConfig();

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final SimpleDateFormat datesdf = new SimpleDateFormat("yyyyMMdd");

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
			String upToken = qiniu_auth.uploadToken(qiniu_bucket, key);
			Response response = qiniu_uploadManager.put(is, key, upToken, null, null);
			// 解析上传成功的结果
			DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
			fileUrl = qiniu_host + "/" + putRet.key;
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

	@RequestMapping("/tencentUpload.do")
	public @ResponseBody RstResult uploadFileToTencentCloud(@RequestParam("file") CommonsMultipartFile file,
			HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		clientConfig.setRegion(tencent_region);
		COSClient cosClient = new COSClient(clientConfig, cred);
		StringBuffer sb = new StringBuffer();
		String fileName = file.getOriginalFilename();
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		String pre = "/img/";
		String folder = datesdf.format(new Date()) + "/";
		// 指定要上传到 COS 上的路径
		String key = sb.append(pre).append(folder).append(sdf.format(new Date())).append(RandomUtil.getRandomCode(6)).append(".")
				.append(suffix).toString();
		String url = "";
		try{
			CreateFolderRequest createFolderRequest = new CreateFolderRequest(tencent_bucketName, pre + folder);
			if (!JSONObject.fromObject(cosClient.createFolder(createFolderRequest)).getString("code").equals("0")) {
				throw new Exception("上传失败");
			}
			UploadFileRequest uploadFileRequest = new UploadFileRequest(tencent_bucketName, key, file.getBytes());
			JSONObject uploadFileRet = JSONObject.fromObject(cosClient.uploadFile(uploadFileRequest));
			if (!uploadFileRet.getString("code").equals("0")) {
				throw new Exception("上传失败");
			}
			url = uploadFileRet.getJSONObject("data").getString("access_url");
			logger.info("文件上传成功路径:" + url);
		} catch (Exception e) {
			logger.error("上传文件到腾讯云失败" + e.getMessage(), e);
			return new RstResult(ErrorCode.SB, "上传文件失败");
		}finally {
			cosClient.shutdown();
		}
		return new RstResult(ErrorCode.CG, "上传文件成功", url);
	}
}
