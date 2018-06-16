package com.mooctest.weixin.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mooctest.weixin.manager.QuizManager;
import org.hibernate.transform.ToListResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mooctest.weixin.manager.ClusteringManager;
import com.mooctest.weixin.manager.Managers;
import com.mooctest.weixin.model.Question;
import com.mooctest.weixin.model.Quiz;
import com.mooctest.weixin.model.QuizAnswer;



@Controller
@RequestMapping("/answer")
public class AnswerController {

	@Autowired
	QuizManager quizManager;

	@RequestMapping(value="/toresult")
	public ModelAndView queryResult(@RequestParam("openid")String openid,HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv=new ModelAndView();
		
		Quiz quiz=quizManager.getQuiz(openid);
		List<Question> list=quizManager.getQuestion(quiz.getId());
		List<String> list2=new ArrayList<String>();
		for(Question question:list){
			list2.add(String.valueOf(question.getId()));
		}
		mv.addObject("questionId", list2);
		mv.setViewName("quiz_result");
		return mv;
	}
	
	@RequestMapping(value="/result")
	public ModelAndView toResult(@RequestParam("quizid")String quizid){
		ModelAndView mv=new ModelAndView();		

		List<Question> list=quizManager.getQuestion(Integer.parseInt(quizid));
		List<String> list2=new ArrayList<String>();
		for(Question question:list){
			list2.add(String.valueOf(question.getId()));
		}
		mv.addObject("questionId", list2);
		mv.setViewName("quiz_result");
		return mv;
	}
	
	@SuppressWarnings(value={"unchecked","rawtypes"})
	@RequestMapping(value="/cluster")
    public ModelAndView quiz(@RequestParam("questionid") String questionid){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());
        
        ModelAndView mv=new ModelAndView();
        List<QuizAnswer> list = quizManager.getQuizAnswer(Integer.valueOf(questionid));
        List<Question> list2=quizManager.getQuestionById(Integer.parseInt(questionid));
        Question question=new Question();
        if(!list2.isEmpty()){
        	question=list2.get(0);
        }
        int quizType=question.getType();
        
        ArrayList<String> answers = new ArrayList<String>();
        for (QuizAnswer answer : list) {
            answers.add(answer.getWorAnswer());
        }
        
        HashMap<String, ArrayList<String>> map = ClusteringManager.classify(answers);
        if (map == null) {
            mv.addObject("msg","参与回答的学生太少，无法进行分类!" );
            mv.addObject("msg_title", "分类失败");
            mv.setViewName("fail");
            return mv;
        }
        
        if(quizType==1||quizType==2){
			StringBuilder data_arr=new StringBuilder();
			StringBuilder text_arr=new StringBuilder();
			Set set=map.entrySet();
			Iterator it=set.iterator();
	        while(it.hasNext()){
	        	Map.Entry me=(Map.Entry)it.next();
				ArrayList<String> aList=(ArrayList<String>)me.getValue();
	        	data_arr.append(aList.size()+";");
	        	text_arr.append(me.getKey()==""?"其它"+";":me.getKey()+";");
	        }
			mv.setViewName("statistic");
			mv.addObject("data_arr", data_arr.toString());
			mv.addObject("text_arr", text_arr.toString());
		}else{
		        String content = "";
		        String other = "";
		        StringBuilder tmp;
		        String split = "<span style='color: #49b7e8;'>;&nbsp</span>";
		        for (String keyword : map.keySet()) {
		            if (keyword.equals("")) {
		                other += "<h1>未归类的回答</h1>";
		                tmp = new StringBuilder();
		                for (String answer : map.get(keyword)) {
		                    tmp.append(answer + split);//<span style="color: #49b7e8;">返回</span>
		                }
		                other += "<p>" + tmp + "</p>";
		                other += "<br><br><br>";
		            } else {                
		                content += "<h1>" + keyword + "</h1>";
		                tmp = new StringBuilder();
		                for (String answer : map.get(keyword)) {
		                    tmp.append(answer + split);//<span style="color: red;">返回</span>
		                }
		                content += "<p>" + tmp + "</p>";
		                content += "<br>";
		            }
		        }
		        content += other;
		        
		        mv.addObject("date", date);
		        mv.addObject("title", "问答题");
		        mv.addObject("subtitle", "分类结果");
		        mv.addObject("text", content);
		        mv.setViewName("text_message");
		}
        return mv;
    }
}
