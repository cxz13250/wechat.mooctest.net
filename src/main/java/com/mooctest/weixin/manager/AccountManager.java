package com.mooctest.weixin.manager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mooctest.weixin.dao.AccountDao;
import com.mooctest.weixin.model.Account;

/**  
* 类说明   
*  
* @author cxz
* @date 2017年3月20日  新建  
*/
@Service
public class AccountManager {

	@Autowired
	private AccountDao accountDao;
	
	
	//根据openid获取慕测账号
	public String getAccount(String openid){
		List<Account> list=accountDao.getAccountByColValue("openid", openid);
		return list.get(0).getUsername();
	}
	
	//判断微信用户身份
	public int checkAccount(String openid){
		List<Account> list=accountDao.getAccountByColValue("openid", openid);
		if(list.isEmpty()){
			return 2;
		}
		else{
			Account account=list.get(0);
			return account.getType();
		}
	}
	
	//判断慕测号是否已经被绑定
	public boolean checkAccount1(String username){
		List<Account> list=accountDao.getAccountByColValue("username", username);
		if(list.isEmpty()){
			return true;
		}
		else
			return false;
	}
	
	//将慕测账号与微信号映射存入微信数据库
	public void saveAccount(Account account){
		List<Account> Accounts=new ArrayList<Account>();
		Accounts.add(account);
		accountDao.saveAccount(Accounts);
	}

	public void deleteAccount(String openid){
		List<Account> Accounts=accountDao.getAccountByColValue("openid", openid);
		accountDao.deleteAccount(Accounts);
	}
}
