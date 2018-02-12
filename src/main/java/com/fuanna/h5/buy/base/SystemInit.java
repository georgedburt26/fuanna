package com.fuanna.h5.buy.base;

import java.security.KeyStore;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;
import com.fuanna.h5.buy.mapper.JdbcTestMapper;
import com.fuanna.h5.buy.mapper.ResourceMapper;
import com.fuanna.h5.buy.model.Resource;
import com.fuanna.h5.buy.service.AdminService;

@SuppressWarnings("deprecation")
public class SystemInit {

	private static final Logger logger = Logger.getLogger(HttpsClient.class);
	
	@Autowired
	ResourceMapper resourceMapper;
	
	@Autowired
	JdbcTestMapper jdbcTestMapper;
	
	@Autowired
	AdminService adminService;
	
	public void init() throws Exception {
		testJdbc();
		initResource();
		initHttpsClient();
	}
	
	public void initResource() {
		for (Resource resource : resourceMapper.queryAllResources()) {
			BaseConfig.addResource(resource);
		}
		BaseConfig.setTreeResources(adminService.queryResources());
		logger.info("系统权限资源初始化结束");
	}
	
	private void testJdbc() throws Exception{
		try {
			jdbcTestMapper.test();
			logger.info("数据库|" + BaseConfig.getJdbcConfig("url") + "|JDBC连接成功");
		}catch(Exception e) {
			logger.error("数据库|" + BaseConfig.getJdbcConfig("url") + "|JDBC连接失败", e);
		}
	}
	
	private void initHttpsClient() {
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
}
