package com.sharingfuelcard.sharingfuelcard.module;

/**
 * Created by 鹏祺 on 2017/9/22.
 */

public class RechargeHistoryBean extends BaseBean {
    private String co_id;
    private String combo_type;
    private String monthly_sharing;
    private String period;
    private long order_time;
    private String effective_time;
    private String combo_price;
    private String user_id;
    private String oilcard_id;

    public String getCo_id() {
        return co_id;
    }

    public void setCo_id(String co_id) {
        this.co_id = co_id;
    }

    public String getCombo_type() {
        return combo_type;
    }

    public void setCombo_type(String combo_type) {
        this.combo_type = combo_type;
    }

    public String getMonthly_sharing() {
        return monthly_sharing;
    }

    public void setMonthly_sharing(String monthly_sharing) {
        this.monthly_sharing = monthly_sharing;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public long getOrder_time() {
        return order_time;
    }

    public void setOrder_time(long order_time) {
        this.order_time = order_time;
    }

    public String getEffective_time() {
        return effective_time;
    }

    public void setEffective_time(String effective_time) {
        this.effective_time = effective_time;
    }

    public String getCombo_price() {
        return combo_price;
    }

    public void setCombo_price(String combo_price) {
        this.combo_price = combo_price;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOilcard_id() {
        return oilcard_id;
    }

    public void setOilcard_id(String oilcard_id) {
        this.oilcard_id = oilcard_id;
    }
}
