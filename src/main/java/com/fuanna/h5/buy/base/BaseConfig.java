package com.fuanna.h5.buy.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.locks.ReadWriteLock;

import org.apache.log4j.Logger;

public class BaseConfig {

	private static Logger logger = Logger.getLogger(BaseConfig.class);
	
	private static final Properties JDBC_CONFIG = new Properties();
	
	private static final String JDBC_PATH = "//jdbc.properties";
	
    private static final Map<String, ReadWriteLock> rwls = new HashMap<String, ReadWriteLock>();
	
    static {
        try(InputStream in = BaseConfig.class.getResourceAsStream(JDBC_PATH)){
        	JDBC_CONFIG.load(in);
        } catch (IOException e) {  
            logger.error("JDBC_CONFIG获取失败" + e.getMessage(), e);  
        }
    }
    
    public static String getJdbcConfig(String name) {
    	return JDBC_CONFIG.getProperty(name);
    }
}
