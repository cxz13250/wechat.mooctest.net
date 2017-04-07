package com.mooctest.weixin.controller;

import com.mooctest.weixin.manager.Managers;
import com.mooctest.weixin.manager.WitestManager;
import com.mooctest.weixin.model.Account;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**  
* 类说明   
*  
* @author  cxz
* @date 2017年3月20日  新建  
*/
@Controller
@RequestMapping("/account")
public class AccountController {
	
	@RequestMapping(value="/new")
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
	
	@RequestMapping(value="/check",method = RequestMethod.POST)
	public ModelAndView checkAccount(HttpServletRequest request,HttpServletResponse response) throws IOException{

		String openid = request.getParameter("openid");
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		int type=Integer.parseInt(request.getParameter("type"));
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		
		ModelAndView mv=new ModelAndView();
		
		int flag=WitestManager.identity(openid);
		if(flag!=2){
			mv.setViewName("danger");
			mv.addObject("msg", "您已经绑定过账号！");
			mv.addObject("msg_title", "绑定失败！");
		}
		else{
			boolean temp=Managers.accountManager.checkAccount1(username);
			if(!temp)
			{
				mv.setViewName("danger");
				mv.addObject("msg","该账号已被绑定！");
				mv.addObject("msg_title","绑定失败！");
			}else {
				if(type==0){
					temp = WitestManager.isMoocUser1(username, password);
					if (!temp) {
						mv.setViewName("fail");
						mv.addObject("msg","账户密码输入错误！");
						mv.addObject("msg_title","绑定失败！");
					} else {
						Account account = new Account();
						account.setUsername(username);
						account.setOpenid(openid);
						account.setType(type);
						Managers.accountManager.saveAccount(account);
						mv.addObject("msg", "您的账户已经成功绑定！");
						mv.addObject("msg_title","绑定成功！");
						mv.setViewName("success");
					}
				}else{
					mv.setViewName("fail");
					mv.addObject("msg","账户密码输入错误！");
					mv.addObject("msg_title","绑定失败！");
				}
			}
		}
		return mv;
	}
	
	@RequestMapping(value="/info")
	public ModelAndView getAccountInfo(@RequestParam("openid")String openid,HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");

		ModelAndView mv=new ModelAndView();
		mv.addObject("openid", openid);
		mv.addObject("title", "我的信息");
		mv.setViewName("account_menu");
		return mv;
	}
	
	@RequestMapping(value="/cancel")
	public ModelAndView cancelBind(@RequestParam("openid")String openid,HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		
		
		Managers.accountManager.deleteAccount(openid);
		ModelAndView mv=new ModelAndView();
		mv.addObject("msg", "您的账户已经成功解绑！");
		mv.addObject("msg_title","解绑成功！");
		mv.setViewName("success");
		return mv;
	}
}





