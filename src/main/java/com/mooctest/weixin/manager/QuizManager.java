package com.mooctest.weixin.manager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mooctest.weixin.dao.AccountDao;
import com.mooctest.weixin.dao.PreparedQuizDao;
import com.mooctest.weixin.dao.QuestionDao;
import com.mooctest.weixin.dao.QuizAnswerDao;
import com.mooctest.weixin.dao.QuizDao;
import com.mooctest.weixin.dao.QuizItemDao;
import com.mooctest.weixin.entity.Worker;
import com.mooctest.weixin.json.JSONObject;
import com.mooctest.weixin.model.Account;
import com.mooctest.weixin.model.PreparedQuiz;
import com.mooctest.weixin.model.Question;
import com.mooctest.weixin.model.Quiz;
import com.mooctest.weixin.model.QuizAnswer;
import com.mooctest.weixin.model.QuizItem;
import com.mooctest.weixin.pojo.UserRequest;
import com.mooctest.weixin.util.NewsMessageUtil;

/**  
* 类说明   
*  
* @author  cxz
* @date 2017年4月11日  新建  
*/
@Service
public class QuizManager {

	@Autowired
	AccountDao accountDao;
	
	@Autowired
	QuizItemDao quizItemDao;
	
	@Autowired
	PreparedQuizDao preparedQuizDao;
	
	@Autowired
	QuizAnswerDao quizAnswerDao;
	
	@Autowired
	QuizDao quizDao;
	
	@Autowired
	QuestionDao questionDao;
	
	//获取学生小测回答内容
	public List<QuizAnswer> getQuizAnswer(int questionId){
        List<QuizAnswer> list = quizAnswerDao.getListByQuizId(questionId);
        return list;
    }
	
	//根据小测id题目获取本次小测
	public List<Question> getQuestion(int quizid){
        List<Question> list = questionDao.getQuestionByQuizid(quizid);
        return list;
    }
	
	//根据题目id获取本题
	public List<Question> getQuestionById(int id){
	    List<Question> list = questionDao.getQuestionById(id);
	    return list;
	}
	
	//创建小测并生成第一个题
	public String startQuiz(List<Worker> workers,Integer groupId, int quizType, 
			 String quizTitle, String quizContent, String teaOpenid) {

		Timestamp quizTime = new Timestamp(new Date().getTime());
		Timestamp endTime=null;
		String openid;
		//生成本次小测信息
		Quiz quiz=new Quiz();
		quiz.setGroupid(groupId);
		quiz.setOpenid(teaOpenid);
		quiz.setStart(quizTime);
		quiz.setEnd(endTime);
		quizDao.saveQuiz(quiz);
		//生成本题信息
		Question question=new Question();
		question.setQuizid(quiz.getId());
		question.setContent(quizContent);
		question.setTitle(quizTitle);
		question.setType(quizType);
		questionDao.saveQuestion(question);
		
		//将本题信息整合学生信息存入表quizitem中
		List<Account> list2=new ArrayList<Account>();
		for (Worker worker : workers) {
			QuizItem quizItem = new QuizItem();
			list2=accountDao.getAccountByUsername(worker.getId());
			if(!list2.isEmpty()){
				openid=list2.get(0).getOpenid();
				quizItem.setWorOpenId(openid);	
				quizItem.setGrade(worker.getGrade());
				quizItem.setGroupid(groupId);
				quizItem.setType(quizType);
				quizItem.setTitle(quizTitle);
				quizItem.setContent(quizContent);
				quizItem.setState(1);
				quizItem.setWorAnswer("");
				quizItem.setManOpenId(teaOpenid);
				quizItem.setCreateTime(quizTime);
				quizItem.setQuizid(quiz.getId());
				quizItem.setQuestionid(question.getId());
				quizItemDao.saveQuizItem(quizItem);
			}
		}
		return String.valueOf(quiz.getId());
	}
	
	//生成本题
	public void creatQuestion(List<Worker> workers,Integer groupId, int quizType, 
			 String quizTitle, String quizContent, String teaOpenid,int quizid){
		
		Timestamp quizTime = new Timestamp(new Date().getTime());
		String openid;
		
		//将本题信息存入表question中
		Question question=new Question();
		question.setContent(quizContent);
		question.setQuizid(quizid);
		question.setTitle(quizTitle);
		question.setType(quizType);
		questionDao.saveQuestion(question);
		
		//将本题信息整合学生信息存入表quizitem中
		List<Account> list2=new ArrayList<Account>();
		for (Worker worker : workers) {
			QuizItem quizItem = new QuizItem();
			list2=accountDao.getAccountByUsername(worker.getId());
			if(!list2.isEmpty()){
				openid=list2.get(0).getOpenid();
				quizItem.setWorOpenId(openid);	
				quizItem.setGrade(worker.getGrade());
				quizItem.setGroupid(groupId);
				quizItem.setType(quizType);
				quizItem.setTitle(quizTitle);
				quizItem.setContent(quizContent);
				quizItem.setState(1);
				quizItem.setWorAnswer("");
				quizItem.setManOpenId(teaOpenid);
				quizItem.setCreateTime(quizTime);
				quizItem.setQuizid(quizid);
				quizItem.setQuestionid(question.getId());
				quizItemDao.saveQuizItem(quizItem);
			}
		}
	}
	
	//提交答案
	public boolean writeQuizAnswer(String stuOpenId, String answer,int index){
		List<QuizItem> list=getQuizItem(stuOpenId);
		QuizItem quizItem = list.get(index);
		Timestamp quizTime = new Timestamp(new Date().getTime());
		QuizAnswer quizAnswer=new QuizAnswer();
		if (quizItem != null){
			quizItem.setWorAnswer(answer);
			quizItemDao.updateQuizItem(quizItem);
			quizAnswer.setQuizid(quizItem.getQuizid());
			quizAnswer.setRecordCreateTime(quizTime);
			quizAnswer.setWorAnswer(answer);
			quizAnswer.setOpenid(stuOpenId);
			quizAnswer.setQuestionid(quizItem.getQuestionid());
			quizAnswerDao.saveQuizAnswer(quizAnswer);
			return true;
		}else{
			return false;
		}
	}
	
	//判断小测是否存在
	public boolean existQuiz(String manOpenId){
		List<QuizItem> qList = quizItemDao.getListByCondition("manOpenId='" + manOpenId + "' and state = 1");
		if (qList == null || qList.isEmpty()){
			return false;
		}
		return true;
	}
	
	//结束小测
	public boolean closeQuiz(UserRequest userRequest,Timestamp quizTime){
		String manOpenid = userRequest.getFromUserName();
		
		List<QuizItem> qList = quizItemDao.getListByCondition("manOpenId='" + manOpenid + "' and state = 1");
		if (qList == null || qList.isEmpty()){
			return false;
		}	
		int quizid=qList.get(0).getQuizid();
		
		List<QuizAnswer> aList=quizAnswerDao.getListByQuizId(quizid);
		List<Question> list=questionDao.getQuestionByQuizid(quizid);
			
		int total = qList.size()/list.size();
		int answered = aList.size()/list.size();
		
		Quiz quiz=quizDao.getQuizById(quizid).get(0);
		quiz.setEnd(quizTime);
		quizDao.updateQuiz(quiz);//设置小测结束时间
		
		for (QuizItem quizItem : qList) {
			quizItem.setState(-1);   //设置state属性:-1为已结束
			quizItemDao.updateQuizItem(quizItem);
		}
		
		String ansInfo = "";
		ansInfo += "小测[已结束]\n";
		ansInfo += "参与总人数：" + total + "\n提交回答人数：" + answered;
		ansInfo += "\n-------------------------------------" + "\n点击阅读全文查看详细结果";	
			
		String toUserName = userRequest.getToUserName();
		String fromUserName = userRequest.getFromUserName();
		String xml = NewsMessageUtil.createQuizResultXml(ansInfo, toUserName, fromUserName, quizid);
		userRequest.setResultXml(xml);
		return true;
	}
	
	//根据群组ID获取备用小测
	public List<PreparedQuiz> getPreparedQuiz(int groupId){
        List<PreparedQuiz> qList = preparedQuizDao.getListByColumnValue("groupId", groupId);
        if(!qList.isEmpty()){
        	return qList;
        }
        else {
			return null;
		}
    }
	
	//根据备用小测ID获取备用小测
	public PreparedQuiz getPreparedQuizById(int id){
        List<PreparedQuiz> list = preparedQuizDao.getListByColumnValue("id", id);
        if (list.isEmpty()){
            return null;
        }else{
            return list.get(0);
        }
    }
	
	//根据学生ID获取小测内容
	public List<QuizItem> getQuizItem(String worOpenId){
		String condition = "worOpenId='" + worOpenId + "' and state=1";
		List<QuizItem> qList = quizItemDao.getListByCondition(condition);
		if (!qList.isEmpty()) {
			return qList;
		}else
			return null;
	}
	
	//根据老师ID获取小测
	public Quiz getQuiz(String manOpenId){
		String condition="openid='"+manOpenId+"' order by start desc";
		List<Quiz> qList=quizDao.getQuizByCondition(condition);
		if(!qList.isEmpty()){
			return qList.get(0);
		}else{
			return null;
		}
	}
	
	//根据老师ID获取小测ID
	public int getQuizIdByOpenid(String openid){
		List<Quiz> list=quizDao.getQuizByOpenId(openid);
		return list.get(0).getId();
	}
	
	public boolean checkQuizContent(PreparedQuiz quiz){
        String content = quiz.getQuizContent();
        int quizType = quiz.getQuizType();
        if (quizType == 1 || quizType == 2){
            try {
                new JSONObject(content);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
}
