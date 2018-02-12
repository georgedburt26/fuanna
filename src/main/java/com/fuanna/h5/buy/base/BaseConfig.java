package com.fuanna.h5.buy.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fuanna.h5.buy.model.Resource;

public class BaseConfig {

	private static Logger logger = Logger.getLogger(BaseConfig.class);
	
	private static final Properties BASE_CONFIG = new Properties();
	
	private static final Properties JDBC_CONFIG = new Properties();
	
	private static final Properties UPLOAD_CONFIG = new Properties();
	
	private static final String JDBC_PATH = "//jdbc.properties";
	
	private static final String UPLOAD_PATH = "//upload.properties";
	
	private static final String CONFIG_PATH = "//config.properties";
	
	private static final List<Resource> resources = new ArrayList<Resource>();
	
	private static final List<Resource> treeResources = new ArrayList<Resource>();
	
	private static final Map<Long, Map<String, HttpSession>> sessionMap = new HashMap<Long, Map<String, HttpSession>>();

	private static final ConcurrentHashMap<Long, ReentrantLock> rtls = new ConcurrentHashMap<Long, ReentrantLock>();
	
    static {
        try(InputStream in = BaseConfig.class.getResourceAsStream(JDBC_PATH)){
        	JDBC_CONFIG.load(in);
        } catch (IOException e) {  
            logger.error("JDBC_CONFIG获取失败" + e.getMessage(), e);  
        }
        
        try(InputStream in = BaseConfig.class.getResourceAsStream(UPLOAD_PATH)){
        	UPLOAD_CONFIG.load(in);
        } catch (IOException e) {  
            logger.error("UPLOAD_CONFIG获取失败" + e.getMessage(), e);  
        }
        
        try(InputStream in = BaseConfig.class.getResourceAsStream(CONFIG_PATH)){
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
		return BaseConfig.sessionMap.get(companyId);
	}

	protected static void setTreeResources(List<Resource> treeResources) {
		BaseConfig.treeResources.addAll(treeResources);
	}
	
	protected static void putSessionMap(Long companyId, String key, HttpSession value) {
		ReentrantLock rtl = null;
		synchronized (logger) {
			if(rtls.get(companyId) == null) {
				rtl = new ReentrantLock();
				rtls.put(companyId, rtl);
			}
			else {
				rtl = rtls.get(companyId);
			}
		}
		rtl.lock();
		Map<String, HttpSession> map = BaseConfig.sessionMap.get(companyId);
		if (map != null && !map.isEmpty()) {
			map.put(key, value);
		}
		else {
			map = new HashMap<String, HttpSession>();
			map.put(key, value);
			BaseConfig.sessionMap.put(companyId, new HashMap<String, HttpSession>());
		}
		rtl.unlock();
	}
	
	protected static void removeSessionMap(Long companyId, String key) {
		BaseConfig.sessionMap.get(companyId).remove(key);
	}
}
