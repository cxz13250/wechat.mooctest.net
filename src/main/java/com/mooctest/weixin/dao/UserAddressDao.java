package com.mooctest.weixin.dao;

import com.mooctest.weixin.daobase.BaseDao;
import com.mooctest.weixin.model.UserAddress;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author ROKG
 * @Description
 * @Date: Created in 下午9:34 2018/6/10
 * @Modified By:
 */
@Repository
public class UserAddressDao extends BaseDao<UserAddress,Integer>{

    public List<UserAddress> getCompetitionByColValue(String colName, Object colValue){
        String hqlstring="select distinct q from UserAddress q where q."+colName+" = ?";
        return getListByHQL(hqlstring, colValue);
    }

    public void updateAddress(UserAddress address){
        saveOrUpdate(address);
    }
}
