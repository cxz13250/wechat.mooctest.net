package com.mooctest.weixin.util;

import com.mooctest.weixin.pojo.Token;

public class CustomMessageUtil {

    public static boolean sendTextCustomMessage(String openid, String textMessage, Token token) {
        return AdvancedUtil.sendCustomMessage(token.getAccessToken(),
                AdvancedUtil.makeTextCustomMessage(openid, textMessage));
    }

}
