package com.mooctest.weixin.manager;


import java.util.ArrayList;
import java.util.List;
import com.mooctest.weixin.entity.FinishedTask;
import com.mooctest.weixin.entity.Group;
import com.mooctest.weixin.entity.TaskInfo;
import com.mooctest.weixin.util.HttpRequestUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class WitestManager {
	
	//微信服务器url
	private static String server = "http://e9d0b0ab.ngrok.io/weixin/";	
	
	public static String account_page=server +"q/account/info";  //账号信息页面url
	public static String bind_page=server+"q/account/new";  //账号绑定页面url
	public static String check_account=server+"q/account/check";  //验证账号控制器url
	public static String task_page=server+"q/task/query";//任务密码url	
	public static String grade_page=server+"q/task/grade";//任务成绩url
	public static String group_page=server+"q/group/query";//我的群组url
	
	//慕测主站url
	private static String moocserver="http://mooctest.net/api/wechat";
	
	public static String is_Mooc_url=moocserver+"/checkAccount";
	public static String taskinfo_url=moocserver+"/getTaskInfo";
	public static String taskgrade_url=moocserver+"/getFinishedTaskInfo";
	public static String group_url=moocserver+"/getGroupList";
	public static String join_url=moocserver+"/joinGroup";
	
	//获取考试密码url
	public static String exam_pwd_url="http://dev.mooctest.net/taskSecret";
	
	//判断用户是否已经绑定慕测账号
	public static boolean isMoocUser(String openid){		
		return Managers.accountManager.checkAccount(openid);
	}	
	
	//判断用户输入的慕测账号密码是否正确
	public static boolean isMoocUser1(String username,String password){	
		try{
			String param="account="+username+"&password="+password;
			String result=HttpRequestUtil.sendGet(is_Mooc_url, param);
			System.out.println(result);
			JSONObject jsonObject=JSONObject.fromObject(result);			
			JSONObject object=JSONObject.fromObject(jsonObject.get("data"));
			boolean isMooc=object.getBoolean("success");
			return isMooc;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	} 
	
	//从主站获取生成任务密码所需信息
	public static List<TaskInfo> getTaskInfo(String username){
		String param="account="+username;
		String result=HttpRequestUtil.sendGet(taskinfo_url, param);
		JSONObject jsonObject=JSONObject.fromObject(result);
		JSONArray ja=JSONArray.fromObject(jsonObject.get("data"));
		JSONObject obj=new JSONObject();
		List<TaskInfo> list=new ArrayList<TaskInfo>();
		
		for(int i=0;i<ja.size();i++){
			obj=ja.getJSONObject(i);
			TaskInfo taskInfo=new TaskInfo();
			taskInfo.setId(obj.getInt("id"));
			taskInfo.setTaskName(obj.getString("taskName"));
			taskInfo.setPassword(obj.getString("password"));
			list.add(taskInfo);
		}
		return list;
	}
	
	//获取任务成绩
	public static List<FinishedTask> getFinishedTaskInfo(String username){
		String param="account="+username;
		String result=HttpRequestUtil.sendGet(taskgrade_url, param);
		System.out.println(result);
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
			group.setGroupName(obj.getString("groupName"));
			group.setManagerName(obj.getString("managerName"));
			group.setId(obj.getInt("id"));
			list.add(group);
		}
		return list;
	}
	
	//加入群组
	public static boolean joinGroup(String username,String groupId,String managerName){
		String param="account="+username+"&groupId="+groupId+"&managerName="+managerName;
		String result=HttpRequestUtil.sendPost(join_url, param);
		JSONObject jsonObject=JSONObject.fromObject(result);
		JSONObject object=JSONObject.fromObject(jsonObject.get("data"));
		boolean join=object.getBoolean("success");
		return join;
	}
}
