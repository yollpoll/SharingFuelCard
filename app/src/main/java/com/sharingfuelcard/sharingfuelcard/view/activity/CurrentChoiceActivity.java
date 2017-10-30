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
import com.sharingfuelcard.sharingfuelcard.module.CurrentPlanBean;
import com.sharingfuelcard.sharingfuelcard.retrofitService.CurrentPlanService;
import com.sharingfuelcard.sharingfuelcard.view.adapter.CurrentPlanAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 鹏祺 on 2017/9/19.
 */

public class CurrentChoiceActivity extends BaseActivity {
    private RecyclerView rvCurrentPlan;
    private CurrentPlanAdapter mAdapter;
    private List<CurrentPlanBean> list=new ArrayList<>();
    private Retrofit retrofit;
    private CurrentPlanService service;

    public static void gotoCurrentChoiceActivity(Context context) {
        Intent intent = new Intent(context, CurrentChoiceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_choice);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        super.initView();
        rvCurrentPlan= (RecyclerView) findViewById(R.id.rv_current_plan);
    }

    @Override
    protected void initData() {
        super.initData();
        showBack();
        setTitle("当前套餐");

        retrofit = Httptools.getInstance().getRetrofit();
        service = retrofit.create(CurrentPlanService.class);

        mAdapter = new CurrentPlanAdapter(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvCurrentPlan.setAdapter(mAdapter);
        rvCurrentPlan.setLayoutManager(layoutManager);
        getData();
    }

    private void getData() {
        Call<ResponseData<List<CurrentPlanBean>>> call = service.getPlan();
        call.enqueue(new Callback<ResponseData<List<CurrentPlanBean>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<CurrentPlanBean>>> call, Response<ResponseData<List<CurrentPlanBean>>> response) {
                if (response.body().getCode() == 1) {
                    list.addAll(response.body().getData());
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseData<List<CurrentPlanBean>>> call, Throwable t) {

            }
        });
    }
}
