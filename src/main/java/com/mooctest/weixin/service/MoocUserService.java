package com.mooctest.weixin.service;

import java.util.Date;
import java.util.List;

import com.mooctest.weixin.message.Image;
import com.mooctest.weixin.message.ImageMessage;
import com.mooctest.weixin.pojo.WeixinMedia;
import com.mooctest.weixin.util.CommonUtil;
import org.apache.log4j.Logger;

import com.mooctest.weixin.manager.LoggerManager;
import com.mooctest.weixin.manager.Managers;
import com.mooctest.weixin.model.QuizItem;
import com.mooctest.weixin.model.RollcallItem;
import com.mooctest.weixin.pojo.UserRequest;
import com.mooctest.weixin.util.MessageUtil;
import com.mooctest.weixin.util.NewsMessageUtil;


/**  
* 类说明   
*  
* @author  
* @date 2017年3月30日  新建  
*/
public class MoocUserService extends GuestService{

	
	private static Logger logger = Logger.getLogger(MoocUserService.class);
	
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
				if(CommonUtil.isNumeric(content)){
					processContest(userRequest);
				}
				return userRequest.getResultXml();
//				respContent = "您发送的是文本消息！";
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
					} else if (eventKey.equals("mytask")) {
						processMyTask(userRequest);
						return userRequest.getResultXml();
					} else if (eventKey.equals("taskgrade")){
						processGrade(userRequest);
						return userRequest.getResultXml();
					} else if (eventKey.equals("mygroup")){
						processGroup(userRequest);
						return userRequest.getResultXml();
					} else if (eventKey.equals("help")){
						processHelpMessage(userRequest);
						return userRequest.getResultXml();
					} else if (eventKey.equals("exam")){
						processQuiz(userRequest);
						return userRequest.getResultXml();
					} else if(eventKey.equals("rollcall")){
						processRollcall(userRequest);
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
  	
    //提示用户进入任务密码页面
    protected static void processMyTask(UserRequest userRequest){
    	String respContent= "请点击<a href='"+userRequest.taskUrl()+"'>我的任务</a>查看任务密码";
		userRequest.getTextMessage().setContent(respContent);
		userRequest.setResultXml(MessageUtil.messageToXml(userRequest.getTextMessage()));
    }
    
    //提示用户进入任务成绩页面
    protected static void processGrade(UserRequest userRequest){
    	String respContent= "请点击<a href='"+userRequest.gradeUrl()+"'>任务成绩</a>查看成绩";
    	userRequest.getTextMessage().setContent(respContent);
    	userRequest.setResultXml(MessageUtil.messageToXml(userRequest.getTextMessage()));
    }
    
    //提示用户进入我的群组页面
    protected static void processGroup(UserRequest userRequest){
    	String respContent="请点击<a href='"+userRequest.groupUrl()+"'>我的群组</a>查看群组";
    	userRequest.getTextMessage().setContent(respContent);
    	userRequest.setResultXml(MessageUtil.messageToXml(userRequest.getTextMessage()));
    }
    
    //提示用户进入小测页面
    protected static void processQuiz(UserRequest userRequest){
		userRequest.removeSession();
		String respContent;
		List<QuizItem> quiz = Managers.quizManager.getQuizItem(userRequest.getFromUserName());
		if (quiz == null){
			respContent = "您暂无参与的小测！";
			userRequest.getTextMessage().setContent(respContent);
			userRequest.setResultXml(MessageUtil.messageToXml(userRequest.getTextMessage()));
		}else {
			String xml = NewsMessageUtil.createQuizPageXml2(quiz, userRequest);
			userRequest.setResultXml(xml);
		}
	}
    
    //提示用户进入签到页面
    protected static void processRollcall(UserRequest userRequest){
    	userRequest.removeSession();
    	String respContent;
    	RollcallItem rItem=Managers.rollcallManager.getRollcall(userRequest.getFromUserName());
    	if(rItem==null){
    		respContent = "您暂无参与的点名！";
    		userRequest.getTextMessage().setContent(respContent);
			userRequest.setResultXml(MessageUtil.messageToXml(userRequest.getTextMessage()));
    	}else{
			userRequest.setResultXml(NewsMessageUtil.createRollcallXml(userRequest));
		}
    }

    protected static void processContest(UserRequest userRequest){
		userRequest.removeSession();
		String respContent;
		WeixinMedia media=Managers.contestManager.getContestInfo(userRequest.getContent(),userRequest.getFromUserName());
		if(media==null) {
			respContent = "您尚未参加任何考试！";
			userRequest.getTextMessage().setContent(respContent);
			userRequest.setResultXml(MessageUtil.messageToXml(userRequest.getTextMessage()));
		}else {
			Image image=new Image();
			image.setMediaId(media.getMediaId());
			ImageMessage imageMessage=new ImageMessage();
			imageMessage.setFromUserName(userRequest.getToUserName());
			imageMessage.setToUserName(userRequest.getFromUserName());
			imageMessage.setImage(image);
			imageMessage.setMsgType(media.getType());
			imageMessage.setCreateTime(new Date().getTime());
			userRequest.setResultXml(MessageUtil.messageToXml(imageMessage));
		}
	}
}
