package com.sharingfuelcard.sharingfuelcard.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.module.PlanChoiceBean;

import java.util.List;

/**
 * Created by 鹏祺 on 2017/9/26.
 */

public class MorePlanChoiceAdapter extends RecyclerView.Adapter<MorePlanChoiceAdapter.ViewHolder> {
    private List<PlanChoiceBean> list;
    private OnItemClickListener onItemClickListener;
    private OnBtnBuyClickListener onBtnBuyClickListener;

    public MorePlanChoiceAdapter(List<PlanChoiceBean> list, OnItemClickListener onItemClickListener, OnBtnBuyClickListener onBtnBuyClickListener) {
        this.list = list;
        this.onBtnBuyClickListener = onBtnBuyClickListener;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_more_plan_choice, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        PlanChoiceBean item = list.get(position);
        holder.tvName.setText(item.getCombo_name());
        holder.tvMonthMoney.setText(item.getMonthly_sharing() + "");
        holder.tvMonth.setText("共" + item.getPeriod() + "个月");
        holder.tvDiscountMoney.setText(item.getDiscount_rate() * item.getOriginal_price() + "元");
        if (null != onBtnBuyClickListener)
            holder.btnBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBtnBuyClickListener.onClick(position);
                }
            });
        if (null != onItemClickListener) {
            holder.rlRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(v, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvMonthMoney, tvMonth, tvDiscountMoney;
        Button btnBuy;
        RelativeLayout rlRoot;

        public ViewHolder(View itemView) {
            super(itemView);
            rlRoot = (RelativeLayout) itemView.findViewById(R.id.rl_root);
            tvName = (TextView) itemView.findViewById(R.id.tv_plan_name);
            tvMonthMoney = (TextView) itemView.findViewById(R.id.tv_month_share);
            tvMonth = (TextView) itemView.findViewById(R.id.tv_month);
            tvDiscountMoney = (TextView) itemView.findViewById(R.id.tv_discount_money);
            btnBuy = (Button) itemView.findViewById(R.id.btn_buy);
        }
    }

    public interface OnBtnBuyClickListener {
        void onClick(int position);
    }
}
