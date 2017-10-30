package com.kakawin.gis.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties
public class SpringBootWebApplication extends WebMvcConfigurerAdapter {

	/**
	 * favorPathExtension表示支持后缀匹配，
	 * 属性ignoreAcceptHeader默认为fasle，表示accept-header匹配，defaultContentType开启默认匹配。
	 */
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(false);
	}

	/**
	 * setUseSuffixPatternMatch : 设置是否是后缀模式匹配，如“/user”是否匹配/user.*，默认真即匹配；<br>
	 * setUseTrailingSlashMatch : 设置是否自动后缀路径模式匹配，如“/user”是否匹配“/user/”，默认真即匹配；
	 */
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.setUseSuffixPatternMatch(false).setUseTrailingSlashMatch(true);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebApplication.class, args);
	}
}
