package com.mooctest.weixin.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mooctest.weixin.daobase.BaseDao;
import com.mooctest.weixin.model.QuizAnswer;

/**  
* 类说明   
*  
* @author  cxz
* @date 2017年4月11日  新建  
*/
@Repository
public class QuizAnswerDao extends BaseDao<QuizAnswer,Integer> {
	
	public List<QuizAnswer> getListByColumnValue(String colName, Object colValue){
        String hqlString = "select distinct q from QuizAnswer q where q." + colName + " = ?";
        return getListByHQL(hqlString, colValue);
    }
    
    public List<QuizAnswer> getListByCondition(String condition) {
        String hqlString = "select distinct q from QuizAnswer q where " + condition;
        return getListByHQL(hqlString);
    }
    
    public List<QuizAnswer> getListByQuizId(int quizId) {
        String hqlString = "select distinct q from QuizAnswer q where q.quizId=" + quizId;
        return getListByHQL(hqlString);
    }

    public void saveQiuzAnswer(QuizAnswer quizAnswer){
    	save(quizAnswer);
    }
}
