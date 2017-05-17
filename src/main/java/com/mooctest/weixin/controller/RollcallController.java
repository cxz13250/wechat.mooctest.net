package com.mooctest.weixin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mooctest.weixin.entity.Group;
import com.mooctest.weixin.manager.Managers;
import com.mooctest.weixin.manager.WitestManager;
import com.mooctest.weixin.model.RollcallItem;
import com.mooctest.weixin.util.CustomMessageUtil;



@Controller
@RequestMapping("/rollcall")
public class RollcallController {
	//老师创建点名
	@RequestMapping(value="/create")
    public ModelAndView create(@RequestParam("openid") String openid){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = dateFormat.format(new Date());
        ModelAndView mv = new ModelAndView();
        
        if (Managers.rollcallManager.existRollcall(openid)){
            mv.setViewName("danger");
			mv.addObject("msg", "存在进行中的点名！");
			mv.addObject("msg_title", "无法创建");
            return mv;
        }
        
        List<String> groupIdList = new ArrayList<String>();
        List<String> groupNameList = new ArrayList<String>();
        List<Group> groups = WitestManager.getGroup2(Managers.accountManager.getAccount(openid).getMoocid());
        for (Group group : groups) {
            groupIdList.add(String.valueOf(group.getId()));
            groupNameList.add(group.getGroupName());
        }
        
        mv.addObject("groupIdList",groupIdList);
        mv.addObject("groupNameList",groupNameList);
        mv.addObject("openid",openid);
        mv.addObject("date",date);
        mv.setViewName("rollcall_create");
        return mv;
    }
	
	//老师提交点名信息
	@RequestMapping(value="/submit_rollcall")
    public ModelAndView submitRollcall(@RequestParam("openid") String openid, @RequestParam("classId") String classid, @RequestParam("latitude") String latitude, @RequestParam("longitude") String longitude){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());
        ModelAndView mv = new ModelAndView();
        if (Managers.rollcallManager.startRollcall(classid, openid, latitude, longitude)){
            CustomMessageUtil.sendTextCustomMessage(openid, "点名创建成功！\n\n您的学生在此公众号中使用【点名菜单】就可以参与此次点名。\n\n如需结束此次点名请再次点击下方的【点名菜单】", Managers.config.getToken());  
            mv.setViewName("success");
            mv.addObject("msg", "点名创建成功！");
            mv.addObject("msg_title", "创建成功");
        }else {
            mv.setViewName("fail");
            mv.addObject("msg", "点名创建失败！");
            mv.addObject("msg_title", "创建失败");
        }
        return mv;
    }
	
	//学生参与点名
	@RequestMapping(value="/join_rollcall")
    public ModelAndView rollcall(@RequestParam("openid") String openid){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());        
        RollcallItem rollcall = Managers.rollcallManager.getRollcall(openid);
        
        ModelAndView mv = new ModelAndView();
        if (null == rollcall){
            mv.setViewName("danger");
			mv.addObject("msg","点名已经结束！");
			mv.addObject("msg_title","签到失败");
        }else if(rollcall.getWorLocation()!=""&&rollcall.getWorLocation()!=null){
        	System.out.println(rollcall.getWorLocation());
        	mv.setViewName("danger");
        	mv.addObject("msg", "你已经签过到了！");
        	mv.addObject("msg_title", "签到失败");
        }else {
            mv.addObject("date", date);
            mv.addObject("openid", openid);
            mv.setViewName("rollcall");      
        }
        return mv;
    }
	
	//学生提交签到信息
	@RequestMapping(value="/submit_rollcall_location")
	public ModelAndView submitRollcallLocation(@RequestParam("openid") String openid, @RequestParam("latitude") String latitude, @RequestParam("longitude") String longitude){
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        ModelAndView mv = new ModelAndView();

	        if (Managers.rollcallManager.writeStudentLocation(openid, latitude, longitude)) {
	            String distance = Managers.rollcallManager.getRollcall(openid).getDistance();
	            mv.setViewName("success");
	            mv.addObject("msg", "<h1>成功参与点名</h1><p>距离教师：" + distance + "米</p>");
	            mv.addObject("msg_title", "签到成功");
	        }else {
	            mv.setViewName("fail");
				mv.addObject("msg","参与点名失败！");
				mv.addObject("msg_title","点名失败！");
	        }
	       
	        return mv;
	}
}
