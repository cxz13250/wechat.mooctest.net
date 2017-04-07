package com.mooctest.weixin.pojo;

import java.util.Date;
import java.util.Map;

import com.mooctest.weixin.manager.WitestManager;
import com.mooctest.weixin.message.NewsMessage;
import com.mooctest.weixin.message.TextMessage;
import com.mooctest.weixin.session.Operation;
import com.mooctest.weixin.session.Session;
import com.mooctest.weixin.session.SessionItem;
import com.mooctest.weixin.util.MessageUtil;



public class UserRequest {
	private Map<String, String> requestMap;
	private String fromUserName; // 发送方帐号
	private String toUserName; // 开发者微信号
	private String msgType; // 消息类型
	private String content; // 内容
	private String createTime; // 生成时间
	private TextMessage textMessage;
	private NewsMessage newsMessage;
	private String resultXml;
	private int identity;

	public UserRequest(Map<String, String> requestMap) {
		setRequestMap(requestMap);
		setResultXml("");
		try {
			fromUserName = requestMap.get("FromUserName"); // 发送方帐号
			toUserName = requestMap.get("ToUserName"); // 开发者微信号
			msgType = requestMap.get("MsgType"); // 消息类型
			content = requestMap.get("Content"); // 内容
			createTime = requestMap.get("CreateTime"); // 生成时间
			textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			identity=WitestManager.identity(fromUserName); // 判断是否已经绑定慕测账户
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//返回任务密码url
	public String taskUrl(){
		return WitestManager.task_page+"?openid="+fromUserName;
	}
	
	//返回账号信息url
	public String accountUrl(){
		return WitestManager.account_page+"?openid="+fromUserName;
	}
	
	//返回账号绑定url
	public String bindUrl(){
		return WitestManager.bind_page +"?openid="+ fromUserName;
	}
	
	//返回任务成绩url
	public String gradeUrl(){
		return WitestManager.grade_page +"?openid="+ fromUserName;
	}
		
	//返回群组信息url
	public String groupUrl(){
		return WitestManager.group_page +"?openid="+ fromUserName;
	}
	
	public SessionItem getSession(){
		if (!this.hasSession()) {
			this.initSession();
		}
		return Session.get(fromUserName);
	}
	
	public void initSession(){
		if (!this.hasSession()){
			SessionItem si = new SessionItem(Operation.OPER0, Operation.STAGE0);
			Session.set(fromUserName, si);
		}
	}
	
	public void removeSession(){
		Session.remove(fromUserName);
	}
	
	public boolean hasSession(){
		return Session.contains(fromUserName);
	}
	
	public boolean isProcessed(){
		return !resultXml.isEmpty();
	}

	public Map<String, String> getRequestMap() {
		return requestMap;
	}

	public void setRequestMap(Map<String, String> requestMap) {
		this.requestMap = requestMap;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getResultXml() {
		return resultXml;
	}

	public void setResultXml(String resultXml) {
		this.resultXml = resultXml;
	}

	public TextMessage getTextMessage() {
		return textMessage;
	}

	public void setTextMessage(TextMessage textMessage) {
		this.textMessage = textMessage;
	}

	public NewsMessage getNewsMessage() {
		return newsMessage;
	}

	public void setNewsMessage(NewsMessage newsMessage) {
		this.newsMessage = newsMessage;
	}

	public int getIdentity() {
		return identity;
	}

	public void setIdentity(int identity) {
		this.identity = identity;
	}

	

}
