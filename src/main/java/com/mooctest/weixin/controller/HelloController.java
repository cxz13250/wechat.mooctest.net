package com.mooctest.weixin.controller;

import com.mooctest.weixin.data.ContestResult;
import com.mooctest.weixin.manager.WitestManager;
import com.mooctest.weixin.pojo.WeixinUserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Jerry Wang on 2017/3/31.
 */
@Controller
@RequestMapping("/test")
public class HelloController {

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public void hello(){
        WeixinUserInfo userInfo=new WeixinUserInfo();
        userInfo.setHeadImgUrl("http://mooctest-site.oss-cn-shanghai.aliyuncs.com/app/1507945275436/avatar.png");
        userInfo.setNickname("黄勇");
        ContestResult results=new ContestResult();
        results.setName("2017大赛");
        results.setRank(90);
        results.setScore(90.0);
        try {
            String result = WitestManager.getImage(userInfo, results);
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
