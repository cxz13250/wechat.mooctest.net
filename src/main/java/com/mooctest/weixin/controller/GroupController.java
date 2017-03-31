package com.mooctest.weixin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/group")
public class GroupController {

	@RequestMapping(value="/query")
	public ModelAndView getMyGroup(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv=new ModelAndView();
		return mv;
	}
}
