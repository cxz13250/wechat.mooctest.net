package com.mooctest.weixin.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mooctest.weixin.daobase.BaseDao;
import com.mooctest.weixin.model.QuizItem;

/**  
* 类说明   
*  
* @author  cxz
* @date 2017年4月11日  新建  
*/
@Repository
public class QuizItemDao extends BaseDao<QuizItem, Integer> {

	public List<QuizItem> getListByColumnValue(String colName, Object colValue){
		String hqlString = "select distinct q from QuizItem q where q." + colName + " = ?";
		return getListByHQL(hqlString, colValue);
	}
	
	public List<QuizItem> test(String condition){
		String hqlString = "select distinct q from QuizItem q where stuOpenId='openid1' and state=1";
		return getListByHQL(hqlString);
	}
	
	public List<QuizItem> getListByCondition(String condition) {
		String hqlString = "select distinct q from QuizItem q where " + condition;
		return getListByHQL(hqlString);
	}
	
	public void saveQuizItem(QuizItem quizItem){
		save(quizItem);
	}
	
	public void updateQuizItem(QuizItem quizItem){
		update(quizItem);
	}
}
