package com.mooctest.weixin.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mooctest.weixin.data.Task;
import com.mooctest.weixin.message.Image;
import com.mooctest.weixin.message.ImageMessage;
import com.mooctest.weixin.pojo.WeixinMedia;
import com.mooctest.weixin.util.CommonUtil;
import org.apache.log4j.Logger;

import com.mooctest.weixin.data.Group;
import com.mooctest.weixin.manager.LoggerManager;
import com.mooctest.weixin.manager.Managers;
import com.mooctest.weixin.manager.WitestManager;
import com.mooctest.weixin.model.Quiz;
import com.mooctest.weixin.pojo.UserRequest;
import com.mooctest.weixin.util.MessageUtil;
import com.mooctest.weixin.util.NewsMessageUtil;


public class TeacherService extends GuestService{

	private static Logger logger=Logger.getLogger(TeacherService.class);
	
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
				if(content.equals("比赛")){
					processContest(userRequest);
				}
				return userRequest.getResultXml();
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
						processAccount(userRequest);
						return userRequest.getResultXml();
					}else if(eventKey.equals("exam")){
						processQuiz(userRequest);
						return userRequest.getResultXml();
					}else if(eventKey.equals("exam_score")){
						processResult(userRequest);
						return userRequest.getResultXml();
					}else if(eventKey.equals("rollcall")){
						processRollcall(userRequest);
						return userRequest.getResultXml();
					}else if(eventKey.equals("taskgrade")){
						processGrade(userRequest);
						return userRequest.getResultXml();
					}else if(eventKey.equals("mytask")){
						processPassword(userRequest);
						return userRequest.getResultXml();
					}else if (eventKey.equals("monthly")){
						processMonthlyMatch(userRequest);
						return userRequest.getResultXml();
					} else {
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

	//提示用户进入账号信息页面
	protected static void processAccount(UserRequest userRequest){
		String respContent= "请点击<a href='"+userRequest.accountUrl()+"'>账号页面</a>查看账号信息";
		userRequest.getTextMessage().setContent(respContent);
		userRequest.setResultXml(MessageUtil.messageToXml(userRequest.getTextMessage()));
	}	
	
	//提示老师进入创建小测页面
	protected static void processQuiz(UserRequest userRequest){
		String respContent = "";
		int id=Managers.accountManager.getAccount(userRequest.getFromUserName()).getMoocid();
		List<Group> groups = WitestManager.getGroup2(id);
		if (groups.isEmpty()){
			respContent = "您还没有建立任何班级，赶紧登录mooctest.net建立班级吧！";
			userRequest.removeSession();
			userRequest.getTextMessage().setContent(respContent);
			userRequest.setResultXml(MessageUtil.messageToXml(userRequest.getTextMessage()));
			return;
		}
		userRequest.removeSession();
		Timestamp quizTime = new Timestamp(new Date().getTime());
		if (Managers.quizManager.closeQuiz(userRequest,quizTime)) return;
		else{
			String xml = NewsMessageUtil.createQuizCreatorXml(userRequest);
			userRequest.setResultXml(xml);
		}
	}
	
	//提示老师进入小测结果页面
	protected static void processResult(UserRequest userRequest){
		
		String respContent="";
		Quiz quiz=Managers.quizManager.getQuiz(userRequest.getFromUserName());
		if(quiz==null){
			respContent = "您还没有发布小测，请点击小测菜单创建小测！";
			userRequest.removeSession();
			userRequest.getTextMessage().setContent(respContent);
			userRequest.setResultXml(MessageUtil.messageToXml(userRequest.getTextMessage()));
			return;
		}
		userRequest.removeSession();
		String xml=NewsMessageUtil.createQuizResultXml2(userRequest);
		userRequest.setResultXml(xml);
	}
	
	//提示老师进入创建点名页面
	protected static void processRollcall(UserRequest userRequest) {
		String respContent="";
		userRequest.removeSession();
		int id=Managers.accountManager.getAccount(userRequest.getFromUserName()).getMoocid();
		List<Group> groups = WitestManager.getGroup2(id);
		if (groups.isEmpty()){
			respContent = "您还没有建立任何班级，赶紧登录mooctest.net建立班级吧";
			userRequest.removeSession();
			userRequest.getTextMessage().setContent(respContent);
			userRequest.setResultXml(MessageUtil.messageToXml(userRequest.getTextMessage()));
			return;
		}
		if (Managers.rollcallManager.closeRollcall(userRequest)) return;
		else {
			String rollcallCreatorXml = NewsMessageUtil.createRollcallCreatorXml(userRequest);
			userRequest.setResultXml(rollcallCreatorXml);
		}
	}

	//提示老师进入查看任务成绩页面
	protected  static void processGrade(UserRequest userRequest){
		String respContent="";
		userRequest.removeSession();
		int id=Managers.accountManager.getAccount(userRequest.getFromUserName()).getMoocid();
		List<Task> list=WitestManager.getFinishedTask(id);
		if (list.isEmpty()){
			respContent = "您当前没有已结束的任务，请登录mooctest.net查看！";
			userRequest.removeSession();
			userRequest.getTextMessage().setContent(respContent);
			userRequest.setResultXml(MessageUtil.messageToXml(userRequest.getTextMessage()));
			return;
		}else{
			String getGradeCreatorXml=NewsMessageUtil.createGradeCreatorXml(userRequest);
			userRequest.setResultXml(getGradeCreatorXml);
		}
	}

	protected static void processPassword(UserRequest userRequest){
		String respContent="";
		userRequest.removeSession();
		int id=Managers.accountManager.getAccount(userRequest.getFromUserName()).getMoocid();
		List<Task> list=WitestManager.getUnstartedTask(id);
		List<Task> list1=WitestManager.getCurrentTask(id);
		if (list.isEmpty()&&list1.isEmpty()){
			respContent = "您还没有创建任务，赶紧登录mooctest.net发布任务吧！";
			userRequest.removeSession();
			userRequest.getTextMessage().setContent(respContent);
			userRequest.setResultXml(MessageUtil.messageToXml(userRequest.getTextMessage()));
			return;
		}else{
			String getPassowrdXml=NewsMessageUtil.CreatePasswordXml(userRequest);
			userRequest.setResultXml(getPassowrdXml);
		}
	}

	protected static void processContest(UserRequest userRequest){
		userRequest.removeSession();
		try {
			WeixinMedia media = Managers.contestManager.getWorkersContest2(userRequest.getContent(), userRequest.getFromUserName());
			if (media == null) {
				return;
			} else {
				Image image = new Image();
				image.setMediaId(media.getMediaId());
				ImageMessage imageMessage = new ImageMessage();
				imageMessage.setFromUserName(userRequest.getToUserName());
				imageMessage.setToUserName(userRequest.getFromUserName());
				imageMessage.setImage(image);
				imageMessage.setMsgType(media.getType());
				imageMessage.setCreateTime(new Date().getTime());
				userRequest.setResultXml(MessageUtil.messageToXml(imageMessage));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
