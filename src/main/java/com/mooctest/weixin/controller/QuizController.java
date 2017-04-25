package com.mooctest.weixin.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mooctest.weixin.entity.Group;
import com.mooctest.weixin.entity.Worker;
import com.mooctest.weixin.json.JSONArray;
import com.mooctest.weixin.json.JSONObject;
import com.mooctest.weixin.manager.Managers;
import com.mooctest.weixin.manager.WitestManager;
import com.mooctest.weixin.model.PreparedQuiz;
import com.mooctest.weixin.model.QuizItem;
import com.mooctest.weixin.util.CustomMessageUtil;
import com.mooctest.weixin.util.QuizAnswerFormat;


@Controller
@RequestMapping("/quiz")
public class QuizController {

	@RequestMapping(value = "/create")
	public ModelAndView create(@RequestParam("openid") String openid,HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String date = dateFormat.format(new Date());

		if (Managers.quizManager.existQuiz(openid)) {
			ModelAndView mv = new ModelAndView();
			mv.setViewName("danger");
			mv.addObject("msg", "存在进行中的小测！");
			mv.addObject("msg_title", "无法创建！");
			return mv;
		}

		List<String> groupIdList = new ArrayList<String>();
		List<String> groupNameList = new ArrayList<String>();

		List<Group> groups = WitestManager.getGroup2(Managers.accountManager.getMoocId(openid));
		List<List<PreparedQuiz>> list = new ArrayList<List<PreparedQuiz>>();
		for (Group group : groups) {
			groupIdList.add(String.valueOf(group.getId()));
			groupNameList.add(group.getGroupName());
			list.add(Managers.quizManager.getPreparedQuiz((int)group.getId()));
		}

		ModelAndView mv = new ModelAndView();
		mv.addObject("classIdList", groupIdList);
		mv.addObject("classNameList", groupNameList);
		mv.addObject("openid", openid);
		mv.addObject("date", date);
		// mv.setViewName("quiz_create2");
		// modify by lsy
		mv.setViewName("quiz_create_new");
		return mv;
	}
	
	@RequestMapping(value = "/show")
	public ModelAndView quiz(@RequestParam("openid") String openid,HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(new Date());

		QuizItem quiz = Managers.quizManager.getQuizItem(openid);
		ModelAndView mv = new ModelAndView();
		if (null == quiz) {
			mv.setViewName("fail");
			mv.addObject("msg", "小测已经结束！");
			mv.addObject("msg_title", "无法参与！");
			return mv;
		}
		
		if(quiz.getWorAnswer()!=""){
			mv.setViewName("fail");
			mv.addObject("msg","您已经提交过答案了！");
			mv.addObject("msg_title","答题失败！");
			return mv;
		}

		if (quiz.getType() == 1) {
			List<String> options = buildQuizOptionsList(quiz.getContent());
			if (options != null && options.size() > 0) {
				mv.addObject("options", options);
			}
			mv.addObject("quiz", quiz);
			mv.addObject("date", date);
			mv.setViewName("quiz_1");
		} else if (quiz.getType() == 2) {
			List<String> options = buildQuizOptionsList(quiz.getContent());
			if (options != null && options.size() > 0) {
				mv.addObject("options", options);
			}
			mv.addObject("quiz", quiz);
			mv.addObject("date", date);
			mv.setViewName("quiz_2");
		} else {
			mv.addObject("quiz", quiz);
			mv.addObject("date", date);
			mv.setViewName("quiz_3");
		}
		return mv;
	}
	
	@RequestMapping(value = "/submit_quiz")
	public ModelAndView submitQuizNew(@RequestParam("openid") String openid,
			@RequestParam("quizList") String preparedQuizId, HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView();

		//如果是新的小测
		if (preparedQuizId.equals("-1")) {
			String quizTitle = request.getParameter("quiz_title");
			String defaultTitle = "老师您想问什么？";
			if (quizTitle == null || quizTitle.length() == 0 || quizTitle.equals(defaultTitle))
				quizTitle = "未命名的小测";

			String quizType = request.getParameter("quiz_type"); //小测类型
			String groupId = request.getParameter("classId");  //群组ID

			JSONObject jo = new JSONObject();
			if (quizType.equals("1") || quizType.equals("2")) {
				String[] content = request.getParameterValues("optionText");
				// 单选题 or 多选题
				for (int i = 0; i < content.length; i++) {
					String key = String.valueOf((char) ('A' + i));
					String value = content[i];
					if (value.equals("")) {
						value = "选项"+key;
					}
					jo.put(key, value);
				}
				List<Worker> workers = WitestManager.getMember(groupId);
				Managers.quizManager.startQuiz(workers,
				Integer.parseInt(groupId), Integer.parseInt(quizType),
				quizTitle, jo.toString(), openid);
			} else {
				String description = request.getParameter("quizDescription");//小测描述
				if(description.equals("")){
					description = "暂无题目描述";
				};
				List<Worker> workers = WitestManager.getMember(String.valueOf(groupId));
				Managers.quizManager.startQuiz(workers,
				Integer.parseInt(groupId), Integer.parseInt(quizType),
				quizTitle, description, openid);
			}

		} else {
			PreparedQuiz quiz = Managers.quizManager.getPreparedQuizById(Integer.valueOf(preparedQuizId));
			int classId = quiz.getGroupId();
			int quizType = quiz.getQuizType();
			String quizTitle = quiz.getQuizTitle();
			List<Worker> workers = WitestManager.getMember(String.valueOf(classId));
			 Managers.quizManager.startQuiz(workers, classId, quizType,
			  quizTitle, quiz.getQuizContent(),
			 openid);
		}

		CustomMessageUtil.sendTextCustomMessage(openid,
				"小测创建成功！\n\n您的学生在此公众号中点击【小测菜单】就可以获得这次小测的内容。\n\n如需结束此次小测请【再次】点击下方的【小测菜单】", Managers.config.getToken());
		mv.addObject("msg","小测创建成功！");
		mv.addObject("msg_title","创建成功");
		mv.setViewName("success");
		return mv;
	}
	
	@RequestMapping(value = "/submit_answer")
	public ModelAndView submitAnswer(@RequestParam("openid") String openid, @RequestParam("quiz_type") String quiz_type,
			HttpServletRequest request) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(new Date());
		ModelAndView mv = new ModelAndView();

		if (quiz_type.equals("1")) {
			String answer = request.getParameter("answer");
			String formatedAnswer = QuizAnswerFormat.formatSingleQuizAnswer(answer);
			if(!Managers.quizManager.writeQuizAnswer(openid, formatedAnswer)){
				mv.setViewName("fail");
				mv.addObject("msg","该小测已结束！");
				mv.addObject("msg_title","提交失败！");
			}
			else{
				// 向用户发送反馈信息
				CustomMessageUtil.sendTextCustomMessage(openid,
						"你已成功提交回答:\n\n  " + formatedAnswer + "\n\n您可以在小测结束前重复提交回答，最后一次提交的结果将作为您的最终回答",
						Managers.config.getToken());
				mv.addObject("msg","成功提交答案！");
				mv.addObject("msg_title","提交成功");
				mv.setViewName("success");
			}
		} else if (quiz_type.equals("2")) {
			String[] answers = request.getParameterValues("answer");
			String answerStr = "";
			for (String string : answers) {
				answerStr += string;
			}
			String formatedAnswer = QuizAnswerFormat.formatMultipleQuizAnswer(answerStr);
			if(!Managers.quizManager.writeQuizAnswer(openid, formatedAnswer)){
				mv.setViewName("fail");
				mv.addObject("msg","该小测已结束！");
				mv.addObject("msg_title","提交失败！");
				return mv;
			}
			// 向用户发送反馈信息
			CustomMessageUtil.sendTextCustomMessage(openid,
					"你已成功提交回答:\n\n  " + formatedAnswer + "\n\n您可以在小测结束前重复提交回答，最后一次提交的结果将作为您的最终回答",
					Managers.config.getToken());

			mv.addObject("msg","成功提交答案！");
			mv.addObject("msg_title","提交成功");
			mv.setViewName("success");
		} else if (quiz_type.equals("3")) {
			String formatedAnswer = request.getParameter("answer");
			if(!Managers.quizManager.writeQuizAnswer(openid, formatedAnswer)){
				mv.setViewName("fail");
				mv.addObject("msg","该小测已结束！");
				mv.addObject("msg_title","提交失败！");
				return mv;
			}
			// 向用户发送反馈信息
			CustomMessageUtil.sendTextCustomMessage(openid,
					"你已成功提交回答:\n\n  " + formatedAnswer + "\n\n您可以在小测结束前重复提交回答，最后一次提交的结果将作为您的最终回答",
					Managers.config.getToken());

			mv.addObject("msg","成功提交答案！");
			mv.addObject("msg_title","提交成功");
			mv.setViewName("success");
		}
		return mv;
	}
	
	@RequestMapping(value = "/get_prepared_quiz_new")
	public void getPreparedQuizNew(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int quizId = Integer.parseInt(request.getParameter("quizId"));
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");

		PreparedQuiz preparedQuiz = Managers.quizManager.getPreparedQuizById(quizId);
		JSONObject quiz = new JSONObject();
		quiz.put("title", preparedQuiz.getQuizTitle());
		quiz.put("type", preparedQuiz.getQuizType());
		String quizContent = preparedQuiz.getQuizContent();
		JSONArray content = new JSONArray();
		if (Managers.quizManager.checkQuizContent(preparedQuiz)) {
			content = quizOptionsToStringNew(quizContent);
		}
		quiz.put("content", content);

		response.getWriter().write(quiz.toString());
		response.getWriter().flush();
	}
	
	@RequestMapping(value = "/get_prepared_quiz_list_new")
	public void getPreparedQuizListNew(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int clazzid = Integer.parseInt(request.getParameter("clazzId"));
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");

		List<PreparedQuiz> preparedQuiz = Managers.quizManager.getPreparedQuiz(clazzid);
		if(!preparedQuiz.isEmpty()){
			JSONArray preparedQuizList = new JSONArray();
			for (PreparedQuiz quiz : preparedQuiz) {
				JSONObject tmp = new JSONObject();
				tmp.put("id", quiz.getId());
				tmp.put("title", quiz.getQuizTitle());
				preparedQuizList.put(tmp);
			}
			response.getWriter().write(preparedQuizList.toString());
			response.getWriter().flush();
		}
	}
	
	//生成小测选项
	private List<String> buildQuizOptionsList(String quizContent) {
		try {
			JSONObject jo = new JSONObject(quizContent);
			List<String> options = new ArrayList<String>();
			for (int i = 0; i < jo.length(); i++) {
				String option = jo.getString(String.valueOf((char) ('A' + i)));
				options.add(option);
			}
			return options;
		} catch (Exception e) {
			return null;
		}
	}
	
	private JSONArray quizOptionsToStringNew(String quizContent) {
		JSONArray content = new JSONArray();
		try {
			JSONObject jo = new JSONObject(quizContent);
			for (int i = 0; i < jo.length(); i++) {
				String index = String.valueOf((char) ('A' + i));
				String option = jo.getString(index);
				content.put(option);
			}
			return content;
		} catch (Exception e) {
			return null;
		}
	}
}
