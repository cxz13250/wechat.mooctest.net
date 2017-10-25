package com.mooctest.weixin.manager;

import com.mooctest.weixin.data.ContestResult;
import com.mooctest.weixin.model.Account;
import com.mooctest.weixin.pojo.Token;
import com.mooctest.weixin.pojo.UserRequest;
import com.mooctest.weixin.pojo.WeixinMedia;
import com.mooctest.weixin.pojo.WeixinUserInfo;
import com.mooctest.weixin.util.AdvancedUtil;
import com.mooctest.weixin.util.CommonUtil;
import com.mooctest.weixin.util.MessageUtil;
import org.springframework.stereotype.Service;
import sun.java2d.pipe.SolidTextRenderer;

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
}
