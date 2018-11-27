package com.diandianzuan.aso.diandianzuan.manager;

import com.diandianzuan.aso.diandianzuan.bean.UserBean;
import com.diandianzuan.aso.diandianzuan.global.Constant;
import com.diandianzuan.aso.diandianzuan.util.CommonUtil;
import com.diandianzuan.aso.diandianzuan.util.SPUtil;


public class AccountManager {
    /**
     * 用户
     */
    public static UserBean sUserBean;

    /**
     * 存储用户cookie
     */
    public static void saveCookie() {
        if (sUserBean == null) {
            return;
        }
        SPUtil.put("phone", sUserBean.getTelNumber());
//        SPUtil.put("password", sUserBean.get());
    }

    /**
     * 清除本地cookie
     */
    public static void removeCookie() {
        SPUtil.remove("phone");
        SPUtil.remove("password");
    }

    /**
     * 退出登录
     */
    public static void logout() {
        sUserBean = null;
        String userBase64 = CommonUtil.objectToBase64(AccountManager.sUserBean);
        SPUtil.put(Constant.USER, userBase64);
        removeCookie();
    }
}
