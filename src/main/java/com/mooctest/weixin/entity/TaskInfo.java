package com.mooctest.weixin.entity;

public class TaskInfo {

	private int id;

	private String password;
	
	private String taskName;
	
	private int workerid;

	private int taskId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;

	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public int getWorkerid() {
		return workerid;
	}

	public void setWorkerid(int workerid) {
		this.workerid = workerid;
	}
}
