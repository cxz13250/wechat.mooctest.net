package com.mooctest.weixin.manager;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mooctest.weixin.entity.FinishedTask;
import com.mooctest.weixin.entity.Group;
import com.mooctest.weixin.entity.TaskInfo;
import com.mooctest.weixin.util.HttpRequestUtil;
import com.thoughtworks.xstream.security.ForbiddenClassException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class WitestManager {
	
	//微信服务器url
	private static String server = "http://628122ee.ngrok.io/weixin/";	
	
	public static String account_page=server +"q/account/info";  //账号信息页面url
	public static String bind_page=server+"q/account/new";  //账号绑定页面url
	public static String check_account=server+"q/account/check";  //验证账号控制器url
	public static String task_page=server+"q/task/query";//任务密码url	
	public static String grade_page=server+"q/task/grade";//任务成绩url
	public static String group_page=server+"q/group/query";//我的群组url
	
	//慕测主站url
	private static String moocserver="";
	
	public static String is_Mooc_url=moocserver+"";
	public static String taskinfo_url=moocserver+"";
	public static String taskgrade_url=moocserver+"";
	public static String group_url=moocserver+"";
	
	//获取考试密码url
	public static String exam_pwd_url="http://dev.mooctest.net/taskSecret";
	
	//判断用户是否已经绑定慕测账号
	public static boolean isMoocUser(String openid){		
		return Managers.accountManager.checkAccount(openid);
	}	
	
	//判断用户输入的慕测账号密码是否正确
	public static boolean isMoocUser1(String username,String password){	
		try{
			String param="username"+username+"&password="+password;
			String result=HttpRequestUtil.sendPost(is_Mooc_url, param);
			JSONObject jsonObject=JSONObject.fromObject(result);
			boolean isMooc=jsonObject.getBoolean("success");
			return isMooc;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	} 
	
	//从主站获取生成任务密码所需信息
	public static List<TaskInfo> getID(String username){
		String param="username="+username;
		String result=HttpRequestUtil.sendGet(taskinfo_url, param);
		JSONObject jsonObject=JSONObject.fromObject(result);
		JSONArray ja=jsonObject.getJSONArray("TaskInfo");
		JSONObject obj=new JSONObject();
		List<TaskInfo> list=new ArrayList<TaskInfo>();
		TaskInfo taskInfo=new TaskInfo();
		for(int i=0;i<ja.size();i++){
			obj=ja.getJSONObject(i);
			taskInfo.setId(obj.getInt("id"));
			taskInfo.setTaskName(obj.getString("taskName"));
			taskInfo.setWorkerid(obj.getInt("workerId"));
			list.add(taskInfo);
		}
		return list;
	}
	
	//获取任务密码
	public static String getExamPwd(String stuID,String taskID){
		String param="stuID="+stuID+"&taskID="+taskID;
		String result=HttpRequestUtil.sendGet(exam_pwd_url, param);
		JSONObject jsonObject=JSONObject.fromObject(result);
		JSONArray jArray=jsonObject.getJSONArray("data");
		if(jArray.isEmpty()){
			return "无";
		}
		else{
			JSONObject obj=jArray.getJSONObject(0);
			String exampwd=obj.getString("taskSecret");
			return exampwd;
		}
	}
	
	//获取任务成绩
	public static List<FinishedTask> getFinishedTaskInfo(String username){
		String param="account="+username;
		String result=HttpRequestUtil.sendGet(taskgrade_url, param);
		JSONObject jsonObject=JSONObject.fromObject(result);
		JSONArray ja=jsonObject.getJSONArray("data");
		List<FinishedTask> list=new ArrayList<>();
		JSONObject obj=new JSONObject();
		FinishedTask fTask=new FinishedTask();
		for(int i=0;i<ja.size();i++){
			obj=ja.getJSONObject(i);
			fTask.setTaskName(obj.getString("taskName"));
			fTask.setGrade(obj.getInt("grade"));
			list.add(fTask);
		}
		return list;
	}
	
	//获取群组信息
	public static List<Group> getGroup(String username){
		String param="account="+username;
		String result=HttpRequestUtil.sendGet(group_url, param);
		JSONObject jsonObject=JSONObject.fromObject(result);
		JSONArray ja=jsonObject.getJSONArray("data");
		List<Group> list=new ArrayList<Group>();
		JSONObject obj=new JSONObject();
		Group group=new Group();
		for(int i=0;i<ja.size();i++){
			obj=ja.getJSONObject(i);
			group.setGroupName();
		}
	}
}
