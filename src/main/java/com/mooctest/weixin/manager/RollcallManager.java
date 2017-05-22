package com.mooctest.weixin.manager;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mooctest.weixin.dao.AccountDao;
import com.mooctest.weixin.dao.RollcallAnswerDao;
import com.mooctest.weixin.dao.RollcallDao;
import com.mooctest.weixin.dao.RollcallItemDao;
import com.mooctest.weixin.entity.Worker;
import com.mooctest.weixin.model.Account;
import com.mooctest.weixin.model.Rollcall;
import com.mooctest.weixin.model.RollcallAnswer;
import com.mooctest.weixin.model.RollcallItem;
import com.mooctest.weixin.pojo.UserRequest;
import com.mooctest.weixin.util.CommonUtil;
import com.mooctest.weixin.util.DistanceUtil;
import com.mooctest.weixin.util.NewsMessageUtil;



@Service
public class RollcallManager {

	@Autowired
	RollcallDao rollcallDao;
	
	@Autowired
	AccountDao accountDao;
	
	@Autowired
	RollcallItemDao rollcallItemDao;
	
	@Autowired
	RollcallAnswerDao rollcallAnswerDao;
	
	//检验是否已存在点名
	public boolean existRollcall(String teaOpenId){
		List<RollcallItem> qList = rollcallItemDao.getListByCondition("manOpenId='" + teaOpenId + "' and state=1");
		if (qList == null || qList.isEmpty()){
			return false;
		}
		return true;
	}
	
	//开始点名
	public boolean startRollcall(String groupId, String manOpenId, String latitude, String longitude) {
		
		Timestamp rollcallTime = new Timestamp(new Date().getTime());
		List<Worker> workers = WitestManager.getMember(groupId);
		if (workers == null || workers.isEmpty()) {
			return false;
		}
		
		Rollcall rollcall=new Rollcall();
		rollcall.setCreateTime(rollcallTime);
		rollcall.setManLocation(latitude+" "+longitude);
		rollcall.setManOpenid(manOpenId);
		rollcall.setGroupid(groupId);
		rollcallDao.saveRollcall(rollcall);
		
		String openid;
		for (Worker worker : workers) {
			List<Account> list2=accountDao.getAccountByUsername(worker.getId());
			if(!list2.isEmpty()){
				Account account=list2.get(0);
				openid=account.getOpenid();//根据学生慕测帐号编号获取openid
				RollcallItem rollcallItem = new RollcallItem();
				rollcallItem.setManLocation(latitude + " " + longitude);
				rollcallItem.setWorOpenId(openid);	
				rollcallItem.setGrade(worker.getGrade());
				rollcallItem.setGroupId(Integer.valueOf(groupId));
				rollcallItem.setState(1);
				rollcallItem.setRollcallid(rollcall.getId());
				rollcallItem.setManOpenId(manOpenId);
				rollcallItem.setCreateTime(rollcallTime);
				rollcallItemDao.saveRollcallItem(rollcallItem);
			}
		}
		return true;
	}
	
	//结束点名
	public boolean closeRollcall(UserRequest userRequest){
		String teaOpenid = userRequest.getFromUserName();
		List<RollcallItem> qList = rollcallItemDao.getListByCondition("manOpenId='" + teaOpenid + "' and state=1");
		
		if (qList == null || qList.isEmpty()){
			return false;
		}
		
		int total = qList.size(), answered = 0, count500m = 0;
		for (RollcallItem rollcallItem : qList) {
			rollcallItem.setState(-1);
			rollcallItemDao.updateRollcallItem(rollcallItem);
			String distance = rollcallItem.getDistance();
			if (distance != null && distance.length() > 0) {
				answered++;
				if (Double.valueOf(distance) <= 50.0) {
					count500m++;
				}
			}
		}
		String url = WitestManager.rollcall_result_page;
		url = url.replace("ROLLCALLID", String.valueOf(qList.get(0).getGroupId()));
		url = url.replace("OPENID", CommonUtil.urlEncodeUTF8(teaOpenid));
		userRequest.getTextMessage().setContent(url);
		
		String description = "";
		description += "点名[已结束]\n";
		description += "总人数：" + total + "\n参与人数：" + answered;
		description += "\n有效参与人数（距离<50米）：" + count500m;
		description += "\n-------------------------------------" + "\n点击阅读全文查看详细结果";	
		
		String rollcallResultXml = NewsMessageUtil.createRollcallResultXml(description, userRequest.getToUserName(), userRequest.getFromUserName(), qList.get(0).getGroupId());
		userRequest.setResultXml(rollcallResultXml);
		return true;
	}
	
	//根据学生id获取点名
	public RollcallItem getRollcall(String stuopenid) {
		List<RollcallItem> qList = rollcallItemDao.getListByCondition("worOpenId='" + stuopenid + "' and state=1");
		if (qList == null || qList.isEmpty()){
			return null;
		}else {
			return qList.get(0);
		}
	}
	
	//学生签到
	public boolean writeStudentLocation(String stuOpenid, String latitude, String longitude){
		RollcallItem rollcallItem=getRollcall(stuOpenid);
		Timestamp rollcallTime = new Timestamp(new Date().getTime());
		if(rollcallItem==null){
			return false;
		}
		String location1=rollcallItem.getManLocation();
		String location2=latitude + " " + longitude;
		rollcallItem.setWorLocation(location2);
		rollcallItem.setDistance(getDistance(location1, location2));
		rollcallItemDao.updateRollcallItem(rollcallItem);
		RollcallAnswer rollcallAnswer=new RollcallAnswer();
		rollcallAnswer.setOpenid(stuOpenid);
		rollcallAnswer.setRecordtime(rollcallTime);
		rollcallAnswer.setRollcallid(rollcallItem.getRollcallid());
		rollcallAnswer.setWorLocation(location2);
		rollcallAnswer.setDistance(getDistance(location1, location2));
		rollcallAnswerDao.saveRollcallAnswer(rollcallAnswer);
		return true;
	}
	
	//根据学生和老师的位置计算距离
	public String getDistance(String location1,String location2){
		String[] s1=location1.split(" ");
		String[] s2=location2.split(" ");
		double lng1=Double.valueOf(s1[1]);
		double lat1=Double.valueOf(s1[0]);
		double lng2=Double.valueOf(s2[1]);
		double lat2=Double.valueOf(s2[0]);
		double distance = DistanceUtil.getDistance(lng1, lat1, lng2, lat2)/(double)10;
		return Double.toString(distance);
	}
}
