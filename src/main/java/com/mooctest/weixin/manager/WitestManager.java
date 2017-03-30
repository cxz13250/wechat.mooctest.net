package com.mooctest.weixin.manager;


import com.mooctest.weixin.util.HttpRequestUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class WitestManager {
	
	//微信服务器url
	private static String server = "http://114.55.91.85/weixin/";	
	
	public static String account_page=server +"q/account/info";  //账号信息页面url
	public static String bind_page=server+"q/account/new";  //账号绑定页面url
	public static String check_account=server+"q/account/check";  //验证账号控制器url
	public static String task_page=server+"q/task/query";//任务信息url	
	//慕测主站url
	private static String moocserver="";
	
	public static String is_Mooc_url=moocserver+"";
	
	//获取考试密码url
	public static String exam_pwd_url="http://dev.mooctest.net/taskSecret";
	
	//判断用户是否已经绑定慕测账号
	public static boolean isMoocUser(String openid){		
		return Managers.accountManager.checkAccount(openid);
	}	
	
	//判断用户输入的慕测账号是否存在
	public static boolean isMoocUser1(String username){	
		try{
			String param="username"+username;
			String result=HttpRequestUtil.sendPost(is_Mooc_url, param);
			JSONObject jsonObject=JSONObject.fromObject(result);
			boolean isMooc=jsonObject.getBoolean("data");
			return isMooc;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	} 
	
	//获取考试密码
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
			String exampwd=obj.getString("");
			return exampwd;
		}
	}
}
