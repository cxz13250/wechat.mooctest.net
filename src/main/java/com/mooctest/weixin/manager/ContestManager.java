package com.mooctest.weixin.manager;

import com.mooctest.weixin.data.ContestResult;
import com.mooctest.weixin.data.TeacherContest;
import com.mooctest.weixin.data.WorkerContest;
import com.mooctest.weixin.model.Account;
import com.mooctest.weixin.pojo.Token;
import com.mooctest.weixin.pojo.UserRequest;
import com.mooctest.weixin.pojo.WeixinMedia;
import com.mooctest.weixin.pojo.WeixinUserInfo;
import com.mooctest.weixin.util.AdvancedUtil;
import com.mooctest.weixin.util.CommonUtil;
import com.mooctest.weixin.util.MessageUtil;
import javassist.expr.NewArray;
import org.springframework.stereotype.Service;
import sun.java2d.pipe.SolidTextRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ROGK on 2017/10/5.
 */
@Service
public class ContestManager {

    public WeixinMedia getContestInfo(String content,String fromUserName)throws Exception{
        Account account=Managers.accountManager.getAccount(fromUserName);
        if(account==null)
            return null;
        ContestResult contestResult;
        if(content.equals("比赛"))
            contestResult=WitestManager.getContest2(account.getMoocid());
        else
            contestResult=WitestManager.getContest(Integer.parseInt(content),account.getMoocid());
        if(contestResult==null)
            return null;
        Token token=Managers.config.getToken();
        WeixinUserInfo userInfo= AdvancedUtil.getUserInfo(token.getAccessToken(),fromUserName);
        String imageUrl=WitestManager.getImage(userInfo,contestResult);
        WeixinMedia media=AdvancedUtil.uploadMedia(token.getAccessToken(), MessageUtil.REQ_MESSAGE_TYPE_IMAGE,imageUrl);
        return media;
    }



    public WeixinMedia getWorkersContest2(String content,String fromUserName)throws Exception{
        Account account=Managers.accountManager.getAccount(fromUserName);
        if(account==null)
            return null;
        TeacherContest teacherContest=new TeacherContest();
        if(content.equals("比赛"))
            teacherContest=WitestManager.getContestForTeacher2(account.getMoocid());
        if(teacherContest==null)
            return null;
        Token token=Managers.config.getToken();
        String imageUrl=WitestManager.getImage22(teacherContest);
        WeixinMedia media=AdvancedUtil.uploadMedia(token.getAccessToken(), MessageUtil.REQ_MESSAGE_TYPE_IMAGE,imageUrl);
        return media;
    }
}
