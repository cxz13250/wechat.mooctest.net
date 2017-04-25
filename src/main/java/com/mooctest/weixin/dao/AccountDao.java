package com.mooctest.weixin.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mooctest.weixin.daobase.BaseDao;
import com.mooctest.weixin.model.Account;

/**  
* 类说明   
*  
* @author  cxz
* @date 2017年3月20日  新建  
*/
@Repository
public class AccountDao extends BaseDao<Account, Integer> {

	public List<Account> getAccountByColValue(String colName, Object colValue){
		String hqlstring="select distinct q from Account q where q."+colName+" = ?";
		return getListByHQL(hqlstring, colValue);
	}
	
	public List<Account> getAccountByUsername(int moocid){
		return getAccountByColValue("moocid", moocid);
	}
	
	public void saveAccount(List<Account> Accounts){
		for(Account w : Accounts)
		{
			save(w);
		}
	}
	
	public void deleteAccount(List<Account> Accounts){
		for(Account w : Accounts){
			delete(w);
		}
	}
}
