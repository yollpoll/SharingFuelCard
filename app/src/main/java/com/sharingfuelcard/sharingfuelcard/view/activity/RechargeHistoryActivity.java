package com.sharingfuelcard.sharingfuelcard.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.base.BaseActivity;
import com.sharingfuelcard.sharingfuelcard.http.Httptools;
import com.sharingfuelcard.sharingfuelcard.http.ResponseData;
import com.sharingfuelcard.sharingfuelcard.module.RechargeHistoryBean;
import com.sharingfuelcard.sharingfuelcard.retrofitService.RechageHistoryService;
import com.sharingfuelcard.sharingfuelcard.view.adapter.RechargeHistoryAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 鹏祺 on 2017/9/19.
 */

public class RechargeHistoryActivity extends BaseActivity {
    private RecyclerView rvHistory;
    private RechargeHistoryAdapter mAdapter;
    private List<RechargeHistoryBean> list = new ArrayList<>();

    public static void gotoRechargeHistoryActivity(Context context) {
        Intent intent = new Intent(context, RechargeHistoryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_history);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        super.initView();
        rvHistory = (RecyclerView) findViewById(R.id.rv_history);
    }

    @Override
    protected void initData() {
        super.initData();
        showBack();
        setTitle("历史充值记录");
        mAdapter = new RechargeHistoryAdapter(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvHistory.setAdapter(mAdapter);
        rvHistory.setLayoutManager(layoutManager);
        getData();
    }

    private void getData() {
        Retrofit retrofit = Httptools.getInstance().getRetrofit();
        RechageHistoryService rechageHistoryService = retrofit.create(RechageHistoryService.class);
        Call<ResponseData<List<RechargeHistoryBean>>> call = rechageHistoryService.getHistory();
        call.enqueue(new Callback<ResponseData<List<RechargeHistoryBean>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<RechargeHistoryBean>>> call, Response<ResponseData<List<RechargeHistoryBean>>> response) {
                try {
                    list.addAll(response.body().getData());
                    mAdapter.notifyDataSetChanged();
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<ResponseData<List<RechargeHistoryBean>>> call, Throwable t) {

            }
        });
    }
}
