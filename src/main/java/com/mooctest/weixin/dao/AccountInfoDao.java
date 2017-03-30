package com.mooctest.weixin.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mooctest.weixin.daobase.BaseDao;
import com.mooctest.weixin.model.AccountInfo;

/**  
* 类说明   
*  
* @author cxz 
* @date 2017年3月25日  新建  
*/
@Repository
public class AccountInfoDao extends BaseDao<AccountInfo, Integer>{

	public List<AccountInfo> getAccoutByColValue(String colName, Object colValue){
		String hqlstring="select distinct q from Account q where q."+colName+" = ?";
		return getListByHQL(hqlstring, colValue);
	}
	
	public List<AccountInfo> getAccountInfoByOpenId(String openid){
		return getAccoutByColValue("username", openid);
	}
}
