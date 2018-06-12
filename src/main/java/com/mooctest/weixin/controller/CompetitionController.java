package com.mooctest.weixin.controller;

import com.mooctest.weixin.data.JoinResult;
import com.mooctest.weixin.manager.*;
import com.mooctest.weixin.model.Account;
import com.mooctest.weixin.model.Competition;
import com.mooctest.weixin.model.User2Competition;
import com.mooctest.weixin.model.UserAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @Autowired
    User2CompetitionManager user2CompetitionManager;

    @Autowired
    UserAddressManager addressManager;

    @RequestMapping(value = "/to",method = RequestMethod.GET)
    public ModelAndView toCompetition(@RequestParam(value = "openid")String openid, HttpServletRequest request, HttpServletResponse response)throws Exception{
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        ModelAndView mv=new ModelAndView();
        Account account=accountManager.getAccount(openid);

        if(account==null) {
            mv.setViewName("fail");
            mv.addObject("msg","查询失败！");
            mv.addObject("msg_title","请先绑定账号！");
        }else {
            Competition competition=competitionManager.get();
            User2Competition user2Competition=user2CompetitionManager.get(account.getMoocid(),competition.getId());
            mv.setViewName("monthly");
            mv.addObject("openid",openid);
            mv.addObject("flag",user2Competition!=null);
        }
        return mv;
    }

    @RequestMapping(value = "/enter",method = RequestMethod.POST)
    public boolean enterCompetition(@RequestParam(value = "openid")String openId,@RequestParam(value = "address")String address,HttpServletRequest request,HttpServletResponse response)throws Exception{
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        Account account=accountManager.getAccount(openId);
        Competition competition=competitionManager.get();
        try {
            UserAddress userAddress=addressManager.getAddress(account.getMoocid());
            if (userAddress==null){
                userAddress=new UserAddress();
            }
            userAddress.setAddress(address);
            userAddress.setUserId(account.getMoocid());
            addressManager.save(userAddress);
            JoinResult joinResult = WitestManager.joinGroup(account.getUsername(), String.valueOf(competition.getGroupId()), competition.getManagerName());
            if (joinResult.isSuccess()){
                User2Competition user2Competition=new User2Competition();
                user2Competition.setCompetitionId(competition.getId());
                user2Competition.setUserId(account.getMoocid());
                user2CompetitionManager.save(user2Competition);
            }
            return joinResult.isSuccess();
        }catch (Exception e){
            return false;
        }
    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    public Competition get(){
        return competitionManager.get();
    }

    @RequestMapping(value = "",method = RequestMethod.PUT)
    public Competition update(@RequestBody Competition c){
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
