package com.mooctest.weixin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mooctest.weixin.manager.Managers;

@Controller
@RequestMapping("/help")
public class HelpInfoController {
    
	
	@RequestMapping(value="/info")
    public ModelAndView helpinfo(){
        String text = "<h2>绑定账号</h2><p>参赛者使用公众号的<span style='color: red'>账号</span>"
                + "绑定慕测账号，绑定成功之后，方可以使用其他菜单功能获取本次参赛的任务信息及查询慕测账号的信息</p>"
                + "<h2>任务密码</h2><p>参赛者使用公众号的<span style='color: red'>我的任务</span>"
                + "菜单查询自己的任务信息，查询成功之后，便可以知道相应的任务密码"
                + "<h2>任务群组</h2><p>参赛者使用公众号的<span style='color: red'>我的群组</span>"
                + "菜单可以获得当前自己所在群组；也可以根据对应群主姓名和群组编号加入新的群组<h2>任务密码</h2>"
                + "<p>参赛者使用公众号的<span style='color: red'>我的成绩</span>菜单可以获取当前自己已完成任务的成绩</p><h2>吐槽</h2>"
                + "<p>使用过程有任何问题或是意见，欢迎和我们吐槽</p><br><br><br>";
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());
        
        ModelAndView mv = new ModelAndView();
        mv.addObject("date", date);
        mv.addObject("title", "帮助信息");
        mv.addObject("subtitle", "使用说明");
        mv.addObject("text", text);
        mv.setViewName("text_message");
        return mv;
    }
}
