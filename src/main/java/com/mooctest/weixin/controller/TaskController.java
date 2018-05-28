package com.mooctest.weixin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mooctest.weixin.data.*;
import com.mooctest.weixin.model.Account;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mooctest.weixin.manager.Managers;
import com.mooctest.weixin.manager.WitestManager;

/**  
* 类说明   
*  
* @author cxz 
* @date 2017年3月27日  新建  
*/
@Controller
@RequestMapping("/task")
public class TaskController {
	
	@RequestMapping(value="/query")
	public ModelAndView getTaskInfo(@RequestParam("openid")String openid,HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		
		String username=Managers.accountManager.getAccount(openid).getUsername();
		List<TaskInfo> list=WitestManager.getTaskInfo(username);
		ModelAndView mv=new ModelAndView();
		mv.addObject("list", list);
		mv.setViewName("mytask");
		return mv;
	}
	
	@RequestMapping(value="/grade")
	public ModelAndView getTaskGrade(@RequestParam("openid")String openid,HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		
		String username=Managers.accountManager.getAccount(openid).getUsername();
		
		List<FinishedTask> list=WitestManager.getFinishedTaskInfo(username);
		
		ModelAndView mv=new ModelAndView();
		mv.addObject("list", list);
		mv.setViewName("mygrade");
		return mv;
	}

	@RequestMapping(value="/manager_grade")
	public ModelAndView workerTask(@RequestParam("openid")String openid,HttpServletRequest request,HttpServletResponse response)throws IOException{

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");

		ModelAndView mv=new ModelAndView();
		Account account=Managers.accountManager.getAccount(openid);

		if(account==null) {
			mv.setViewName("fail");
			mv.addObject("msg","查询失败！");
			mv.addObject("msg_title","请先绑定账号！");
		}

		int id= account.getMoocid();
		List<Task> list=WitestManager.getFinishedTask(id);

		if(list.isEmpty()){
			mv.addObject("msg","查询失败！");
			mv.addObject("msg_title","当前没有可查询的任务！");
			mv.setViewName("danger");
		}
		else {
			mv.addObject("task", list);
			mv.setViewName("manager_grade");
		}
		return mv;
	}

	@RequestMapping(value="/worker_grade")
	public ModelAndView workerGrade(@RequestParam("id")String id,HttpServletResponse response,HttpServletRequest request)throws IOException{

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		String name=request.getParameter("name");

		System.out.println(name);

		List<Grade> list=WitestManager.getWorkersGrade(id);
		ModelAndView mv=new ModelAndView();

		if(list.isEmpty()){
			mv.addObject("msg","查询失败！");
			mv.addObject("msg_title","此任务暂时无法查看成绩！");
			mv.setViewName("fail");
		}else {
			mv.setViewName("workers_grade");
			mv.addObject("grade", list);
			mv.addObject("name",name);
		}
		return mv;
	}

	@RequestMapping(value="/manager_task")
	public ModelAndView managerTask(@RequestParam("openid")String openid,HttpServletRequest request,HttpServletResponse response)throws IOException{

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");

		ModelAndView mv=new ModelAndView();

		Account account=Managers.accountManager.getAccount(openid);
		if(account==null){
			mv.setViewName("fail");
			mv.addObject("msg","查询失败！");
			mv.addObject("msg_title","请先绑定账号！");
		}
		int id=account.getMoocid();
		List<Task> list=WitestManager.getCurrentTask(id);
		List<Task> list1=WitestManager.getUnstartedTask(id);

		if(list.isEmpty()&&list1.isEmpty()){
			mv.addObject("msg","查询失败！");
			mv.addObject("msg_title","当前没有可查询的任务！");
			mv.setViewName("danger");
		}
		else {
			mv.addObject("current", list);
			mv.addObject("unstart", list1);
			mv.setViewName("manager_task");
		}
		return mv;
	}

	@RequestMapping(value="/worker_task")
	public ModelAndView workerTask(@RequestParam("id")String id,HttpServletResponse response,HttpServletRequest request)throws IOException{

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");

		String name =request.getParameter("name");
		ModelAndView mv=new ModelAndView();
		List<Password> list=WitestManager.getWorkersPassword(id);
		if(list.isEmpty()) {
			mv.addObject("msg", "查询失败！");
			mv.addObject("msg_title", "此任务暂时无法查看密码！");
			mv.setViewName("fail");
		}else{
			mv.addObject("pwd",list);
			mv.addObject("name", name);
			mv.setViewName("workers_task");
		}
		return mv;
	}
}
