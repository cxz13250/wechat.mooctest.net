package com.mooctest.weixin.menu;

/**
 * click类型的按钮
 * 
 * @author Aaron
 * @date 2014-6-23
 */
public class ClickButton extends Button {
	private String type;
	private String key;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}