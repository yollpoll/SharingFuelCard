package com.sharingfuelcard.sharingfuelcard.module;

/**
 * Created by 鹏祺 on 2017/9/22.
 */

public class MyCardMsgBean extends BaseBean {
    private String oilcard_id;
    private int oilcard_type;
    private String oilcard_name;

    public String getOilcard_id() {
        return oilcard_id;
    }

    public void setOilcard_id(String oilcard_id) {
        this.oilcard_id = oilcard_id;
    }

    public int getOilcard_type() {
        return oilcard_type;
    }

    public void setOilcard_type(int oilcard_type) {
        this.oilcard_type = oilcard_type;
    }

    public String getOilcard_name() {
        return oilcard_name;
    }

    public void setOilcard_name(String oilcard_name) {
        this.oilcard_name = oilcard_name;
    }
}
