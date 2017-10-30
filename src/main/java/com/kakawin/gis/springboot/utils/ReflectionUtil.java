package com.kakawin.gis.springboot.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class ReflectionUtil {

	public static List<Class<?>> findClasses(String pattern) {
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		List<Class<?>> classes = new ArrayList<Class<?>>();
		try {
			Resource[] resources = resolver.getResources(pattern);
			for (Resource resource : resources) {
				String className = resource.getURL().getPath();
				className = className.split("(classes/)|(!/)")[1];
				className = className.replace("/", ".").replace(".class", "");
				try {
					Class<?> cls = Class.forName(className);
					classes.add(cls);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			return classes;
		} catch (IOException e) {
			return null;
		}
	}
}
