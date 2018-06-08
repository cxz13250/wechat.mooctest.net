package com.mooctest.weixin.manager;

import com.mooctest.weixin.dao.CompetitionDao;
import com.mooctest.weixin.model.Competition;
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

    public void save(Competition competition){
        competitionDao.updateCompetition(competition);
    }

    public Competition get(){
        List<Competition> competitions=competitionDao.getCompetitionByColValue("is_delete",false);
        return competitions.size()>0?competitions.get(0):null;
    }
}
