package com.sharingfuelcard.sharingfuelcard.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.module.RechargeHistoryBean;
import com.sharingfuelcard.sharingfuelcard.utils.Constant;
import com.sharingfuelcard.sharingfuelcard.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 鹏祺 on 2017/9/22.
 */

public class RechargeHistoryAdapter extends RecyclerView.Adapter<RechargeHistoryAdapter.ViewHolder> {
    private List<RechargeHistoryBean> list = new ArrayList<>();

    public RechargeHistoryAdapter(List<RechargeHistoryBean> list) {
        this.list = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recharge_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RechargeHistoryBean item = list.get(position);
        String type = "";
        switch (item.getCombo_type()) {
            case Constant.PERSONAL_PLAN:
                type = "个人套餐";
                break;
            case Constant.FAMILY_PLAN:
                type = "家庭套餐";
                break;
            case Constant.COMPANY_PLAN:
                type = "企业套餐";
                break;
            case Constant.GREAT_PLAN:
                type = "优选套餐";
                break;
        }
        holder.tvBuyType.setText(type);
        holder.tvBuyData.setText(Utils.getDate(item.getOrder_time()));
        holder.tvBuyMoney.setText(item.getOriginal_price());
        holder.tvShareMoney.setText(item.getMonthly_sharing());
        holder.tvShareDate.setText(Utils.getDate(item.getOrder_time()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBuyType, tvBuyData, tvBuyMoney,
                tvShareDate, tvShareMoney;

        public ViewHolder(View itemView) {
            super(itemView);
            tvBuyType = (TextView) itemView.findViewById(R.id.tv_buy_type);
            tvBuyData = (TextView) itemView.findViewById(R.id.tv_buy_date);
            tvBuyMoney = (TextView) itemView.findViewById(R.id.tv_buy_money);
            tvShareDate = (TextView) itemView.findViewById(R.id.tv_share_date);
            tvShareMoney = (TextView) itemView.findViewById(R.id.tv_share_money);
        }
    }
}
