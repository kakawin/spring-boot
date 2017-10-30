package com.kakawin.gis.springboot.config.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;

@Configuration
@MapperScan("com.kakawin.gis.springboot.modules.**.mapper")
public class MybatisPlusConfig {

	/**
	 * mybatis-plus SQL执行效率插件【生产环境可以关闭】
	 */
	@Bean
	@Profile({ "dev", "test" })
	public PerformanceInterceptor performanceInterceptor() {
		return new PerformanceInterceptor();
	}

	/**
	 * mybatis-plus分页插件<br>
	 * 文档：http://mp.baomidou.com<br>
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		paginationInterceptor.setLocalPage(true);// 开启 PageHelper 的支持
		return paginationInterceptor;
	}

}
