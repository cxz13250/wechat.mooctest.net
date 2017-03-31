package com.mooctest.weixin.manager;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mooctest.weixin.util.HttpRequestUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class WitestManager {
	
	//微信服务器url
	private static String server = "http://628122ee.ngrok.io/weixin/";	
	
	public static String account_page=server +"q/account/info";  //账号信息页面url
	public static String bind_page=server+"q/account/new";  //账号绑定页面url
	public static String check_account=server+"q/account/check";  //验证账号控制器url
	public static String task_page=server+"q/task/query";//任务信息url	
	//慕测主站url
	private static String moocserver="";
	
	public static String is_Mooc_url=moocserver+"";
	public static String taskinfo_url=moocserver+"";
	public static String taskgrade_url=moocserver+"";
	
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
	
	//从主站获取 学生ID,考试ID
	public static List<String> getID(String username){
		String param="username="+username;
		String result=HttpRequestUtil.sendGet(taskinfo_url, param);
		JSONObject jsonObject=JSONObject.fromObject(result);
		JSONArray ja=jsonObject.getJSONArray("TaskInfo");
		JSONObject object=ja.getJSONObject(0);
		String taskName=object.getString("taskName1");
		String worker=object.getString("worker1");
		List<String> list=new ArrayList<String>();
		list.add(taskName);
		list.add(worker);
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
	public static String getFinishedTaskInfo(String username){
		String param="account="+username;
		String result=HttpRequestUtil.sendGet(taskgrade_url, param);
		JSONObject jsonObject=JSONObject.fromObject(result);
		JSONArray ja=jsonObject.getJSONArray("data");
		JSONObject object=ja.getJSONObject(0);
		String taskName=object.getString("taskName");
		int grade=object.getInt("grade");
		return null;
	}
}
