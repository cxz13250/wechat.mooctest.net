package com.mooctest.weixin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mooctest.weixin.manager.Managers;
import com.mooctest.weixin.model.Task;

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
	public ModelAndView queryTask(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		String openid=request.getParameter("openid");
		
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
		
        List<Task> list=Managers.taskManager.getTaskByOpenid(openid);
    	ModelAndView mv=new ModelAndView();

    	List<String> taskname=new ArrayList<>();
        for(Task task : list){
        	taskname.add(task.getName());
        }
        
        mv.addObject("list", taskname);
        mv.addObject("openid",openid);
        mv.addObject("JSApiTicket", Managers.config.getTicket());
        mv.addObject("appid", Managers.config.getAppid());
        mv.setViewName("mytask");
    	return mv;
	}
	
	@RequestMapping(value="taskinfo")
	public ModelAndView queryTaskInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		String openid=request.getParameter("openid");
		String name=request.getParameter("name");
		
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        
        Task task=Managers.taskManager.getTaskByCondition(openid, name);
        ModelAndView mv=new ModelAndView();
        mv.addObject("name", name);
        mv.addObject("advisor", task.getAdvisor());
        mv.addObject("group",task.getGroup());
        mv.addObject("begin", task.getBegin());
        mv.addObject("end", task.getEnd());
        mv.addObject("password", task.getPassword());
        mv.setViewName("task_info");
        return mv;
	}
}
