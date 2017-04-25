package com.mooctest.weixin.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mooctest.weixin.daobase.BaseDao;
import com.mooctest.weixin.model.RollcallAnswer;

@Repository
public class RollcallAnswerDao extends BaseDao<RollcallAnswer, Integer> {

	public List<RollcallAnswer> getListByColumnValue(String colName, Object colValue){
		String hqlString = "select distinct q from RollcallAnswer q where q." + colName + " = ?";
		return getListByHQL(hqlString, colValue);
	}
	
	public List<RollcallAnswer> getListByCondition(String condition) {
		String hqlString = "select distinct q from RollcallAnswer where " + condition;
		return getListByHQL(hqlString);
	}
	
	public void saveRollcallAnswer(RollcallAnswer rollcallAnswer){
		save(rollcallAnswer);
	}
}
