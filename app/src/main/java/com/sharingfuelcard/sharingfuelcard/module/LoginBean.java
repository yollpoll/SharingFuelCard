package com.sharingfuelcard.sharingfuelcard.module;

/**
 * Created by 鹏祺 on 2017/9/20.
 */

public class LoginBean extends BaseBean {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
