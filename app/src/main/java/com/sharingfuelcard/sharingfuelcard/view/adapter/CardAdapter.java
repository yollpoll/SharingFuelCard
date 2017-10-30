package com.sharingfuelcard.sharingfuelcard.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.module.MyCardMsgBean;

import java.util.List;

/**
 * Created by 鹏祺 on 2017/10/20.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private List<MyCardMsgBean> list;
    private OnCheckListener onCheckedChangeListener;

    public CardAdapter(List<MyCardMsgBean> list, OnCheckListener onCheckedChangeListener) {
        this.list = list;
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        MyCardMsgBean item = list.get(position);
        holder.rbCard.setText(item.getOilcard_name());
        holder.rbCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onCheckedChangeListener.onCheck(position, isChecked);
            }
        });
        holder.rbCard.setEnabled(item.isEnable());
        holder.rbCard.setChecked(item.isIcCheck());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox rbCard;

        public ViewHolder(View itemView) {
            super(itemView);
            rbCard = (CheckBox) itemView.findViewById(R.id.rb_card);
        }
    }

    public interface OnCheckListener {
        void onCheck(int position, boolean check);
    }
}
