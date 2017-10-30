package com.kakawin.gis.springboot.utils;

public class SysResponse {

	private long timestamp = System.currentTimeMillis();
	private boolean success;
	private int code;
	private String message = "";
	private Object data;

	public SysResponse(boolean success) {
		this.success = success;
	}

	public static SysResponse ok(Object data) {
		SysResponse response = new SysResponse(true);
		response.setCode(ResponseCode.OK.value());
		response.setMessage(ResponseCode.OK.getDescription());
		response.setData(data);
		return response;
	}

	public static SysResponse ok() {
		return ok(null);
	}

	public static SysResponse fail(ResponseCode responseCode, String message) {
		SysResponse response = new SysResponse(false);
		response.setCode(responseCode.value());
		response.setMessage(message);
		return response;
	}

	public static SysResponse fail(ResponseCode responseCode) {
		return fail(responseCode, responseCode.getDescription());
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
