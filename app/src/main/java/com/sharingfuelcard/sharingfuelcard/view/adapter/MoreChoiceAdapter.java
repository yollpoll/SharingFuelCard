package com.sharingfuelcard.sharingfuelcard.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sharingfuelcard.sharingfuelcard.R;

/**
 * Created by 鹏祺 on 2017/9/15.
 */

public class MoreChoiceAdapter extends RecyclerView.Adapter<MoreChoiceAdapter.ViewHolder> {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMoreChoice, tvMonthMoney, tvMonth, tvType, tvDiscount;
        Button btnBuy;

        public ViewHolder(View itemView) {
            super(itemView);
            tvMoreChoice = (TextView) itemView.findViewById(R.id.tv_more_choice);
            tvMonthMoney = (TextView) itemView.findViewById(R.id.tv_month_money);
            tvMonth = (TextView) itemView.findViewById(R.id.tv_month);
            tvType = (TextView) itemView.findViewById(R.id.tv_type);
            tvDiscount = (TextView) itemView.findViewById(R.id.tv_discount_money);
            btnBuy = (Button) itemView.findViewById(R.id.btn_buy);
        }
    }
}
