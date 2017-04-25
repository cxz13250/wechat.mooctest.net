package com.mooctest.weixin.manager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mooctest.weixin.dao.AccountDao;
import com.mooctest.weixin.dao.PreparedQuizDao;
import com.mooctest.weixin.dao.QuizAnswerDao;
import com.mooctest.weixin.dao.QuizDao;
import com.mooctest.weixin.dao.QuizItemDao;
import com.mooctest.weixin.entity.Worker;
import com.mooctest.weixin.json.JSONObject;
import com.mooctest.weixin.model.Account;
import com.mooctest.weixin.model.PreparedQuiz;
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
	
	//获取学生小测回答内容
	public List<QuizAnswer> getQuizAnswer(int quizId){
        List<QuizAnswer> list = quizAnswerDao.getListByQuizId(quizId);
        return list;
    }
	
	//发起小测
	public void startQuiz(List<Worker> workers,Integer groupId, int quizType, 
			 String quizTitle, String quizContent, String teaOpenid) {

		Timestamp quizTime = new Timestamp(new Date().getTime());
		Timestamp endTime=null;
		String openid;
		//将小测存入表quiz
		Quiz quiz=new Quiz();
		quiz.setContent(quizContent);
		quiz.setGroupid(groupId);
		quiz.setOpenid(teaOpenid);
		quiz.setTitle(quizTitle);
		quiz.setType(quizType);
		quiz.setStart(quizTime);
		quiz.setEnd(endTime);
		quizDao.saveQuiz(quiz);
		
		//将小测信息整合学生信息存入表quizitem中
		List<Quiz> list=quizDao.getQuizByCondition("start='"+quizTime+"' and openid='"+teaOpenid+"'");
		List<Account> list2=new ArrayList<Account>();
		if(!list.isEmpty()){
			quiz=list.get(0);
		}
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
				quizItem.setCreatetime(quizTime);
				quizItem.setQuizId(quiz.getId());
				quizItemDao.saveQuizItem(quizItem);
			}
		}
	}
	
	//提交答案
	public boolean writeQuizAnswer(String stuOpenId, String answer){
		QuizItem quizItem = getQuizItem(stuOpenId);
		Timestamp quizTime = new Timestamp(new Date().getTime());
		QuizAnswer quizAnswer=new QuizAnswer();
		if (quizItem != null){
			quizItem.setWorAnswer(answer);
			quizItemDao.updateQuizItem(quizItem);
			quizAnswer.setQuizId(quizItem.getQuizId());
			quizAnswer.setRecordCreateTime(quizTime);
			quizAnswer.setWorAnswer(answer);
			quizAnswer.setOpenid(stuOpenId);
			quizAnswerDao.saveQiuzAnswer(quizAnswer);
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
		int total = qList.size();
		int answered = 0;
		
		int quizid=qList.get(0).getQuizId();
		Quiz quiz=quizDao.getQuizById(quizid).get(0);
		quiz.setEnd(quizTime);
		quizDao.updateQuiz(quiz);
		
		for (QuizItem quizItem : qList) {
			quizItem.setState(-1);   //设置state属性:-1为已结束
			String answer = quizItem.getWorAnswer();
			if (!(answer == null || answer.equals(""))) {
				answered++;
			}
			quizItemDao.updateQuizItem(quizItem);
		}
		
		String ansInfo = "";
		String quizName = qList.get(0).getTitle();
		ansInfo += "小测：" + quizName + "[已结束]\n";
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
	public QuizItem getQuizItem(String worOpenId){
		String condition = "worOpenId='" + worOpenId + "' and state=1 order by createtime desc";
		List<QuizItem> qList = quizItemDao.getListByCondition(condition);
		if (!qList.isEmpty()) {
			return qList.get(0);
		}else
			return null;
	}
	
	//根据老师ID获取小测
	public Quiz getQuiz(String manOpenId){
		String condition="openid='"+manOpenId+"' order by start desc";
		List<Quiz> qList=quizDao.getQuizByCondition(condition);
		return qList.get(0);
	}
	
	//根据老师ID获取小测ID
	public int getQuizIdByOpenid(String openid){
		List<Quiz> list=quizDao.getQuizByOpenId(openid);
		return list.get(0).getId();
	}
	
	//根据小测ID获取小测类型
	public int getQuizTypeByQuizid(String quizid){
		List<Quiz> list=quizDao.getQuizById(Integer.parseInt(quizid));
		return list.get(0).getType();
	}
	
	public boolean checkQuizContent(QuizItem quiz){
        String content = quiz.getContent();
        int quizType = quiz.getType();
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
