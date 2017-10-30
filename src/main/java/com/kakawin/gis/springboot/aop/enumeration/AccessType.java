package com.kakawin.gis.springboot.aop.enumeration;

public enum AccessType {
	/** 未知 */
	UNKNOW(0),
	/** 查 */
	RETRIEVE(1),
	/** 增 */
	CREATE(2),
	/** 改 */
	UPDATE(3),
	/** 删 */
	DELETE(4);

	private final int value;

	private AccessType(int value) {
		this.value = value;
	}

	public int value() {
		return this.value;
	}

}
