package com.sharingfuelcard.sharingfuelcard.http;

/**
 * Created by 鹏祺 on 2017/9/6.
 */

public class Url {
    public final static String HEAD_URL = "http://47.95.246.251:80/ya-oilcard/";
    public final static String IMG_HEAD_URL = "http://47.95.246.251:80/ya-oilcard";
    public final static String REGISTER = HEAD_URL + "user/addUser";
    public final static String LOGIN = HEAD_URL + "user/login";
    public final static String GET_USER = HEAD_URL + "user/getUser";
    public static final String BIND_CARD = HEAD_URL + "oilcard/bindOilcard";
    public static final String MY_CARD_MSG = HEAD_URL + "oilcard/getOilcards";
    public static final String RECHARGE_HISTORY = HEAD_URL + "comboOrder/getRechargeHistory";
    public static final String MY_BALANCE = HEAD_URL + "comboOrder/getOilcardCombos";
    public static final String ALL_CHOICE = HEAD_URL + "comboOrder/getOilcardCombos";
    public static final String GET_HOME_DATA = HEAD_URL + "comboOrder/getHomeData";
    public static final String GET_SMS_CODE = HEAD_URL + "user/getSmscode";
    public static final String BUY_CARD = HEAD_URL + "oilcard/buyOilcard";
    public static final String GET_ADDRESS = HEAD_URL + "user/getAdress";
    public static final String SET_ADDRESS = HEAD_URL + "user/alterAdress";
    public static final String SET_PASSWORD = HEAD_URL + "user/alterPassword";
    public static final String UNBIND_FUEL_CARD = HEAD_URL + "oilcard/delOilcard";
    public static final String BUY_PLAN = HEAD_URL + "comboOrder/addOrder";
    public static final String FORGET_PASSWORD = HEAD_URL + "user/repassword";
    public static final String GET_MY_PLAN = HEAD_URL + "comboOrder/getMyComboOrders";
}
