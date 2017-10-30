package com.sharingfuelcard.sharingfuelcard.utils;


import com.google.gson.Gson;
import com.sharingfuelcard.sharingfuelcard.module.UserBean;

/**
 * Created by 鹏祺 on 2017/5/24.
 */

public class SPUtiles {
    public static final String TOKEN = "token";
    public static final String USER = "user";
    public static final String USERNAME = "username";

    public static String getUsername() {
        return SharePreferencesUtils.getString(USERNAME);
    }

    public static void saveUsername(String username) {
        SharePreferencesUtils.putString(USERNAME, username);
    }

    public static UserBean getUser() {
        String json = SharePreferencesUtils.getString(USER);
        Gson gson = new Gson();
        UserBean userBean = gson.fromJson(json, UserBean.class);
        return userBean;
    }

    public static void saveUser(UserBean userBean) {
        String json;
        if (null != userBean) {
            Gson gson = new Gson();
            json = gson.toJson(userBean);
        } else {
            json = "";
        }
        SharePreferencesUtils.putString(USER, json);
    }

    public static String getToken() {
        return SharePreferencesUtils.getString(TOKEN);
    }

    public static void saveToken(String token) {
        SharePreferencesUtils.putString(TOKEN, token);
    }

}
