package com.mooctest.weixin.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mooctest.weixin.daobase.BaseDao;
import com.mooctest.weixin.model.PreparedQuiz;

/**  
* 类说明   
*  
* @author  cxz
* @date 2017年4月11日  新建  
*/
@Repository
public class PreparedQuizDao extends BaseDao<PreparedQuiz, Integer>{

	public List<PreparedQuiz> getListByColumnValue(String colName, Object colValue){
		String hqlString = "select distinct q from PreparedQuiz q where q." + colName + " = ?";
		return getListByHQL(hqlString, colValue);
	}
	
	public List<PreparedQuiz> getListByCondition(String condition) {
		String hqlString = "select distinct q from PreparedQuiz q where " + condition;
		return getListByHQL(hqlString);
	}
}
