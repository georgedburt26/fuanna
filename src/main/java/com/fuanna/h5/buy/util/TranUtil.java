package com.fuanna.h5.buy.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TranUtil {
	Logger logger = LoggerFactory.getLogger(getClass());

	private static final String EQUAL_FLAG = "=";
	private static final String APPEND_FLAG = "&";
	public static String formatSignMsg(Map<String, String> paraMap,boolean urlencode,boolean keyLowerCase) {

		StringBuffer buff = new StringBuffer();
		List<Map.Entry<String, String>> keyList = new ArrayList<Map.Entry<String, String>>(paraMap.entrySet());

		Collections.sort(keyList,new Comparator<Map.Entry<String, String>>() {
					@Override
					public int compare(Map.Entry<String, String> src,Map.Entry<String, String> tar) {
						return (src.getKey()).toString().compareTo(tar.getKey());
					}
				});

		for (int i = 0; i < keyList.size(); i++) {
			Map.Entry<String, String> item = keyList.get(i);
			String key = item.getKey();
			String val = item.getValue();
			if (!StringUtils.isEmpty(key)) {
				
				if (urlencode) {
					try {
						val = URLEncoder.encode(val, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						
						e.printStackTrace();
						return null;
					}

				}
				buff.append(keyLowerCase?key.toLowerCase():key);
				buff.append(EQUAL_FLAG);
				buff.append(val);
				buff.append(APPEND_FLAG);

			}
		}

		if (buff.length() > 0) {
			return buff.substring(0, buff.length() - 1);
		}else return null;
	}
	
	public static String appendParam(String url, Map<String, String> params) {
		StringBuffer sb = new StringBuffer();
		sb.append(url);
		int index = 0;
		for (Entry<String, String> entry : params.entrySet()) {
			String seperate = index == params.size() - 1 ? "" : "&";
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key + "=" + value + seperate);
			index ++;
		}
		return sb.toString();
	}
}
