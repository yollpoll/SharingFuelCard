package com.sharingfuelcard.sharingfuelcard.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.base.BaseActivity;
import com.sharingfuelcard.sharingfuelcard.http.Httptools;
import com.sharingfuelcard.sharingfuelcard.http.ResponseData;
import com.sharingfuelcard.sharingfuelcard.module.PlanChoiceBean;
import com.sharingfuelcard.sharingfuelcard.retrofitService.MineBalanceService;
import com.sharingfuelcard.sharingfuelcard.utils.ToastUtils;
import com.sharingfuelcard.sharingfuelcard.view.adapter.MainPlanChoiceAdapter;
import com.sharingfuelcard.sharingfuelcard.view.adapter.MorePlanChoiceAdapter;
import com.sharingfuelcard.sharingfuelcard.view.adapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 鹏祺 on 2017/9/15.
 */

public class MoreChoiceActivity extends BaseActivity {
    private RecyclerView rvMoreChoice;
    private List<PlanChoiceBean> list = new ArrayList<>();
    private MorePlanChoiceAdapter mAdapter;

    public static void gotoMoreChoiceActivity(Context context) {
        Intent intent = new Intent(context, MoreChoiceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_choice);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        super.initView();
        rvMoreChoice = (RecyclerView) findViewById(R.id.rv_more_choice);
    }

    @Override
    protected void initData() {
        super.initData();
        showBack();
        setTitle("更多套餐");

        mAdapter = new MorePlanChoiceAdapter(list, onItemClickListener, onBtnBuyClickListener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMoreChoice.setLayoutManager(layoutManager);
        rvMoreChoice.setAdapter(mAdapter);
        rvMoreChoice.setLayoutManager(layoutManager);
        getData();
    }

    private void getData() {
        Retrofit retrofit = Httptools.getInstance().getRetrofit();
        MineBalanceService service = retrofit.create(MineBalanceService.class);
        Call<ResponseData<List<PlanChoiceBean>>> call = service.getAllChoice();
        call.enqueue(new Callback<ResponseData<List<PlanChoiceBean>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<PlanChoiceBean>>> call, Response<ResponseData<List<PlanChoiceBean>>> response) {
                if (null != response.body()) {
                    list.addAll(response.body().getData());
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseData<List<PlanChoiceBean>>> call, Throwable t) {
                ToastUtils.showShort("网路错误");
            }
        });
    }

    private MorePlanChoiceAdapter.OnBtnBuyClickListener onBtnBuyClickListener = new MorePlanChoiceAdapter.OnBtnBuyClickListener() {
        @Override
        public void onClick(int position) {
            //购买
            String type = list.get(position).getType();
            if ("d".equals(type)) {
                //优选套餐
                GreatChoiceActivity.gotoGreatChoiceActivity(getContext());
            } else {
                //一般套餐
                PersonalChoiceActivity.gotoPersonalChoiceActivity(getContext());
            }
        }
    };

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onClick(View view, int position) {
        }
    };
}
