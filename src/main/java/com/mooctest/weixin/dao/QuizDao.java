package com.mooctest.weixin.dao;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mooctest.weixin.daobase.BaseDao;
import com.mooctest.weixin.model.Quiz;

/**  
* 类说明   
*  
* @author  cxz
* @date 2017年4月12日  新建  
*/
@Repository
public class QuizDao extends BaseDao<Quiz, Integer> {

	public List<Quiz> getQuizByColValue(String colName, Object colValue){
		String hqlstring="select distinct q from Quiz q where q."+colName+" = ?";
		return getListByHQL(hqlstring, colValue);
	}
	
	public List<Quiz> getQuizByTime(Timestamp time,String openid){
		return getQuizByCondition("openid='"+openid+"' and start='"+time+"'");
	}
	
	public List<Quiz> getQuizByOpenId(String openid){
		return getQuizByColValue("openid", openid);
	}
	
	public List<Quiz> getQuizById(int id){
		return getQuizByColValue("id", id);
	}
	
	public List<Quiz> getQuizByCondition(String condition){
		String hqlString = "select distinct q from Quiz q where " + condition;
		return getListByHQL(hqlString);
	}
	
	public void saveQuiz(Quiz quiz){
		save(quiz);
	}
	
	public void updateQuiz(Quiz quiz){
		update(quiz);
	}
}
