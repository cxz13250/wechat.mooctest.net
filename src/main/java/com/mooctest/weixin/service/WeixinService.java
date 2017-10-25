package com.mooctest.weixin.service;


import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.mooctest.weixin.manager.LoggerManager;
import com.mooctest.weixin.pojo.UserRequest;
import com.mooctest.weixin.util.MessageUtil;


/**
 * 核心服务类
 * 
 * @author cxz
 * @date 2017:03:22
 */
public class WeixinService {
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return xml
	 */
	private static Logger logger = Logger.getLogger(WeixinService.class); 
	
	public static String processRequest(HttpServletRequest request) {
		// xml格式的消息数据
		String respXml = null;
		
		try {
			// 调用parseXml方法解析请求消息
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			UserRequest userRequest = new UserRequest(requestMap);
			if (userRequest.getIdentity()==0){
				LoggerManager.info(logger, "(MoocUserRequest)" +userRequest.getFromUserName() 
						+ "---->"+ userRequest.getToUserName());
				return MoocUserService.processRequest(userRequest);
			}if(userRequest.getIdentity()==1){
				LoggerManager.info(logger, "(TeacherRequest)" +userRequest.getFromUserName() 
				+ "---->"+ userRequest.getToUserName());
				return TeacherService.processRequest(userRequest);
			}else{
				LoggerManager.info(logger, "(GuestRequest)" +userRequest.getFromUserName() 
						+ "---->"+ userRequest.getToUserName());
				return GuestService.processRequest(userRequest);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respXml;
	}
}
