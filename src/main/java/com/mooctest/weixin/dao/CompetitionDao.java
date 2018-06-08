package com.mooctest.weixin.dao;

import com.mooctest.weixin.daobase.BaseDao;
import com.mooctest.weixin.model.Competition;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author ROKG
 * @Description
 * @Date: Created in 下午1:56 2018/6/8
 * @Modified By:
 */
@Repository
public class CompetitionDao extends BaseDao<Competition,Integer>{

    public List<Competition> getCompetitionByColValue(String colName, Object colValue){
        String hqlstring="select distinct q from Competition q where q."+colName+" = ?";
        return getListByHQL(hqlstring, colValue);
    }

    public void updateCompetition(Competition competition){
        saveOrUpdate(competition);
    }
}
