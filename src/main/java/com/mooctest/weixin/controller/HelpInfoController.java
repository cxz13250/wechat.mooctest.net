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
        String text = "<h2>小测</h2><p>教师使用公众号的<span style='color: red'>小测</span>"
                + "菜单创建小测，创建成功之后，相应班级的学生可以使用<span style='color: red'>小测</span>菜单获取本次小测的内容并参与回答</p>"
                + "<h2>点名</h2><p>教师使用公众号的<span style='color: red'>点名</span>"
                + "菜单创建点名，创建成功之后，相应班级的学生可以使用<span style='color: red'>点名</span>菜单获取本次小测的内容并参与回答。"
                + "<span style='color: red'>点名功能是基于地理位置的，在使用的时候请允许浏览器获取您的地理位置</span></p>"
                + "<h2>考试</h2><p>教师使用公众号的<span style='color: red'>考试</span>"
                + "菜单可以获得一个班级的密码和成绩；学生只能获得自己的考试密码和成绩<h2>关联</h2>"
                + "<p>慕测平台上注册的账号可以和微信账号进行绑定，以使用公众号的功能</p><h2>吐槽</h2>"
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
