package com.mooctest.weixin.controller;

import com.mooctest.weixin.config.Config;
import com.mooctest.weixin.dao.AccountDao;
import com.mooctest.weixin.data.ContestResult;
import com.mooctest.weixin.manager.Managers;
import com.mooctest.weixin.manager.WitestManager;
import com.mooctest.weixin.model.Account;
import com.mooctest.weixin.pojo.WeixinUserInfo;
import com.mooctest.weixin.util.AdvancedUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Jerry Wang on 2017/3/31.
 */
@Controller
@RequestMapping("/test")
public class HelloController {

    @Autowired
    AccountDao accountDao;

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

    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public void test(){
        Config config= Managers.config;
        System.out.println(config.getToken().getAccessToken());
        List<Account> accounts=accountDao.getAccounts();
        accounts=accounts.stream().filter(a-> a.getUnionId()==null).collect(Collectors.<Account>toList());
        System.out.println(accounts.size());
        for(Account account:accounts){
            WeixinUserInfo userInfo= AdvancedUtil.getUserInfo(config.getToken().getAccessToken(),account.getOpenid());
            account.setUnionId(userInfo.getUnionid());
            accountDao.updateAccount(account);
        }
    }

}
