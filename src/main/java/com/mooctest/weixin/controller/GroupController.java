package com.mooctest.weixin.controller;

import com.mooctest.weixin.data.Group;
import com.mooctest.weixin.data.JoinResult;
import com.mooctest.weixin.manager.Managers;
import com.mooctest.weixin.manager.WitestManager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/group")
public class GroupController {

	@RequestMapping(value="/query")
	public ModelAndView getMyGroup(@RequestParam("openid")String openid,HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		
		String username=Managers.accountManager.getAccount(openid).getUsername();
		List<Group> list=WitestManager.getGroup(username);
		
		ModelAndView mv=new ModelAndView();
		mv.addObject("list", list);
		mv.addObject("openid", openid);
		mv.setViewName("mygroup");
		return mv;
	}
	
	@RequestMapping(value="/tojoin")
	public ModelAndView toJoinGroup(@RequestParam("openid")String openid,HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		
		ModelAndView mView=new ModelAndView();
		mView.addObject("openid",openid);
		mView.setViewName("joingroup");
		return mView;
	}
	
	@RequestMapping(value="/join")
	public ModelAndView joinGroup(@RequestParam("openid")String openid,HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		
		String username=Managers.accountManager.getAccount(openid).getUsername();
		String managerName=request.getParameter("managerName"); 
		String groupId=request.getParameter("groupId");
		
		ModelAndView mv=new ModelAndView();
		
		if(managerName==null){
			mv.setViewName("fail");
			mv.addObject("msg","群主名不能为空！");
			mv.addObject("msg_title","加入群组失败");
		}
		else if(groupId==null){
			mv.setViewName("fail");
			mv.addObject("msg","群组编号不能为空！");
			mv.addObject("msg_title","加入群组失败");
		}
		else{
			JoinResult jr=WitestManager.joinGroup(username, groupId, managerName);
			
			if(jr.isSuccess()==false){
				mv.setViewName("fail");
				mv.addObject("msg",jr.getMessage());
				mv.addObject("msg_title","加入群组失败");
			}
			else {
				mv.setViewName("success");
				mv.addObject("msg_title", "成功");
				mv.addObject("msg", "加入群组成功");
			}
		}

		return mv;
	}
}
