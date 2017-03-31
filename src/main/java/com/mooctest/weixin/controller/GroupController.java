package com.mooctest.weixin.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mooctest.weixin.entity.Group;
import com.mooctest.weixin.manager.Managers;
import com.mooctest.weixin.manager.WitestManager;

@Controller
@RequestMapping("/group")
public class GroupController {

	@RequestMapping(value="/query")
	public ModelAndView getMyGroup(@RequestParam("openid")String openid,HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		
		String username=Managers.accountManager.getAccount(openid);
		List<Group> list=WitestManager.getGroup(username);
		
		ModelAndView mv=new ModelAndView();
		mv.addObject("list", list);
		mv.setViewName("mygroup");
		return mv;
	}
	
	@RequestMapping(value="/join")
	public ModelAndView joinGroup(@RequestParam("openid")String openid,HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		
		String username=Managers.accountManager.getAccount(openid);
		String managerName=request.getParameter("managerName"); 
		String groupId=request.getParameter("groupId");
		
		boolean flag=WitestManager.joinGroup(username, groupId, managerName);
		ModelAndView mv=new ModelAndView();
		
		if(flag==false){
			mv.addObject("message","加入群组失败");
		}
		else
			mv.addObject("message", "加入群组成功");
		mv.setViewName("join_success");
		return mv;
	}
}
