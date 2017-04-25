package com.mooctest.weixin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**  
* 类说明   
*  
* @author  
* @date 2017年3月20日  新建  
*/
@Entity
@Table(name="account")
public class Account {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Integer id;
	
	@Column(name="openid")
	private String openid;
	
	@Column(name="username")
	private String username;
	
	@Column(name="type")
	private int type;
	
	@Column(name="mooc_id")
	private int moocid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getMoocid() {
		return moocid;
	}

	public void setMoocid(int moocid) {
		this.moocid = moocid;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}	
}
