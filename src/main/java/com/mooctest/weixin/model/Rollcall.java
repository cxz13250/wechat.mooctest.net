package com.mooctest.weixin.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="rollcall")
public class Rollcall{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="man_openid")
	private String manOpenid;
	
	@Column(name="group_id")
	private String groupid;
	
	@Column(name="man_Location")
	private String manLocation;
	
	@Column(name="create_time")
	private Timestamp createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getManOpenid() {
		return manOpenid;
	}

	public void setManOpenid(String manOpenid) {
		this.manOpenid = manOpenid;
	}

	public String getManLocation() {
		return manLocation;
	}

	public void setManLocation(String manLocation) {
		this.manLocation = manLocation;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	
	
}
