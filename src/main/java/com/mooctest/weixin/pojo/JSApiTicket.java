package com.mooctest.weixin.pojo;

public class JSApiTicket {
    private int expiresIn;
    private String ticket;
    private Long createTime;

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
    
    public boolean isValid(){
        if ((System.currentTimeMillis() - createTime) < (expiresIn - 100) * 1000) {
            return true;
        }
        return false;
    }
}
