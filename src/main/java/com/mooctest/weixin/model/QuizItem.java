package com.mooctest.weixin.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="quiz_item")
public class QuizItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "worOpenid", length = 50)
	private String worOpenId;

	@Column(name = "groupid")
	private Integer groupid;

	@Column(name = "quizid")
	private Integer quizid;
	
	@Column(name = "questionid")
	private Integer questionid;
	
	@Column(name = "type")
	private Integer type;

	@Column(name = "title", length = 50)
	private String title;

	@Column(name = "content", length = 400)
	private String content;

	@Column(name = "worAnswer", length = 100)
	private String worAnswer;

	@Column(name = "state")  //小测状态:1为正在进行,-1为已经结束
	private Integer state;

	@Column(name = "grade")
	private Integer grade;

	@Column(name = "manOpenid", length = 50)
	private String manOpenId;
	
	@Column(name = "createTime")
	private Timestamp createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWorOpenId() {
		return worOpenId;
	}

	public void setWorOpenId(String worOpenId) {
		this.worOpenId = worOpenId;
	}

	public String getWorAnswer() {
		return worAnswer;
	}

	public void setWorAnswer(String worAnswer) {
		this.worAnswer = worAnswer;
	}

	public String getManOpenId() {
		return manOpenId;
	}

	public void setManOpenId(String manOpenId) {
		this.manOpenId = manOpenId;
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Integer getQuizid() {
		return quizid;
	}

	public void setQuizid(Integer quizid) {
		this.quizid = quizid;
	}

	public Integer getQuestionid() {
		return questionid;
	}

	public void setQuestionid(Integer questionid) {
		this.questionid = questionid;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}	
		
}
