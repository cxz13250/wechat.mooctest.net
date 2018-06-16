package com.mooctest.weixin.config;

import com.mooctest.weixin.pojo.JSApiTicket;
import com.mooctest.weixin.pojo.Token;
import com.mooctest.weixin.util.AdvancedUtil;
import com.mooctest.weixin.util.CommonUtil;
import org.springframework.context.annotation.Configuration;



public class Config {
	
    private String appid; //应用ID
    private String appsecret; //应用秘钥
    private String tokenStr; //Token值,由开发者设定
    private Token token; 
    private JSApiTicket ticket;
    
    private String baseUrl; //配置URL
            
    public Config(String appid, String appsecret, String tokenStr, String baseUrl) {
        this.appid = appid;
        this.appsecret = appsecret;
        this.tokenStr = tokenStr;
        this.baseUrl = baseUrl;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public String getTokenStr() {
        return tokenStr;
    }

    public void setTokenStr(String tokenStr) {
        this.tokenStr = tokenStr;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Token getToken() {
        if (token == null || !token.isValid()) {
            if (appid != null && appsecret != null) {
                token = CommonUtil.getToken(appid, appsecret);
            }
        }
        return token;
    }

    public JSApiTicket getTicket() {
        if (ticket == null || !ticket.isValid()) {
            Token t = getToken();
            if (t != null) {
                this.ticket = AdvancedUtil.getJSApiTicket(t.getAccessToken());
            }
        }
        return ticket;
    }

    public void setTicket(JSApiTicket ticket) {
        this.ticket = ticket;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
  
}
