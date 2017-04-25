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

import org.hibernate.annotations.Where;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mooctest.weixin.json.JSONObject;
import com.mooctest.weixin.manager.ClusteringManager;
import com.mooctest.weixin.manager.Managers;
import com.mooctest.weixin.model.Quiz;
import com.mooctest.weixin.model.QuizAnswer;



@Controller
@RequestMapping("/answer")
public class AnswerController {

	@RequestMapping(value="/cluster")
    public ModelAndView quiz(@RequestParam("quizid") String quizid){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());
        
        ModelAndView mv=new ModelAndView();
        int quizType=Managers.quizManager.getQuizTypeByQuizid(quizid);
        List<QuizAnswer> list = Managers.quizManager.getQuizAnswer(Integer.valueOf(quizid));
        ArrayList<String> answers = new ArrayList<String>();
        for (QuizAnswer answer : list) {
            answers.add(answer.getWorAnswer());
        }
        
        HashMap<String, ArrayList<String>> map = ClusteringManager.classify(answers);
        if (map == null) {
            mv.addObject("msg","参与回答的学生太少，无法进行分类!" );
            mv.addObject("msg_title", "分类失败");
            mv.setViewName("danger");
            return mv;
        }
        
        if(quizType==1||quizType==2){
			String[] color={"#00FFFF","#EA0000","#00EC00","#FFCBB3","#484891","CCFF80","#B87070","#000079","#EA0000","#8600FF"};
			StringBuilder data_arr=new StringBuilder();
			StringBuilder color_arr=new StringBuilder();
			StringBuilder text_arr=new StringBuilder();
			List<String> arr=new ArrayList<String>();
			Set set=map.entrySet();
			Iterator it=set.iterator();
	        int i=0;
	        while(it.hasNext()){
	        	Map.Entry me=(Map.Entry)it.next();
				ArrayList<String> aList=(ArrayList<String>)me.getValue();
	        	arr.add(String.valueOf(aList.size()));
	        	float param=(float)aList.size()/(float)list.size();
	        	data_arr.append(param+";");
	        	color_arr.append(color[i]+";");
	        	text_arr.append(me.getKey()==""?"其它":me.getKey()+";");
	        	System.out.println("回答"+me.getKey()+"的人数为:"+aList.size()+",所占比例为:"+param+",所用颜色为:"+color[i]);
	        	i++;
	        }
			mv.setViewName("draw");
			mv.addObject("arrList",arr);
			mv.addObject("data_arr", data_arr.toString());
			mv.addObject("color_arr", color_arr.toString());
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
		        mv.addObject("title", "课堂小测");
		        mv.addObject("subtitle", "分类结果");
		        mv.addObject("text", content);
		        mv.setViewName("text_message");
		}
        return mv;
    }
	
	@SuppressWarnings(value={"unchecked","rawtypes"})
	@RequestMapping(value="/result")
	public ModelAndView test(@RequestParam("openid")String openid,HttpServletRequest request,HttpServletResponse response) throws IOException{

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		
		Quiz quiz=Managers.quizManager.getQuiz(openid);
		
		ModelAndView mv=new ModelAndView();
		
		List<QuizAnswer> list = Managers.quizManager.getQuizAnswer(quiz.getId());
			
		ArrayList<String> answers = new ArrayList<String>();
		for (QuizAnswer answer : list) {
		    answers.add(answer.getWorAnswer());
		}
		        
		HashMap<String, ArrayList<String>> map = ClusteringManager.classify(answers);
		if (map == null) {
		    mv.addObject("msg","参与回答的学生太少，无法进行分类!" );
		    mv.addObject("msg_title", "分类失败");
		    mv.setViewName("danger");
		    return mv;
		}
		 
		if(quiz.getType()==1||quiz.getType()==2){
			String[] color={"#00FFFF","#EA0000","#00EC00","#FFCBB3","#484891","CCFF80","#B87070","#000079","#EA0000","#8600FF"};
			StringBuilder data_arr=new StringBuilder();
			StringBuilder color_arr=new StringBuilder();
			StringBuilder text_arr=new StringBuilder();
			List<String> arr=new ArrayList<String>();
			Set set=map.entrySet();
			Iterator it=set.iterator();
	        int i=0;
	        while(it.hasNext()){
	        	Map.Entry me=(Map.Entry)it.next();
				ArrayList<String> aList=(ArrayList<String>)me.getValue();
	        	arr.add(String.valueOf(aList.size()));
	        	float param=(float)aList.size()/(float)list.size();
	        	data_arr.append(param+";");
	        	color_arr.append(color[i]+";");
	        	text_arr.append(me.getKey()==""?"其它":me.getKey()+";");
	        	System.out.println("回答"+me.getKey()+"的人数为:"+aList.size()+",所占比例为:"+param+",所用颜色为:"+color[i]);
	        	i++;
	        }
			mv.setViewName("draw");
			mv.addObject("arrList",arr);
			mv.addObject("data_arr", data_arr.toString());
			mv.addObject("color_arr", color_arr.toString());
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
		        mv.addObject("title", "课堂小测");
		        mv.addObject("subtitle", "分类结果");
		        mv.addObject("text", content);
		        mv.setViewName("text_message");
		}
        return mv;
	}
}
