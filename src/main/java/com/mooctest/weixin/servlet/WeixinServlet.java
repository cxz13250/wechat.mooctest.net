package com.mooctest.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.mooctest.weixin.manager.LoggerManager;
import com.mooctest.weixin.manager.Managers;
import com.mooctest.weixin.service.WeixinService;
import com.mooctest.weixin.util.SignupUtil;




/**
 * 请求处理的核心类
 * 
 * @author cxz
 * @date 2017-03-20
 */
public class WeixinServlet extends HttpServlet {
	private static Logger logger = Logger.getLogger(WeixinServlet.class); 

	private static final long serialVersionUID = 892403490426217658L;
	
	/**
	 * 请求校验（确认请求来自微信服务器）
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	

		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");
		
		PrintWriter out = response.getWriter();
		// 请求校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (SignupUtil.checkSignature(signature, timestamp, nonce, Managers.config.getTokenStr())) {
			LoggerManager.info(logger, "doGet{signature:"+signature+" timestamp:"+timestamp+" nonce:"+nonce+" echostr"+echostr+"}");
			out.print(echostr);
		}else{
			LoggerManager.error(logger, "doGet{signature:"+signature+" timestamp:"+timestamp+" nonce:"+nonce+" echostr"+echostr+"}");
		}
		out.close();
	}

	/**
	 * 处理微信服务器发来的消息
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// 接收参数微信加密签名、 时间戳、随机数
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		
		LoggerManager.info(logger, "doPost{signature:"+signature+" timestamp:"+timestamp+" nonce:"+nonce+"}");
		
		PrintWriter out = response.getWriter();
		// 请求校验
		if (SignupUtil.checkSignature(signature, timestamp, nonce, Managers.config.getTokenStr())) {
			// 调用核心服务类接收处理请求
			String respXml=WeixinService.processRequest(request);
			if (respXml != null) {
				out.print(respXml);
				LoggerManager.info(logger, "respXml:\n"+respXml);
			}
		}else{
			LoggerManager.error(logger, "doPost{signature:"+signature+" timestamp:"+timestamp+" nonce:"+nonce+"}");
		}
		out.close();
	}
	
}
