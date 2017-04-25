package com.mooctest.weixin.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="rollcall_item")
public class RollcallItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "wor_openid", length = 50)
	private String worOpenId;

	@Column(name = "man_openid", length = 50)
	private String manOpenId;
	
	@Column(name = "rollcall_id")
	private Integer rollcallid;

	@Column(name = "type")
	private Integer type;

	@Column(name = "wor_location", length = 50)
	private String worLocation;
	
	@Column(name = "man_location", length = 50)
	private String manLocation;
	
	@Column(name = "distance", length = 50)
	private String distance;

	@Column(name = "state")
	private Integer state;

	@Column(name = "group_id")
	private Integer groupId;

	@Column(name = "grade")
	private Integer grade;

	@Column(name = "create_time")
	private Timestamp createTime;
	
	@Column(name = "record_time")
	private Timestamp recordTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getWorLocation() {
		return worLocation;
	}

	public void setWorLocation(String worLocation) {
		this.worLocation = worLocation;
	}

	public String getManLocation() {
		return manLocation;
	}

	public void setManLocation(String manLocation) {
		this.manLocation = manLocation;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Timestamp recordTime) {
		this.recordTime = recordTime;
	}

	public String getWorOpenId() {
		return worOpenId;
	}

	public void setWorOpenId(String worOpenId) {
		this.worOpenId = worOpenId;
	}

	public String getManOpenId() {
		return manOpenId;
	}

	public void setManOpenId(String manOpenId) {
		this.manOpenId = manOpenId;
	}

	public Integer getRollcallid() {
		return rollcallid;
	}

	public void setRollcallid(Integer rollcallid) {
		this.rollcallid = rollcallid;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	
	
}
