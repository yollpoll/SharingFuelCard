package com.sharingfuelcard.sharingfuelcard.module;

/**
 * Created by 鹏祺 on 2017/9/26.
 * 套餐
 */

public class PlanChoiceBean extends BaseBean {
    private int id;
    private String type;
    private int period;
    private Double monthly_sharing;
    private Double combo_price;
    private Double original_price;
    private Double discount_rate;
    private String combo_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public Double getMonthly_sharing() {
        return monthly_sharing;
    }

    public void setMonthly_sharing(Double monthly_sharing) {
        this.monthly_sharing = monthly_sharing;
    }

    public Double getCombo_price() {
        return combo_price;
    }

    public void setCombo_price(Double combo_price) {
        this.combo_price = combo_price;
    }

    public Double getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(Double original_price) {
        this.original_price = original_price;
    }

    public Double getDiscount_rate() {
        return discount_rate;
    }

    public void setDiscount_rate(Double discount_rate) {
        this.discount_rate = discount_rate;
    }

    public String getCombo_name() {
        return combo_name;
    }

    public void setCombo_name(String combo_name) {
        this.combo_name = combo_name;
    }
}
