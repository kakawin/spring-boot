package com.kakawin.gis.springboot.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

public class WebUtil {
	public static Map<String, Object> getParamMap(HttpServletRequest request) {
		Map<String, String[]> requestMap = request.getParameterMap();
		return getParamMap(requestMap);
	}

	public static Map<String, Object> getParamMap(Map<String, String[]> requestMap) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for (Entry<String, String[]> ent : requestMap.entrySet()) {
			String[] values = requestMap.get(ent.getKey());
			if (values != null) {
				paramMap.put(ent.getKey(), (values.length > 0 ? values[0] : null));
			}
		}
		return paramMap;
	}

	public static String getRemoteHost(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
	}

}
