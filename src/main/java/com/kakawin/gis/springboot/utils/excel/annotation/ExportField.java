package com.kakawin.gis.springboot.utils.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExportField {

	/**
	 * 导出字段标题
	 */
	String title() default "";

	/**
	 * 字段归属组（根据分组导出）
	 */
	int[] groups();

	/**
	 * 导出字段所在一组的第几列
	 */
	int[] columns();

	/**
	 * 导出字段映射，格式为key:value
	 */
	String[] mappings() default {};
}
