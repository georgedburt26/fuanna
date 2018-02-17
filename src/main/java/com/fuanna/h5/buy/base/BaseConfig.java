package com.fuanna.h5.buy.base;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.inject.New;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fuanna.h5.buy.model.Resource;
import com.fuanna.h5.buy.model.Weather;

public class BaseConfig {

	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private static Logger logger = Logger.getLogger(BaseConfig.class);

	private static final Properties BASE_CONFIG = new Properties();

	private static final Properties JDBC_CONFIG = new Properties();

	private static final Properties UPLOAD_CONFIG = new Properties();

	private static final String JDBC_PATH = "//jdbc.properties";

	private static final String UPLOAD_PATH = "//upload.properties";

	private static final String CONFIG_PATH = "//config.properties";

	private static final List<Resource> resources = new ArrayList<Resource>();

	private static final List<Resource> treeResources = new ArrayList<Resource>();

	private static final ConcurrentHashMap<Long, Map<String, HttpSession>> sessionMap = new ConcurrentHashMap<Long, Map<String, HttpSession>>();

	private static final ConcurrentHashMap<String, Map<String, Weather>> weatherMap = new ConcurrentHashMap<String, Map<String, Weather>>();
	
	private static String weatherDate = sdf.format(new Date());

	static {
		try (InputStream in = BaseConfig.class.getResourceAsStream(JDBC_PATH)) {
			JDBC_CONFIG.load(in);
		} catch (IOException e) {
			logger.error("JDBC_CONFIG获取失败" + e.getMessage(), e);
		}

		try (InputStream in = BaseConfig.class.getResourceAsStream(UPLOAD_PATH)) {
			UPLOAD_CONFIG.load(in);
		} catch (IOException e) {
			logger.error("UPLOAD_CONFIG获取失败" + e.getMessage(), e);
		}

		try (InputStream in = BaseConfig.class.getResourceAsStream(CONFIG_PATH)) {
			BASE_CONFIG.load(in);
		} catch (IOException e) {
			logger.error("BASE_CONFIG获取失败" + e.getMessage(), e);
		}
	}

	public static String getJdbcConfig(String name) {
		return JDBC_CONFIG.getProperty(name);
	}

	public static String getUploadConfig(String name) {
		return UPLOAD_CONFIG.getProperty(name);
	}

	public static String getBaseConfig(String name) {
		return BASE_CONFIG.getProperty(name);
	}

	public static void addResource(Resource resource) {
		resources.add(resource);
	}

	public static List<Resource> getResources() {
		return resources;
	}

	public static List<Resource> getTreeResources() {
		return treeResources;
	}

	public static Map<String, HttpSession> getSessionMap(Long companyId) {
		return sessionMap.get(companyId);
	}

	public static ConcurrentHashMap<String, Map<String, Weather>> getWeatherMap() {
		return weatherMap;
	}

	public static void putWeatherMap(String date, String location, Weather weather) {
		if (!weatherDate.equals(date)) {
			weatherMap.remove(weatherDate);
			weatherDate = date;
			logger.info("移除" + weatherDate + "天气数据");
		}
		Map<String, Weather> map = new HashMap<String, Weather>();
		map.put(location, weather);
		map = weatherMap.putIfAbsent(date, map);
		if (map != null) {
			map.put(location, weather);
		}
	}

	protected static void setTreeResources(List<Resource> treeResources) {
		BaseConfig.treeResources.addAll(treeResources);
	}

	protected static void putSessionMap(Long companyId, String key, HttpSession value) {
		Map<String, HttpSession> map = new HashMap<String, HttpSession>();
		map.put(key, value);
		map = sessionMap.putIfAbsent(companyId, map);
		if (map != null) {
			map.put(key, value);
		}
		// ReentrantLock rtl = null;
		// synchronized (logger) {
		// if(rtls.get(companyId) == null) {
		// rtl = new ReentrantLock();
		// rtls.put(companyId, rtl);
		// }
		// else {
		// rtl = rtls.get(companyId);
		// }
		// }
		// rtl.lock();
		// Map<String, HttpSession> map = BaseConfig.sessionMap.get(companyId);
		// if (map != null && !map.isEmpty()) {
		// map.put(key, value);
		// }
		// else {
		// map = new HashMap<String, HttpSession>();
		// map.put(key, value);
		// BaseConfig.sessionMap.put(companyId, map);
		// }
		// rtl.unlock();
	}

	protected static void removeSessionMap(Long companyId, String key) {
		sessionMap.get(companyId).remove(key);
	}
}
