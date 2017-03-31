package com.mooctest.weixin.manager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mooctest.weixin.dao.AccountDao;
import com.mooctest.weixin.dao.AccountInfoDao;
import com.mooctest.weixin.model.Account;
import com.mooctest.weixin.model.AccountInfo;

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
	
	@Autowired
	private AccountInfoDao accountInfoDao;
	
	//根据openid获取慕测账号
	public String getAccount(String openid){
		List<Account> list=accountDao.getAccountByColValue("openid", openid);
		return list.get(0).getUsername();
	}
	
	//检验慕测账号是否存在
	public boolean checkMooc(String username,String password){
		return false;
	}
	
	//判断微信号是否已经绑定慕测账号
	public boolean checkAccount(String openid){
		List<Account> list=accountDao.getAccountByColValue("openid", openid);
		if(list.isEmpty()){
			return false;
		}
		else
			return true;
	}
	
	//判断微信号是否已经绑定慕测账号
	public boolean checkAccount1(String username){
		List<Account> list=accountDao.getAccountByColValue("username", username);
		if(list.isEmpty()){
			return false;
		}
		else
			return true;
	}
	
	//将慕测账号与微信号映射存入微信数据库
	public void saveAccount(Account account,String openid){
		Account w=new Account();
		w.setOpenid(openid);
		w.setUsername(account.getUsername());
		List<Account> Accounts=new ArrayList<Account>();
		Accounts.add(w);
		accountDao.saveAccount(Accounts);
	}
	
	//获取用户信息
	public AccountInfo getAccountInfoByOpenid(String openid){
		return accountInfoDao.getAccoutByColValue("openid",openid).get(0);
	}
	
}
