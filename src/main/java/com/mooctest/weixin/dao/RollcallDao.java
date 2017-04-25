package com.mooctest.weixin.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mooctest.weixin.daobase.BaseDao;
import com.mooctest.weixin.model.Rollcall;

@Repository
public class RollcallDao extends BaseDao<Rollcall, Integer> {

	public List<Rollcall> getListByColumnValue(String colName, Object colValue){
		String hqlString = "select distinct q from Rollcall q where q." + colName + " = ?";
		return getListByHQL(hqlString, colValue);
	}
	
	public List<Rollcall> getListByCondition(String condition) {
		String hqlString = "select distinct q from Rollcall q where " + condition;
		return getListByHQL(hqlString);
	}
	
	public void saveRollcall(Rollcall rollcall){
		save(rollcall);
	}
	
	public void updateRollcall(Rollcall rollcall){
		update(rollcall);
	}
}
