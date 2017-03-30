package com.mooctest.weixin.pojo;

import java.util.Date;

/**
 * 凭证
 * 
 * @author Aaron
 * @date 2014-6-23
 */
public class Token {

	// 接口访问凭证
	private String accessToken;
	// 凭证有效期，单位：秒
	private int expiresIn;
	private Long createTime;

	public Token() {
		createTime = new Date().getTime();
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	public boolean isValid() {
		if ((System.currentTimeMillis() - createTime) < (expiresIn - 100) * 1000) {
			return true;
		}
		return false;
	}
}