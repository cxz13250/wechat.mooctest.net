package com.mooctest.weixin.manager;
import java.awt.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Formatter;

import com.mooctest.weixin.data.*;
import com.mooctest.weixin.pojo.WeixinUserInfo;
import com.mooctest.weixin.util.CommonUtil;
import com.mooctest.weixin.util.HttpRequestUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.log4j.Logger;


public class WitestManager {

	private static Logger logger = Logger.getLogger(WitestManager.class);

	//服务器url
//	private static String server = "http://553497eb.ngrok.io/weixin/";
	private static String server= "http://wechat.mooctest.net/weixin/";
	
	public static final String account_page=server +"q/account/info";  //账号信息页面url
	public static final String bind_page=server+"q/account/new";  //账号绑定页面url
	public static final String check_account=server+"q/account/check";  //验证账号控制器url
	public static final String task_page=server+"q/task/query";//任务密码url
	public static final String grade_page=server+"q/task/grade";//任务成绩url
	public static final String group_page=server+"q/group/query";//我的群组url
	public static final String join_page=server+"q/group/join";//加入群组url
	public static final String createquiz_page=server+"q/quiz/create";//创建小测url
	public static final String showquiz_page=server+"q/quiz/show";//参与小测url
	public static final String quiz_result_page=server +"q/answer/result?quizid=QUIZID";//老师端本次小测结果url
	public static final String quiz_result_page1=server+"q/answer/toresult";//老师端历次小测结果url
	public static final String rollcall_create_page=server+"q/rollcall/create";//创建点名url
	public static final String rollcall_join_page=server+"q/rollcall/join_rollcall";//参与点名url
	public static final String rollcall_result_page=server+"q/rollcall/result?openid=OPEN&rollcallid=ROLLCALLID";//老师端点名结果
	public static final String manager_grade_page=server+"q/task/manager_grade";
	public static final String manager_task_page=server+"q/task/manager_task";
	public static final String monthly_match_page=server+"q/competition/to";
	
	//慕测主站url
	private static final String moocserver="http://www.mooctest.net/api/wechat";
	private static final String test="http://127.0.0.1:8080/api/wechat";

	public static final String is_Mooc_url=moocserver+"/checkWorker";
	public static final String taskinfo_url=moocserver+"/getTaskInfo";
	public static final String taskgrade_url=moocserver+"/getFinishedTaskInfo";
	public static final String group_url=moocserver+"/getGroupList";
	public static final String join_url=moocserver+"/joinGroup";
	public static final String is_Manager_url=moocserver+"/checkManager";
	public static final String getstudent_url=moocserver+"/getWorkersByGroup";
	public static final String group_url2=moocserver+"/getGroupsByManager";
	public static final String accountinfo_url=moocserver+"/getUserInfo";
	public static final String manager_task_url=moocserver+"/getManagerTask";
	public static final String worker_grade_url=moocserver+"/getWorkersGrade";
	public static final String worker_password_url=moocserver+"/getWorkersPassword";
	public static final String worker_contest_url=moocserver+"/contest";
	public static final String workers_contest_url=moocserver+"/contests/worker";
	public static final String enter_competion_url=moocserver+"/competition";

	//生成比赛结果图片
	public static final String contest_image_url="http://118.178.18.181:10086";
	public static final String worker_image_url="http://118.178.18.181:15006";
//	public static final String contest_image_url="http://10.0.0.45:10080";

	//判断用户身份
	public static int identity(String openid){		
		return Managers.accountManager.checkAccount(openid);
	}	
	
	//判断用户输入的慕测学生账号密码是否正确
	public static int isWorker(String username,String password){	
		try{
			String param="account="+username+"&password="+password;
			String result=HttpRequestUtil.sendGet(is_Mooc_url, param);
			JSONObject jsonObject=JSONObject.fromObject(result);	
			if(jsonObject==null){
				return 0;
			}
			JSONObject object=JSONObject.fromObject(jsonObject.get("data"));
			return object.getInt("id");
		}catch(Exception ex){
			logger.error(ex.getStackTrace());
			ex.printStackTrace();
			return 0;
		}
	} 
	
	//判断用户输入的慕测老师账号密码是否正确
	public static int isManager(String username,String password){
		try {
			String param="account="+username+"&password="+password;
			String result=HttpRequestUtil.sendGet(is_Manager_url, param);
			JSONObject jsonObject=JSONObject.fromObject(result);			
			JSONObject object=JSONObject.fromObject(jsonObject.get("data"));
			if(object==null)
				return 0;
			return object.getInt("id");
		} catch (Exception e) {
			logger.error(e.getStackTrace());
			e.printStackTrace();
			return 0;
		}
	}
	
	//从主站获取生成任务信息
	public static List<TaskInfo> getTaskInfo(String username) throws IOException{
		String param="account="+username;
		String result=HttpRequestUtil.sendGet(taskinfo_url, param);
		JSONObject jsonObject=JSONObject.fromObject(result);
		JSONArray ja=JSONArray.fromObject(jsonObject.get("data"));
		JSONObject obj;
		TaskInfo taskInfo;
		List<TaskInfo> list=new ArrayList<TaskInfo>();
		for(int i=0;i<ja.size();i++){
			obj=ja.getJSONObject(i);
			taskInfo=(TaskInfo)JSONObject.toBean(obj, TaskInfo.class);
			list.add(taskInfo);
		}
		return list;
	}
	
	//获取任务成绩
	public static List<FinishedTask> getFinishedTaskInfo (String username) throws IOException{
		String param="account="+username;
		String result=HttpRequestUtil.sendGet(taskgrade_url, param);
		JSONObject jsonObject=JSONObject.fromObject(result);
		JSONArray ja=JSONArray.fromObject(jsonObject.get("data"));
		List<FinishedTask> list=new ArrayList<>();
		JSONObject obj;
		FinishedTask fTask;
		for(int i=0;i<ja.size();i++){
			obj=ja.getJSONObject(i);
			fTask=(FinishedTask)JSONObject.toBean(obj, FinishedTask.class);
			list.add(fTask);
		}
		return list;
	}
	
	//获取群组信息
	public static List<Group> getGroup(String username) throws IOException{
		String param="account="+username;
		String result=HttpRequestUtil.sendGet(group_url, param);
		JSONObject jsonObject=JSONObject.fromObject(result);
		JSONArray ja=JSONArray.fromObject(jsonObject.get("data"));
		List<Group> list=new ArrayList<Group>();
		Group group;
		JSONObject obj;
		for(int i=0;i<ja.size();i++){
			obj=ja.getJSONObject(i);
			group=(Group)JSONObject.toBean(obj, Group.class);
			list.add(group);
		}
		return list;
	}
	
	//加入群组
	public static JoinResult joinGroup(String username,String groupId,String managerName){
		JoinGroup joinGroup=new JoinGroup();
		joinGroup.setAccount(username);
		joinGroup.setGroupId(groupId);
		joinGroup.setManagerName(managerName);
		//对象转JSON
		String param=JSONSerializer.toJSON(joinGroup).toString();
		String result=HttpRequestUtil.post(join_url, param);
		JSONObject jsonObject=JSONObject.fromObject(result);
		//JSON转对象
		return (JoinResult)JSONObject.toBean(jsonObject, JoinResult.class);
	}
	
	//根据老师账号获取群组
	public static List<Group> getGroup2(int id){
		List<Group> list=new ArrayList<Group>();
		String param="managerId="+id;
		String result=HttpRequestUtil.sendGet(group_url2, param);
		JSONObject jsonObject=JSONObject.fromObject(result);
		JSONObject object=JSONObject.fromObject(jsonObject.get("data"));
		JSONArray ja=JSONArray.fromObject(object.get("groups"));
		Group group;
		JSONObject obj;
		for(int i=0;i<ja.size();i++){
			obj=ja.getJSONObject(i);
			group=(Group)JSONObject.toBean(obj, Group.class);
			list.add(group);
		}
		return list;
	}
	
	//根据群组ID获取成员
	public static List<Worker> getMember(String groupId){
		List<Worker> list=new ArrayList<Worker>();
		String param="groupId="+groupId;
		String result=HttpRequestUtil.sendGet(getstudent_url, param);
		JSONObject jsonObject=JSONObject.fromObject(result);
		JSONObject jObject=JSONObject.fromObject(jsonObject.get("data"));
		JSONArray jArray=JSONArray.fromObject(jObject.get("workers"));
		Worker worker;
		JSONObject object;
		for(int i=0;i<jArray.size();i++){
			object=jArray.getJSONObject(i);
			worker=(Worker)JSONObject.toBean(object, Worker.class);
			list.add(worker);
		}
		return list;
	}
	
	//根据慕测账号获取用户信息
	public static Accountinfo getInfo(int id,String type){
		String param="userId="+id+"&identity="+type;
		String result=HttpRequestUtil.sendGet(accountinfo_url, param);
		JSONObject jsonObject=JSONObject.fromObject(result);
		JSONObject object=JSONObject.fromObject(jsonObject.get("data"));
		return (Accountinfo)JSONObject.toBean(object, Accountinfo.class);
	}

	//根据老师id获取已结束的任务
	public static List<Task> getFinishedTask(int id){
		List<Task> list=new ArrayList<Task>();
		String param="userId="+id;
		String result=HttpRequestUtil.sendGet(manager_task_url,param);
		JSONObject jsonObject=JSONObject.fromObject(result);
		JSONObject object=JSONObject.fromObject(jsonObject.get("data"));
		JSONArray ja=JSONArray.fromObject(object.get("exams"));
		JSONObject obj;
		Task task;
		for(int i=0;i<ja.size();i++){
			obj=ja.getJSONObject(i);
			task=(Task)JSONObject.toBean(obj,Task.class);
			if(task.getStatus()==2) {
				list.add(task);
			}else
				continue;
		}
		return list;
	}

	//根据老师id获取正在进行的任务
	public static List<Task> getCurrentTask(int id){
		List<Task> list=new ArrayList<Task>();
		String param="userId="+id;
		String result=HttpRequestUtil.sendGet(manager_task_url,param);
		JSONObject jsonObject=JSONObject.fromObject(result);
		JSONObject object=JSONObject.fromObject(jsonObject.get("data"));
		JSONArray ja=JSONArray.fromObject(object.get("exams"));
		JSONObject obj;
		Task task;
		for(int i=0;i<ja.size();i++){
			obj=ja.getJSONObject(i);
			task=(Task)JSONObject.toBean(obj,Task.class);
			if(task.getStatus()==1) {
				list.add(task);
			}else
				continue;
		}
		return list;
	}

	//根据老师id获取未开始的任务
	public static List<Task> getUnstartedTask(int id){
		List<Task> list=new ArrayList<Task>();
		String param="userId="+id;
		String result=HttpRequestUtil.sendGet(manager_task_url,param);
		JSONObject jsonObject=JSONObject.fromObject(result);
		JSONObject object=JSONObject.fromObject(jsonObject.get("data"));
		JSONArray ja=JSONArray.fromObject(object.get("exams"));
		JSONObject obj;
		Task task;
		for(int i=0;i<ja.size();i++){
			obj=ja.getJSONObject(i);
			task=(Task)JSONObject.toBean(obj,Task.class);
			if(task.getStatus()==0){
				list.add(task);
			}else
				continue;
		}
		return list;
	}

	//根据任务id获取workers成绩
	public static List<Grade> getWorkersGrade(String id){
		List<Grade> list=new ArrayList<Grade>();
		String param="examId="+id;
		String result=HttpRequestUtil.sendGet(worker_grade_url,param);
		JSONObject jsonObject=JSONObject.fromObject(result);
		JSONObject object=JSONObject.fromObject(jsonObject.get("data"));
		JSONArray ja=JSONArray.fromObject(object.get("grades"));
		Grade grade;
		JSONObject obj;
		for(int i=0;i<ja.size();i++){
			obj=ja.getJSONObject(i);
			grade=(Grade)JSONObject.toBean(obj,Grade.class);
			list.add(grade);
		}
		return list;
	}

	//根据任务id获取workers密码
	public static List<Password> getWorkersPassword(String id){
		List<Password> list=new ArrayList<Password>();
		String param="examId="+id;
		String result=HttpRequestUtil.sendGet(worker_password_url,param);
		JSONObject jsonObject=JSONObject.fromObject(result);
		JSONObject object=JSONObject.fromObject(jsonObject.get("data"));
		JSONArray ja=JSONArray.fromObject(object.get("passwords"));
		Password password;
		JSONObject obj;
		for(int i=0;i<ja.size();i++){
			obj=ja.getJSONObject(i);
			password=(Password)JSONObject.toBean(obj,Password.class);
			list.add(password);
		}
		return list;
	}

	public static ContestResult getContest(long taskId,long userId){
		String param="examId="+taskId+"&userId="+userId;
		String result=HttpRequestUtil.sendGet(worker_contest_url,param);
		JSONObject jsonObject=JSONObject.fromObject(result);
		JSONObject object=JSONObject.fromObject(jsonObject.get("data"));
		if(object==null)
			return null;
		else
			return (ContestResult)JSONObject.toBean(object,ContestResult.class);
	}

	public static ContestResult getContest2(long userId){
		String param="userId="+userId;
		String result=HttpRequestUtil.sendGet(worker_contest_url,param);
		JSONObject jsonObject=JSONObject.fromObject(result);
		JSONObject object=JSONObject.fromObject(jsonObject.get("data"));
		if(object==null)
			return null;
		else
			return (ContestResult)JSONObject.toBean(object,ContestResult.class);
	}

	public static TeacherContest getContestForTeacher2(long userId){
		String param="userId="+userId;
		String result=HttpRequestUtil.sendGet(workers_contest_url,param);
		JSONObject jsonObject=JSONObject.fromObject(result);
		JSONArray array=JSONArray.fromObject(jsonObject.get("data"));
		String teacherName=jsonObject.getString("message");
		if(array==null || array.size()==0)
			return null;
		else {
			int num = (int) (Math.random() * array.size());
			WorkerContest workerContest=(WorkerContest) JSONObject.toBean(array.getJSONObject(num), WorkerContest.class);;
			TeacherContest teacherContest=new TeacherContest(workerContest,teacherName);
			return teacherContest;
		}
	}

	public static String getImage(WeixinUserInfo userInfo, ContestResult contestResult)throws Exception{
		String score= new Formatter().format("%.2f",contestResult.getScore()).toString();
		String rank= new Formatter().format("%.2f",contestResult.getRank()).toString();
		String param="userName="+ URLEncoder.encode(userInfo.getNickname(),"utf-8")+"&userAvatar="+userInfo.getHeadImgUrl()+"&score="
				+score+"&rank="+rank+"&testName="+URLEncoder.encode(contestResult.getName(),"utf-8");
		String result=HttpRequestUtil.sendGet(contest_image_url,param);
		return result;
	}

	public static String getImage2(WorkerContest workerContest)throws Exception{
		System.out.println(workerContest.getScore());
		String score= new Formatter().format("%.2f",workerContest.getScore()).toString();
		String param="userName="+ URLEncoder.encode(workerContest.getWorkerName(),"utf-8")+"&score="
				+score+"&rank="+workerContest.getRank()+"&testName="+URLEncoder.encode(workerContest.getTaskName(),"utf-8");
		String result=HttpRequestUtil.sendGet(worker_image_url,param);
		return result;
	}

	public static String getImage22(TeacherContest teacherContest)throws Exception{
		System.out.println(teacherContest.getTeacherName());
		String score= new Formatter().format("%.2f",teacherContest.getScore()).toString();
		String param="userName="+ URLEncoder.encode(teacherContest.getWorkerName(),"utf-8")+"&score="
				+score+"&rank="+teacherContest.getRank()+"&testName="+URLEncoder.encode(teacherContest.getTaskName(),"utf-8");
		String result=HttpRequestUtil.sendGet(worker_image_url,param);
		return result;
	}

	public static boolean enterCompetition(long userId,long competitionId)throws Exception{
		String params="userId="+userId+"&competitionId="+competitionId;
		String result=HttpRequestUtil.sendPost(enter_competion_url,params);
		JSONObject object=JSONObject.fromObject(result);
		if (object.getInt("status")==200){
			return true;
		}else {
			return false;
		}
	}
}
