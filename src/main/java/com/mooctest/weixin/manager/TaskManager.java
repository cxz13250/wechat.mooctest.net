package com.mooctest.weixin.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mooctest.weixin.dao.TaskDao;
import com.mooctest.weixin.model.Task;

/**  
* 类说明   
*  
* @author cxz
* @date 2017年3月25日  新建  
*/

@Service
public class TaskManager {

	@Autowired
	private TaskDao taskDao;
	
	public List<Task> getTaskByOpenid(String openid){
		List<Task> list=taskDao.getListByOpenId(openid);
		return list;
	}
	
	public Task getTaskByCondition(String openid,String name){
		String condition="openid ='"+openid+"' and name='"+name+"'";
		List<Task> list=taskDao.getListByCondition(condition);
		return list.get(0);
	}
}
