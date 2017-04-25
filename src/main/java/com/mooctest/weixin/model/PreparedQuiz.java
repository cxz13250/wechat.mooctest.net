package com.mooctest.weixin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="prepared_quiz")
public class PreparedQuiz {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
	private Integer id;
	
	@Column(name="group_id")
	private Integer groupId;
	
	@Column(name="quiz_type")
	private Integer quizType;
	
	@Column(name="question_type")
	private Integer questionType;
	
	@Column(name="quiz_content", length=400)
	private String quizContent;
	
	@Column(name="quiz_title", length=50)
	private String quizTitle;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setClassId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getQuizType() {
		return quizType;
	}

	public void setQuizType(Integer quizType) {
		this.quizType = quizType;
	}

	public Integer getQuestionType() {
		return questionType;
	}

	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}

	public String getQuizContent() {
		return quizContent;
	}

	public void setQuizContent(String quizContent) {
		this.quizContent = quizContent;
	}

	public String getQuizTitle() {
		return quizTitle;
	}

	public void setQuizTitle(String quizTitle) {
		this.quizTitle = quizTitle;
	}
	
	
}
