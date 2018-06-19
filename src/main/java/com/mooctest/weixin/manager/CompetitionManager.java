package com.mooctest.weixin.manager;

import com.mooctest.weixin.dao.CompetitionDao;
import com.mooctest.weixin.dao.User2CompetitionDao;
import com.mooctest.weixin.model.Account;
import com.mooctest.weixin.model.Competition;
import com.mooctest.weixin.model.User2Competition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author ROKG
 * @Description
 * @Date: Created in 下午2:01 2018/6/8
 * @Modified By:
 */
@Service
public class CompetitionManager {

    @Autowired
    CompetitionDao competitionDao;

    @Autowired
    User2CompetitionManager user2CompetitionManager;

    @Autowired
    AccountManager accountManager;

    public void save(Competition competition){
        competitionDao.updateCompetition(competition);
    }

    public Competition get(){
        List<Competition> competitions=competitionDao.getCompetitionByColValue("is_delete",false);
        return competitions.size()>0?competitions.get(0):null;
    }

    public boolean processImageForCompetition(String openid,String picUrl){
        Competition competition=get();
        Account account=accountManager.getAccount(openid);
        User2Competition user2Competition=user2CompetitionManager.get(account.getMoocid(),competition.getId());
        if (user2Competition==null) {
            return false;
        }else {
            user2Competition.setImageUrl(picUrl);
            user2CompetitionManager.save(user2Competition);
            return true;
        }
    }
}
