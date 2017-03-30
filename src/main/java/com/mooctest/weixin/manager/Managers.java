package com.mooctest.weixin.manager;

import java.text.SimpleDateFormat;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mooctest.weixin.config.Config;



@SuppressWarnings("resource")
public class Managers {

    public final static AccountManager accountManager;
    public final static TaskManager taskManager;
    public final static Config config;

    //public final static String baseUrl = "http://mooctest.net/MOOCWechat/q/";
    public final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    static {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        context.start();
        accountManager=context.getBean(AccountManager.class);
        taskManager=context.getBean(TaskManager.class);
        config = (Config) context.getBean("mooctestConfig");       
    }

//    public Managers(RollcallManager rm, WechatQuizManager qm) {
//        rollcallManager = rm;
//        wechatQuizManager = qm;
//    }
}

