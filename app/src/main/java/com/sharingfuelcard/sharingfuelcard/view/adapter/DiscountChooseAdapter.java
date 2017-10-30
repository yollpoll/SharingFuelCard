package com.sharingfuelcard.sharingfuelcard.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.module.PlanChoiceBean;

import java.util.List;

/**
 * Created by 鹏祺 on 2017/10/19.
 */

public class DiscountChooseAdapter extends RecyclerView.Adapter<DiscountChooseAdapter.ViewHolder> {
    private List<PlanChoiceBean> list;
    private OnItemClickListener onItemClickListener;
    private Context context;

    public DiscountChooseAdapter(List<PlanChoiceBean> list, OnItemClickListener onItemClickListener) {
        this.list = list;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_personal_plan_discount, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final PlanChoiceBean item = list.get(position);
        holder.tvDiscount.setText(item.getDiscount_rate() + "折");
        holder.tvMonth.setText("周期" + item.getPeriod() + "个月");
        if (item.isCheck()) {
            holder.llRoot.setBackground(context.getResources().getDrawable(R.drawable.shape_discount_orange));
            holder.tvMonth.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.tvDiscount.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.llRoot.setBackground(context.getResources().getDrawable(R.drawable.shape_discount_gray));
            holder.tvMonth.setTextColor(context.getResources().getColor(R.color.colorTextGray));
            holder.tvDiscount.setTextColor(context.getResources().getColor(R.color.colorTextGray));
        }
        if (null != onItemClickListener)
            holder.llRoot.setOnClickListener(new View.OnClickListener() {
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
        LinearLayout llRoot;
        private TextView tvDiscount, tvMonth;

        public ViewHolder(View itemView) {
            super(itemView);
            llRoot = (LinearLayout) itemView.findViewById(R.id.ll_root);
            tvDiscount = (TextView) itemView.findViewById(R.id.tv_discount);
            tvMonth = (TextView) itemView.findViewById(R.id.tv_discount_month);
        }
    }
}
