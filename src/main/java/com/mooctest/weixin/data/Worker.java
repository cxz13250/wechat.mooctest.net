package com.mooctest.weixin.data;

public class Worker {

	private int id;//慕测账号编号
	private String university;//学校
	private String mobile;//慕测账号手机
	private String email;//慕测账号邮箱
	private String workerName;//学生姓名
	private int grade;//年级
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String worName) {
		this.workerName = worName;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int uniId) {
		this.grade = uniId;
	}
	
}
