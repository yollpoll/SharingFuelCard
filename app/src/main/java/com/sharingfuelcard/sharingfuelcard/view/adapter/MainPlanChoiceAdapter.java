package com.sharingfuelcard.sharingfuelcard.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sharingfuelcard.sharingfuelcard.R;

/**
 * Created by 鹏祺 on 2017/9/15.
 */

public class MainPlanChoiceAdapter extends RecyclerView.ViewHolder {

    public MainPlanChoiceAdapter(View itemView) {
        super(itemView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvChoiceType, tvMonth, tvMoney;
        RelativeLayout rlRoot;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvMonth = (TextView) itemView.findViewById(R.id.tv_month);
            tvChoiceType = (TextView) itemView.findViewById(R.id.tv_type);
            tvMonth = (TextView) itemView.findViewById(R.id.tv_month);
        }
    }
}
