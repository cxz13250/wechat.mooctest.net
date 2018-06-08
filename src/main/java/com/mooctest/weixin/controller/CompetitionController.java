package com.mooctest.weixin.controller;

import com.mooctest.weixin.data.JoinResult;
import com.mooctest.weixin.manager.AccountManager;
import com.mooctest.weixin.manager.CompetitionManager;
import com.mooctest.weixin.manager.WitestManager;
import com.mooctest.weixin.model.Account;
import com.mooctest.weixin.model.Competition;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author ROKG
 * @Description
 * @Date: Created in 下午1:33 2018/6/8
 * @Modified By:
 */
@RestController
@RequestMapping(value = "competition")
public class CompetitionController {

    @Autowired
    AccountManager accountManager;

    @Autowired
    CompetitionManager competitionManager;

    @RequestMapping(value = "/enter",method = RequestMethod.GET)
    public boolean enterCompetition(@RequestParam(value = "openId")String openId){
        Account account=accountManager.getAccount(openId);
        Competition competition=competitionManager.get();
        try {
            JoinResult joinResult = WitestManager.joinGroup(account.getUsername(), String.valueOf(competition.getGroupId()), competition.getManagerName());
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    public Competition get(){
        return competitionManager.get();
    }

    @RequestMapping(value = "",method = RequestMethod.PUT)
    public Competition update(@RequestBody @NotNull Competition c){
        Competition competition=competitionManager.get();
        if (competition!=null) {
            competition.setGroupId(c.getGroupId());
            competition.setManagerName(c.getManagerName());
        }else {
            competition=new Competition();
            competition.setManagerName(c.getManagerName());
            competition.setGroupId(c.getGroupId());
            competition.setIs_delete(false);
        }
        competitionManager.save(competition);
        return competition;
    }

}
