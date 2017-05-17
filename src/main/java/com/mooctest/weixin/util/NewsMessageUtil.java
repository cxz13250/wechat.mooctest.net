package com.mooctest.weixin.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mooctest.weixin.manager.Managers;
import com.mooctest.weixin.manager.WitestManager;
import com.mooctest.weixin.message.Article;
import com.mooctest.weixin.message.NewsMessage;
import com.mooctest.weixin.model.QuizItem;
import com.mooctest.weixin.pojo.UserRequest;

import javax.jws.soap.SOAPBinding;


/**
 * 图文消息发送工具类(没完成)
 * 
 * @author 
 * @date 
 */
@Service
public class NewsMessageUtil {
	
	public static Article subscribe_message_article;
	public static Article help_message_article;
	public static Article stu_score_article;
	public static Article tea_score_article;
	
	static {
	    String description, url;
	    String appid = Managers.config.getAppid();
	    String baseUrl = Managers.config.getBaseUrl();
	    // subscribe_message_article
	    subscribe_message_article = new Article();
	    subscribe_message_article.setTitle("感谢您的关注！");
        description = "";
        description += "慕测平台功能包括：Java测试驱动编程，Java度量驱动编程，JavaBug修复，Java覆盖测试，JavaBug测试。C++和Python相关客户端将于2014年年底发布，Appium和Jmeter客户端将于2015年3年发布。\n\n";
        description += "微信端功能包括：考试密码，考试成绩，小测，点名，注册，关联和吐槽。请选择下方按钮使用我们的服务。\n\n";
        description += "如您有任何疑问，请使用我们的吐槽功能提出您的宝贵意见！";
        description += "\n-------------------------------------";
        description += "\n点击阅读全文查看更多内容";
        subscribe_message_article.setDescription(description);
        url = baseUrl + "q/help/info";
        subscribe_message_article.setUrl(url);
        
        // help_message_article
        help_message_article = new Article();
        help_message_article.setTitle(" 使用说明");
        description = "";
        description += "微信端功能包括：考试密码，考试成绩，小测，点名，注册，关联和吐槽。请选择下方按钮使用我们的服务。\n\n";
        description += "如您有任何疑问，请使用我们的吐槽功能提出您的宝贵意见！";
        description += "\n-------------------------------------";
        description += "\n点击阅读全文查看更多内容";
        help_message_article.setDescription(description);
        url = baseUrl + "q/help/info";
        help_message_article.setUrl(url);
        
        // stu_score_article
        stu_score_article = new Article();
        stu_score_article.setTitle("成绩查看");
        description = "";
        description += "点击阅读全文查看成绩";
        stu_score_article.setDescription(description);
        url = AdvancedUtil.makeOauth2Url(baseUrl + "q/exam/stu_recent_score", appid, 1);
        stu_score_article.setUrl(url);
        
        // tea_score_article
        tea_score_article = new Article();
        tea_score_article.setTitle("此功能仅对学生开放");
        description = "";
        tea_score_article.setDescription(description);
        url = baseUrl + "q/exam/tea_recent_score";
        tea_score_article.setUrl(url);
	}
	
	public static String createXMLs(int index, String content,
			String fromUserName, String toUserName) {
		String xml = "";
		List<Article> articleList = new ArrayList<Article>();
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setArticleCount(articleList.size());
		newsMessage.setArticles(articleList);

		xml = MessageUtil.messageToXml(newsMessage);

		return xml;
	}
	
	//生成参与点名图文消息
	public static String createRollcallXml(UserRequest userRequest) {
        Article article = new Article();
        article.setTitle("课堂点名");
        String description = "在教室内参与点名才算有效的哦！\n请允许浏览器获取您的地址！";
        description += "\n-------------------------------------\n";
        description += "请点击阅读全文参与点名";
        article.setDescription(description);
        String url = WitestManager.rollcall_join_page+"?openid=" + userRequest.getFromUserName();
        article.setUrl(url);
        List<Article> articles = new ArrayList<Article>();
        articles.add(article);
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setToUserName(userRequest.getFromUserName());
        newsMessage.setFromUserName(userRequest.getToUserName());
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        newsMessage.setArticleCount(articles.size());
        newsMessage.setArticles(articles);
        String respXml = MessageUtil.messageToXml(newsMessage);
        return respXml;
    }
    
	//生成点名结果图文消息
    public static String createRollcallResultXml(String description, String toUserName, String fromUserName, int rollcallId){
        
        Article article = new Article();
        article.setTitle("点名结果");

        article.setDescription(description);
        String url = WitestManager.rollcall_result_page;
        url = url.replace("ROLLCALLID", String.valueOf(rollcallId));
        url = url.replace("OPENID", CommonUtil.urlEncodeUTF8(fromUserName));
        article.setUrl(url);
        List<Article> articles = new ArrayList<Article>();
        articles.add(article);
        
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        newsMessage.setArticleCount(articles.size());
        newsMessage.setArticles(articles);
        
        String respXml = MessageUtil.messageToXml(newsMessage);
        return respXml;
    }
    
    //生成创建点名图文消息
    public static String createRollcallCreatorXml(UserRequest userRequest){
        Article article = new Article();
        article.setTitle("创建点名");
        String description = "请点击阅读全文创建点名";
        article.setDescription(description);
        String url = Managers.config.getBaseUrl() + "q/rollcall/create?openid=" + CommonUtil.urlEncodeUTF8(userRequest.getFromUserName());
        article.setUrl(url);
        List<Article> articles = new ArrayList<Article>();
        articles.add(article);
        
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setToUserName(userRequest.getFromUserName());
        newsMessage.setFromUserName(userRequest.getToUserName());
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        newsMessage.setArticleCount(articles.size());
        newsMessage.setArticles(articles);
        
        String respXml = MessageUtil.messageToXml(newsMessage);
        return respXml;
    }
	
	//生成订阅之后返回消息
	public static String getSubscribeMessage(UserRequest userRequest){
		List<Article> articles = new ArrayList<Article>();
		articles.add(subscribe_message_article);
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(userRequest.getFromUserName());
		newsMessage.setFromUserName(userRequest.getToUserName());
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setArticleCount(articles.size());
		newsMessage.setArticles(articles);
		
		String respXml = MessageUtil.messageToXml(newsMessage);
		userRequest.setResultXml(respXml);
		return respXml;
	}
	
	//生成使用帮助图文消息
	public static String getHelpMessage(UserRequest userRequest){
		List<Article> articles = new ArrayList<Article>();
		articles.add(help_message_article);
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(userRequest.getFromUserName());
		newsMessage.setFromUserName(userRequest.getToUserName());
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setArticleCount(articles.size());
		newsMessage.setArticles(articles);
		
		String respXml = MessageUtil.messageToXml(newsMessage);
		userRequest.setResultXml(respXml);
		return respXml;
	}
	
	//生成老师小测结果提示图文消息
	public static String createQuizResultXml(String description, String toUserName, String fromUserName, int quizId){
	    
	    Article article = new Article();
	    article.setTitle("小测结果");
	    article.setDescription(description);
	    String url = WitestManager.quiz_result_page;
	    url = url.replace("QUIZID", String.valueOf(quizId));
	    article.setUrl(url);
	    List<Article> articles = new ArrayList<Article>();
	    articles.add(article);
	    
	    NewsMessage newsMessage = new NewsMessage();
	    newsMessage.setToUserName(fromUserName);
	    newsMessage.setFromUserName(toUserName);
	    newsMessage.setCreateTime(new Date().getTime());
	    newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
	    newsMessage.setArticleCount(articles.size());
	    newsMessage.setArticles(articles);
	    
	    String respXml = MessageUtil.messageToXml(newsMessage);
	    return respXml;
	}
	
	//生成创建小测图文消息
	public static String createQuizCreatorXml(UserRequest userRequest){
		Article article = new Article();
		article.setTitle("创建新的小测");
		String description = "请点击阅读全文创建新的小测";
		article.setDescription(description);
		String url = WitestManager.createquiz_page+"?openid=" + CommonUtil.urlEncodeUTF8(userRequest.getFromUserName());
		article.setUrl(url);
		
		List<Article> articles = new ArrayList<Article>();
		articles.add(article);
		
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(userRequest.getFromUserName());
		newsMessage.setFromUserName(userRequest.getToUserName());
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setArticleCount(articles.size());
		newsMessage.setArticles(articles);
		
		String respXml = MessageUtil.messageToXml(newsMessage);
		return respXml;
	}
	
	
	//生成学生小测内容图文消息
	public static String createQuizPageXml2(List<QuizItem> quiz, UserRequest userRequest){
        Article article = new Article();
        article.setTitle("小测");
        
        String description = "您参与的小测共有" + quiz.size()+"道题";
        description += "\n请点击阅读全文进行回答";

        article.setDescription(description);
        
        String fromUserName = userRequest.getFromUserName();
        String url = WitestManager.showquiz_page+"?openid=" + CommonUtil.urlEncodeUTF8(fromUserName);
        article.setUrl(url);
        
        List<Article> articles = new ArrayList<Article>();
        articles.add(article);
        
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(userRequest.getToUserName());
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        newsMessage.setArticleCount(articles.size());
        newsMessage.setArticles(articles);
        
        String respXml = MessageUtil.messageToXml(newsMessage);
        return respXml;
    }
	
	//生成老师查看小测结果的图文消息
	public static String createQuizResultXml2(UserRequest userRequest){
		String description="请点击阅读全文查看小测结果";
		Article article=new Article();
		article.setDescription(description);
		article.setTitle("小测结果");
		article.setUrl(userRequest.resultUrl());
		
		List<Article> articles=new ArrayList<Article>();
		articles.add(article);
		
		NewsMessage newsMessage=new NewsMessage();
		newsMessage.setArticleCount(articles.size());
		newsMessage.setArticles(articles);
		newsMessage.setFromUserName(userRequest.getToUserName());
		newsMessage.setToUserName(userRequest.getFromUserName());
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		String respXml=MessageUtil.messageToXml(newsMessage);
		return respXml;
	}

	//生成群主查看任务成绩的图文消息
	public static String createGradeCreatorXml(UserRequest userRequest){
		String description="请点击阅读全文查看任务成绩";
		String url=WitestManager.manager_grade_page+"?openid="+CommonUtil.urlEncodeUTF8(userRequest.getFromUserName());
		Article article=new Article();
		article.setDescription(description);
		article.setTitle("任务成绩");
		article.setUrl(url);

		List<Article> articles=new ArrayList<Article>();
		articles.add(article);

		NewsMessage newsMessage=new NewsMessage();
		newsMessage.setArticleCount(articles.size());
		newsMessage.setArticles(articles);
		newsMessage.setFromUserName(userRequest.getToUserName());
		newsMessage.setToUserName(userRequest.getFromUserName());
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		String respXml=MessageUtil.messageToXml(newsMessage);
		return respXml;
	}

	//生成群主查看任务密码的图文消息
	public  static String CreatePasswordXml(UserRequest userRequest){
		String description="请点击阅读全文查看任务密码";
		String url=WitestManager.manager_task_page+"?openid="+CommonUtil.urlEncodeUTF8(userRequest.getFromUserName());
		Article article=new Article();
		article.setDescription(description);
		article.setTitle("任务密码");
		article.setUrl(url);

		List<Article> articles=new ArrayList<Article>();
		articles.add(article);

		NewsMessage newsMessage=new NewsMessage();
		newsMessage.setArticleCount(articles.size());
		newsMessage.setArticles(articles);
		newsMessage.setFromUserName(userRequest.getToUserName());
		newsMessage.setToUserName(userRequest.getFromUserName());
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		String respXml=MessageUtil.messageToXml(newsMessage);
		return respXml;
	}
}
