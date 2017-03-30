package com.mooctest.weixin.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mooctest.weixin.daobase.BaseDao;
import com.mooctest.weixin.model.Task;

/**  
* 类说明   
*  
* @author  
* @date 2017年3月25日  新建  
*/

@Repository
public class TaskDao extends BaseDao<Task, Integer> {

	public List<Task> getListByColumnValue(String colName, Object colValue){
		String hqlString = "select distinct q from Clazz q where q." + colName + " = ?";
        return getListByHQL(hqlString, colValue);
	}
	
	public List<Task> getListByOpenId(String OpenId) {
        return getListByColumnValue("openid", OpenId);
    }
	
    public List<Task> getListByCondition(String condition) {
        String hqlString = "select distinct q from Exam q where " + condition;
        return getListByHQL(hqlString);
    }
}
