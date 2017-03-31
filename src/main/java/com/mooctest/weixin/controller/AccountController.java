package com.mooctest.weixin.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mooctest.weixin.manager.Managers;
import com.mooctest.weixin.manager.WitestManager;
import com.mooctest.weixin.model.Account;
import com.mooctest.weixin.model.AccountInfo;

/**  
* 类说明   
*  
* @author  cxz
* @date 2017年3月20日  新建  
*/
@Controller
@RequestMapping("/account")
public class AccountController {
	
	@RequestMapping(value="/account/new")
	public ModelAndView toAccountBind(@RequestParam("openid")String openid,HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		
		ModelAndView mv=new ModelAndView();
		mv.addObject("openid", openid);
		mv.addObject("JSApiTicket", Managers.config.getTicket());
		mv.addObject("appid", Managers.config.getAppid());
		mv.setViewName("account_bind");
		return mv;
	}
	
	@RequestMapping(value="/check")
	public ModelAndView checkAccount(@RequestParam("openid")String openid,HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		
		ModelAndView mv=new ModelAndView();
		boolean flag=WitestManager.isMoocUser(username);
		PrintWriter out=response.getWriter();
		
		if(flag==false)
		{
			out.print("该账号已被绑定！");
			mv.setViewName("account_bind");
		}
				
		flag=WitestManager.isMoocUser1(username,password);
		if(flag==false){
			out.print("用户名或密码错误！");
			mv.setViewName("account_bind");
		}
		else{
			Account account=new Account();
			account.setUsername(username);
			account.setOpenid(openid);
			Managers.accountManager.saveAccount(account, openid);
			mv.addObject("message", "绑定成功！");
			mv.setViewName("bind_success");
		}
		return mv;
	}
	
	@RequestMapping(value="/info")
	public ModelAndView getAccountInfo(@RequestParam("openid")String openid,HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");

		AccountInfo accountInfo=Managers.accountManager.getAccountInfoByOpenid(openid);
		ModelAndView mv=new ModelAndView();
		if(accountInfo!=null){
			mv.addObject("accountInFo", accountInfo);
			mv.addObject("JSApiTicket", Managers.config.getTicket());
			mv.addObject("appid", Managers.config.getAppid());
			mv.setViewName("account_menu");
		}
		return mv;
	}
}





