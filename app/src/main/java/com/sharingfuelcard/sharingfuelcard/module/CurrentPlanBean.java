package com.sharingfuelcard.sharingfuelcard.module;

/**
 * Created by 鹏祺 on 2017/10/25.
 */

public class CurrentPlanBean extends BaseBean {
    private String period;
    private String bindSum;
    private String balance;
    private String effectiveTime;
    private String comboType;
    private String coId;
    private String oilcardId;
    private String spareTime;
    private String comboOrderPrince;
    private String monthlySharing;

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getBindSum() {
        return bindSum;
    }

    public void setBindSum(String bindSum) {
        this.bindSum = bindSum;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(String effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getComboType() {
        return comboType;
    }

    public void setComboType(String comboType) {
        this.comboType = comboType;
    }

    public String getCoId() {
        return coId;
    }

    public void setCoId(String coId) {
        this.coId = coId;
    }

    public String getOilcardId() {
        return oilcardId;
    }

    public void setOilcardId(String oilcardId) {
        this.oilcardId = oilcardId;
    }

    public String getSpareTime() {
        return spareTime;
    }

    public void setSpareTime(String spareTime) {
        this.spareTime = spareTime;
    }

    public String getComboOrderPrince() {
        return comboOrderPrince;
    }

    public void setComboOrderPrince(String comboOrderPrince) {
        this.comboOrderPrince = comboOrderPrince;
    }

    public String getMonthlySharing() {
        return monthlySharing;
    }

    public void setMonthlySharing(String monthlySharing) {
        this.monthlySharing = monthlySharing;
    }
}
