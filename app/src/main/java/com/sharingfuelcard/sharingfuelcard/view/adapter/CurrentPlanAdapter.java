package com.sharingfuelcard.sharingfuelcard.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.module.CurrentPlanBean;
import com.sharingfuelcard.sharingfuelcard.utils.Constant;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 鹏祺 on 2017/10/25.
 */

public class CurrentPlanAdapter extends RecyclerView.Adapter<CurrentPlanAdapter.ViewHolder> {
    private List<CurrentPlanBean> list;

    public CurrentPlanAdapter(List<CurrentPlanBean> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_current_plan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CurrentPlanBean item = list.get(position);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(Long.parseLong(item.getEffectiveTime()));
        String dateStr = dateFormat.format(date);
        holder.tvDate.setText(dateStr);
        holder.tvBalance.setText("套餐余额:" + item.getBalance());
        holder.tvCard.setText("油卡" + item.getOilcardId());
        holder.tvMonthSharing.setText("月享" + item.getMonthlySharing());
        String type = item.getComboType();
        switch (type) {
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
        holder.tvType.setText(type);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvCard, tvBalance, tvType, tvMonthSharing;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvCard = (TextView) itemView.findViewById(R.id.tv_card);
            tvBalance = (TextView) itemView.findViewById(R.id.tv_balance);
            tvType = (TextView) itemView.findViewById(R.id.tv_type);
            tvMonthSharing = (TextView) itemView.findViewById(R.id.tv_month_share);
        }
    }
}
