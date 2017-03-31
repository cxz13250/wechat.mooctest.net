package com.mooctest.weixin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Jerry Wang on 2017/3/31.
 */
@Controller
public class HelloController {

    @RequestMapping("/helloworld")
    public ModelAndView hello(){
        ModelAndView m = new ModelAndView();
        m.setViewName("view/hello");
        return m;
    }

}
