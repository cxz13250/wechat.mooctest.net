package com.mooctest.weixin.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mooctest.weixin.daobase.BaseDao;
import com.mooctest.weixin.model.Question;

@Repository
public class QuestionDao extends BaseDao<Question, Integer> {

	public List<Question> getQuestionByColValue(String colName, Object colValue){
		String hqlstring="select distinct q from Question q where q."+colName+" = ?";
		return getListByHQL(hqlstring, colValue);
	}
	
	public List<Question> getQuestionByCondition(String condition){
		String hqlstring="select distinct q from Question q where "+condition;
		return getListByHQL(hqlstring);
	}
	
	public void saveQuestion(Question question){
		save(question);
	}
	
	public List<Question> getQuestionByQuizid(int quizid){
		return getQuestionByColValue("quizid", quizid);
	}
	
	public List<Question> getQuestionById(int id){
		return getQuestionByColValue("id", id);
	}
}
