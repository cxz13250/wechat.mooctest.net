package com.mooctest.weixin.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="rollcall_answer")
public class RollcallAnswer {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int id;
	
	@Column(name="wor_openid")
	private String openid;
	
	@Column(name="rollcall_id")
	private int rollcallid;
	
	@Column(name="worLocation")
	private String worLocation;
	
	@Column(name="distance")
	private String distance;
	
	@Column(name="recordtime")
	private Timestamp recordtime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRollcallid() {
		return rollcallid;
	}

	public void setRollcallid(int rollcallid) {
		this.rollcallid = rollcallid;
	}

	public String getWorLocation() {
		return worLocation;
	}

	public void setWorLocation(String worLocation) {
		this.worLocation = worLocation;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public Timestamp getRecordtime() {
		return recordtime;
	}

	public void setRecordtime(Timestamp recordtime) {
		this.recordtime = recordtime;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
}
