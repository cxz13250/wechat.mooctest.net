package com.mooctest.weixin.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mooctest.weixin.daobase.BaseDao;
import com.mooctest.weixin.model.RollcallItem;

@Repository
public class RollcallItemDao extends BaseDao<RollcallItem, Integer> {

	public List<RollcallItem> getListByColumnValue(String colName, Object colValue){
		String hqlString = "select distinct q from RollcallItem q where q." + colName + " = ?";
		return getListByHQL(hqlString, colValue);
	}
	
	public List<RollcallItem> getListByCondition(String condition) {
		String hqlString = "select distinct q from RollcallItem q where " + condition;
		return getListByHQL(hqlString);
	}
	
	public void saveRollcallItem(RollcallItem rollcallItem){
		save(rollcallItem);
	}
	
	public void updateRollcallItem(RollcallItem rollcallItem){
		update(rollcallItem);
	}
}
