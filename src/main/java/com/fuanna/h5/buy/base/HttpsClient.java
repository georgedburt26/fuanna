package com.fuanna.h5.buy.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpsClient {

	private static final Logger logger = Logger.getLogger(HttpsClient.class);

	private static CloseableHttpClient httpClient = null;
	
	public static String get(String url, String content, String contentType) {
		String responseBody = "";
		CloseableHttpResponse response = null;
		try {
			HttpGet httpGet = new HttpGet(url);
			httpGet.setHeader("Content-Type", contentType);
			response = httpClient.execute(httpGet);
			int status = response.getStatusLine().getStatusCode();
			if (status == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				responseBody = EntityUtils.toString(entity);
				EntityUtils.consume(entity);
			} else {
				logger.info("http return status error:"
						+ response.getStatusLine().getStatusCode());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return responseBody;
	}

	public static String post(String url, String content, String contentType) {
		String responseBody = "";
		CloseableHttpResponse response = null;
		// CloseableHttpClient httpClient = HttpClients.createDefault();
		// 支持https
		try {
			HttpPost httpPost = new HttpPost(url);
			if (content != null) {
				StringEntity stringEntity = new StringEntity(content, "gbk");
				httpPost.setEntity(stringEntity);
				httpPost.setHeader("Content-Type", contentType);
			}
			response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				responseBody = EntityUtils.toString(entity);
				EntityUtils.consume(entity);
			} else {
				logger.info("http return status error:"
						+ response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return responseBody;
	}

	public static String post(String url, String content) {
		String responseBody = "";
		CloseableHttpResponse response = null;
		// CloseableHttpClient httpClient = HttpClients.createDefault();
		// 支持https
		try {
			HttpPost httpPost = new HttpPost(url);
			if (content != null) {
				StringEntity stringEntity = new StringEntity(content, "utf-8");
				httpPost.setEntity(stringEntity);
				httpPost.setHeader("Content-Type",
						"application/json; charset=utf-8");
			}
			response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				responseBody = EntityUtils.toString(entity);
				EntityUtils.consume(entity);
			} else {
				logger.info("http return status error:"
						+ response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return responseBody;
	}

	public static String post(String url, Map<String, String> nameValuePair,
			String code) {
		String responseBody = "";
		CloseableHttpResponse response = null;
		// CloseableHttpClient httpClient = HttpClients.createDefault();
		// 支持https
		try {
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> entry : nameValuePair.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				nvps.add(new BasicNameValuePair(key, value));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, code));
			response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				responseBody = EntityUtils.toString(entity);
				EntityUtils.consume(entity);
			} else {
				logger.info("http return status error:"
						+ response.getStatusLine().getStatusCode());
				throw new Exception("http请求失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return responseBody;
	}

	public static CloseableHttpClient getHttpClient() {
		return httpClient;
	}

	public static void setHttpClient(CloseableHttpClient httpClient) {
		HttpsClient.httpClient = httpClient;
	}

	public static void main(String[] args) {
		String content = "{ \"button\":[{	\"type\":\"click\",\"name\":\"百度\",\"url\":\"https://www.baidu.com\"}";
		System.out.println(HttpsClient.post("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=cFm7uioUfhZsOj-bqHW6Jbd4rj3zcYJZmiE3dEhu1VGBx0fQq0n7KKx3yW1FYTaMMwzXjwhtFmVdk7X90s-zBljDpypEE0JXWoXv5Y9u7O5rn-0SqkesQbgqfXOUEtWIJVIgAGARDQ", content));
	}
}