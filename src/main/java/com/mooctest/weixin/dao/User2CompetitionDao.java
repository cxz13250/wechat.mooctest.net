package com.mooctest.weixin.dao;

import com.mooctest.weixin.daobase.BaseDao;
import com.mooctest.weixin.model.User2Competition;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author ROKG
 * @Description
 * @Date: Created in 上午1:20 2018/6/11
 * @Modified By:
 */
@Repository
public class User2CompetitionDao extends BaseDao<User2Competition,Integer> {

    public List<User2Competition> getCompetitionByColValue(String colName, Object colValue,String colName1, Object colValue1){
        String hqlstring="select distinct q from User2Competition q where q."+colName+" = ?"+" and q."+colName1+" = ?";
        return getListByHQL(hqlstring, colValue,colValue1);
    }

    public void updateUser2Competition(User2Competition user2Competition){
        saveOrUpdate(user2Competition);
    }
}
