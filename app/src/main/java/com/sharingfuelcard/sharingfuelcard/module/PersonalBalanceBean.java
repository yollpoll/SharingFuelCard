package com.sharingfuelcard.sharingfuelcard.module;

/**
 * Created by 鹏祺 on 2017/9/25.
 */

public class PersonalBalanceBean extends BaseBean {
    private int period;
    private int bindSum;
    private Double balance;
    private long effectiveTime;
    private String comboType;
    private int coId;
    private long oilcardId;
    private int spareTime;
    private Double comboOrderPrince;
    private Double monthlySharing;

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getBindSum() {
        return bindSum;
    }

    public void setBindSum(int bindSum) {
        this.bindSum = bindSum;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public long getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(long effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getComboType() {
        return comboType;
    }

    public void setComboType(String comboType) {
        this.comboType = comboType;
    }

    public int getCoId() {
        return coId;
    }

    public void setCoId(int coId) {
        this.coId = coId;
    }

    public long getOilcardId() {
        return oilcardId;
    }

    public void setOilcardId(long oilcardId) {
        this.oilcardId = oilcardId;
    }

    public int getSpareTime() {
        return spareTime;
    }

    public void setSpareTime(int spareTime) {
        this.spareTime = spareTime;
    }

    public Double getComboOrderPrince() {
        return comboOrderPrince;
    }

    public void setComboOrderPrince(Double comboOrderPrince) {
        this.comboOrderPrince = comboOrderPrince;
    }

    public Double getMonthlySharing() {
        return monthlySharing;
    }

    public void setMonthlySharing(Double monthlySharing) {
        this.monthlySharing = monthlySharing;
    }
}
