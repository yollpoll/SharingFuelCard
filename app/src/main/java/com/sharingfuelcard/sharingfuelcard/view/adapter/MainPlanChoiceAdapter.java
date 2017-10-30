package com.sharingfuelcard.sharingfuelcard.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.module.HomeDataBean;
import com.sharingfuelcard.sharingfuelcard.module.PlanChoiceBean;

import java.util.List;

/**
 * Created by 鹏祺 on 2017/9/15.
 */

public class MainPlanChoiceAdapter extends RecyclerView.Adapter<MainPlanChoiceAdapter.ViewHolder> {

    private List<HomeDataBean.Choice> list;
    private OnItemClickListener onItemClickListener;

    public MainPlanChoiceAdapter(List<HomeDataBean.Choice> list, OnItemClickListener onItemClickListener) {
        this.list = list;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_plan_choice, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        HomeDataBean.Choice item = list.get(position);
        holder.tvMonthShare.setText("月享" + item.getMonthly_sharing() + "");
        holder.tvMonth.setText("共" + item.getPeriod() + "个月");
        holder.tvPlanName.setText(item.getCombo_name());
        holder.tvDetail.setText("折扣后优惠" + (int) ((1-item.getDiscount_rate()) * item.getOriginal_price()) + "元");
        if (null != onItemClickListener)
            holder.rlRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(v, position);
                }
            });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMonthShare, tvMonth, tvPlanName, tvDetail;
        RelativeLayout rlRoot;

        public ViewHolder(View itemView) {
            super(itemView);
            tvMonthShare = (TextView) itemView.findViewById(R.id.tv_month_share);
            tvMonth = (TextView) itemView.findViewById(R.id.tv_month);
            tvPlanName = (TextView) itemView.findViewById(R.id.tv_plan_name);
            tvDetail = (TextView) itemView.findViewById(R.id.tv_detail);
            rlRoot = (RelativeLayout) itemView.findViewById(R.id.rl_root);
        }
    }

}
