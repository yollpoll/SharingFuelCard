package com.sharingfuelcard.sharingfuelcard.module;

import java.util.List;

/**
 * Created by 鹏祺 on 2017/10/13.
 */

public class HomeDataBean extends BaseBean {
    private String balance;
    private String spareTime;
    private List<Choice> HOT;
    private String monthlySharing;

    public String getMonthlySharing() {
        return monthlySharing;
    }

    public void setMonthlySharing(String monthlySharing) {
        this.monthlySharing = monthlySharing;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getSpareTime() {
        return spareTime;
    }

    public void setSpareTime(String spareTime) {
        this.spareTime = spareTime;
    }

    public List<Choice> getHOT() {
        return HOT;
    }

    public void setHOT(List<Choice> HOT) {
        this.HOT = HOT;
    }

    public static class Choice {
        private String id;
        private String type;
        private String period;
        private String monthly_sharing;
        private String combo_price;
        private double original_price;
        private double discount_rate;
        private String combo_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public String getMonthly_sharing() {
            return monthly_sharing;
        }

        public void setMonthly_sharing(String monthly_sharing) {
            this.monthly_sharing = monthly_sharing;
        }

        public String getCombo_price() {
            return combo_price;
        }

        public void setCombo_price(String combo_price) {
            this.combo_price = combo_price;
        }

        public double getOriginal_price() {
            return original_price;
        }

        public void setOriginal_price(double original_price) {
            this.original_price = original_price;
        }

        public double getDiscount_rate() {
            return discount_rate;
        }

        public void setDiscount_rate(double discount_rate) {
            this.discount_rate = discount_rate;
        }

        public String getCombo_name() {
            return combo_name;
        }

        public void setCombo_name(String combo_name) {
            this.combo_name = combo_name;
        }
    }
}
