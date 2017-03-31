package com.mooctest.weixin.manager;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mooctest.weixin.entity.Group;

@Service
public class GroupManager {

	//根据慕测用户名获取群组
	public List<Group> getGroup(String username){
		List<Group> list=WitestManager.getGroup(username);
		return list;
	}
	
	public void joinGroup(String groupId,String managerName){
		
	}
}