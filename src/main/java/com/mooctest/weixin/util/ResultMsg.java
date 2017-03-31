package com.mooctest.weixin.util;

/**
 * Created by Jerry Wang on 2017/3/31.
 */
public class ResultMsg {

    private int errorCode;

    private String errormessage;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrormessage() {
        return errormessage;
    }

    public void setErrormessage(String errormessage) {
        this.errormessage = errormessage;
    }

    public ResultMsg(int errorCode, String errormessage) {
        this.errorCode = errorCode;
        this.errormessage = errormessage;
    }
}
