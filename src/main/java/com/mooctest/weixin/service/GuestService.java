package com.mooctest.weixin.service;

import org.apache.log4j.Logger;

import com.mooctest.weixin.manager.LoggerManager;
import com.mooctest.weixin.pojo.UserRequest;
import com.mooctest.weixin.util.MessageUtil;
import com.mooctest.weixin.util.NewsMessageUtil;


/**
 * 服务类
 * 
 * @author
 * @date
 */
public class GuestService {
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return xml
	 */
	private static Logger logger = Logger.getLogger(GuestService.class);
	//private static String Subscribe_Info = "欢迎关注慕测平台！Web请访问mooctest.net!\n\n慕测平台功能包括：Java测试驱动编程，Java度量驱动编程，JavaBug修复，Java覆盖测试，JavaBug测试。\n\nC++和Python相关客户端将于2014年年底发布，Appium和Jmeter客户端将于2015年3年发布。\n\n微信端将于11月22日开通。微信端功能包括：密码，成绩，小测统计，小测（单选，多选，其他），注册，关联和客服。";
	
    public static String processRequest(UserRequest userRequest) {
		String respXml = "";
		String respContent = "未知的消息类型！";

		try {
			String fromUserName = userRequest.getFromUserName(); // 发送方帐号
			String toUserName = userRequest.getToUserName(); // 开发者微信号
			String msgType = userRequest.getMsgType(); // 消息类型
			String content = userRequest.getContent(); // 内容
			String createTime = userRequest.getContent(); // 生成时间
			LoggerManager.info(logger, "(UserRequest)[" + msgType + "]"
					+ createTime + ":" + fromUserName + "---->" + toUserName);

			// 处理Session
			if (processSession(userRequest)) {
				return userRequest.getResultXml();
			}		
			// 处理Text
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				LoggerManager.info(logger, "(StudentText)" + createTime + ":" + fromUserName + "---->" + toUserName + ":" + content);
				respContent = "您发送的是文本消息！";
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "您发送的是图片消息！";
			}
			// 语音消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "您发送的是语音消息！";
			}
			// 视频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
				respContent = "您发送的是视频消息！";
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "您发送的是地理位置消息！";
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接消息！";
			}
			
			// 处理Event
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = userRequest.getRequestMap().get("Event");
				// 关注
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					processSubscribeEvent(userRequest);
					return userRequest.getResultXml();
				}
				// 取消关注
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// 取消订阅后用户不会再收到公众账号发送的消息，因此不需要回复
				}
				// 扫描带参数二维码
				else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
					processScanEvent(userRequest);
					return userRequest.getResultXml();
					// TODO 处理扫描带参数二维码事件
				}
				// 上报地理位置
				else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {
					// TODO 处理上报地理位置事件
					return null;
					//return userRequest.getResultXml();
				}
				// 自定义菜单事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					String eventKey = userRequest.getRequestMap().get("EventKey");
					if (eventKey.equals("account")) {
						processBingding(userRequest);
						return userRequest.getResultXml();
					} else if (eventKey.equals("mytask")) {
						processBingding(userRequest);
						return userRequest.getResultXml();
					}else if (eventKey.equals("taskgrade")) {
						processBingding(userRequest);
						return userRequest.getResultXml();
					}else if (eventKey.equals("mygroup")){
						processBingding(userRequest);
						return userRequest.getResultXml();
					}else if (eventKey.equals("help")){
						processHelpMessage(userRequest);
						return userRequest.getResultXml();
					}
				}
			}
			userRequest.getTextMessage().setContent(respContent);
			userRequest.setResultXml(MessageUtil.messageToXml(userRequest.getTextMessage()));
			return userRequest.getResultXml();
		} catch (Exception e) {
			logger.error(e.getStackTrace());
			e.printStackTrace();
		}

		return respXml;
	}
    
	//提示用户进入账号绑定页面
	protected static void processBingding(UserRequest userRequest) {
		String respContent = "请点击<a href='" + userRequest.bindUrl() + "'>绑定页面</a>进行绑定";
		userRequest.getTextMessage().setContent(respContent);
		userRequest.setResultXml(MessageUtil.messageToXml(userRequest.getTextMessage()));
	}
    
	//使用帮助图文消息
	protected static void processHelpMessage(UserRequest userRequest) {
		userRequest.getTextMessage().setContent(NewsMessageUtil.getHelpMessage(userRequest));		
	}

	protected static void processScanEvent(UserRequest userRequest) {
		String eventKey = userRequest.getRequestMap().get("EventKey");
		userRequest.getTextMessage().setContent(eventKey);
		userRequest.setResultXml(MessageUtil.messageToXml(userRequest.getTextMessage()));
	}

	//用户关注回复消息
	protected static void processSubscribeEvent(UserRequest userRequest) {
		//String respContent = "您好！欢迎关注软件测试MOOC(STMOOC)! 软件测试MOOC课程将于2015年2月在Coursera平台发布。在这期间我们将陆续发布课程录制进展和内测版本（包括视频录像和练习题）。欢迎试用！";
		userRequest.getTextMessage().setContent(NewsMessageUtil.getSubscribeMessage(userRequest));
		//userRequest.setResultXml(MessageUtil.messageToXml(userRequest.getTextMessage()));
	}

	protected static boolean processSession(UserRequest userRequest){
		boolean result = false;
		if (!userRequest.hasSession()){
			return result;
		}
		result = true;
		return result;
	}
}
