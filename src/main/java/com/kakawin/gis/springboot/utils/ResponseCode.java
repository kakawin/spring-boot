package com.kakawin.gis.springboot.utils;

public enum ResponseCode {

	OK(2000, "OK"),

	ERROR(3000, "error");

	private final int value;

	private final String description;

	private ResponseCode(int value, String description) {
		this.value = value;
		this.description = description;
	}

	/**
	 * Return the integer value of this status code.
	 */
	public int value() {
		return this.value;
	}

	/**
	 * Return the descriptionof this status code.
	 */
	public String getDescription() {
		return description;
	}

}
