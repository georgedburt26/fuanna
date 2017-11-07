package com.fuanna.h5.buy.base;

import java.io.IOException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpsClient {

	private static final Logger logger = Logger.getLogger(HttpsClient.class);

	private static CloseableHttpClient httpClient = null;

	static {
		RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder
				.<ConnectionSocketFactory> create();
		ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();
		registryBuilder.register("http", plainSF);
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			TrustStrategy anyTrustStrategy = new TrustStrategy() {
				@Override
				public boolean isTrusted(
						java.security.cert.X509Certificate[] arg0, String arg1)
						throws java.security.cert.CertificateException {
					return true;
				}
			};
			SSLContext sslContext = SSLContexts.custom().useTLS()
					.loadTrustMaterial(trustStore, anyTrustStrategy).build();
			LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(
					sslContext,
					SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			registryBuilder.register("https", sslSF);
			Registry<ConnectionSocketFactory> registry = registryBuilder
					.build();
			final PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
					registry);
			connManager.setMaxTotal(200);
			connManager.setDefaultMaxPerRoute(50);
			ConnectionKeepAliveStrategy keepAliveStrategy = new DefaultConnectionKeepAliveStrategy() {
				public long getKeepAliveDuration(HttpResponse response,
						HttpContext context) {
					long keepAlive = super.getKeepAliveDuration(response,
							context);
					if (keepAlive == -1) {
						keepAlive = 60 * 1000;
					}
					return keepAlive;
				}
			};
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(5000).setConnectTimeout(5000)
					.setConnectionRequestTimeout(5000).build();
			HttpsClient.setHttpClient(HttpClientBuilder.create()
					.setDefaultRequestConfig(requestConfig)
					.setConnectionManager(connManager)
					.setKeepAliveStrategy(keepAliveStrategy).build());
			new Thread() {
				@Override
				public void run() {
					try {
						while (true) {
							Thread.sleep(5000);
							// 关闭失效的连接
							connManager.closeExpiredConnections();
							// 可选的, 关闭30秒内不活动的连接
							connManager.closeIdleConnections(60,
									TimeUnit.SECONDS);
						}
					} catch (Exception e) {
						logger.error("HttpsClient connectionPool连接检测线程启动失败", e);
					}
				}
			}.start();
		} catch (Exception e) {
			logger.error("HttpsClient初始化失败", e);
		}
		logger.info("httpsClient初始化结束");	
	}
	
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