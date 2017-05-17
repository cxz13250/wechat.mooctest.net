package com.mooctest.weixin.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="quiz_answer")
public class QuizAnswer {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
	
	@Column(name="wor_openid")
    private String openid;
	
    @Column(name = "wor_answer")
    private String worAnswer;

    @Column(name = "quizid")
    private Integer quizid;
    
    @Column(name = "questionid")
    private Integer questionid;
    
    @Column(name = "worNum")
    private String worNum;

    @Column(name = "record_create_time")
    private Timestamp recordCreateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getRecordCreateTime() {
		return recordCreateTime;
	}

	public void setRecordCreateTime(Timestamp recordCreateTime) {
		this.recordCreateTime = recordCreateTime;
	}

	public String getWorAnswer() {
		return worAnswer;
	}

	public void setWorAnswer(String worAnswer) {
		this.worAnswer = worAnswer;
	}

	public String getWorNum() {
		return worNum;
	}

	public void setWorNum(String worNum) {
		this.worNum = worNum;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
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
}
