package com.mooctest.weixin.manager;

import com.mooctest.weixin.dao.User2CompetitionDao;
import com.mooctest.weixin.model.User2Competition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author ROKG
 * @Description
 * @Date: Created in 上午1:22 2018/6/11
 * @Modified By:
 */
@Service
public class User2CompetitionManager {

    @Autowired
    User2CompetitionDao competitionDao;

    public User2Competition get(long userId,long competitionId){
        List<User2Competition> competitions=competitionDao.getCompetitionByColValue("userId",userId,"competitionId",competitionId);
        return competitions.size()==0?null:competitions.get(0);
    }

    public void save(User2Competition address){
        competitionDao.updateUser2Competition(address);
    }
}
