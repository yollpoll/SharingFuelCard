package com.sharingfuelcard.sharingfuelcard.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.gyf.barlibrary.BarParams;
import com.gyf.barlibrary.ImmersionBar;
import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.base.BaseActivity;
import com.sharingfuelcard.sharingfuelcard.base.MyApplication;
import com.sharingfuelcard.sharingfuelcard.http.Httptools;
import com.sharingfuelcard.sharingfuelcard.http.ResponseData;
import com.sharingfuelcard.sharingfuelcard.module.HomeDataBean;
import com.sharingfuelcard.sharingfuelcard.module.PersonalBalanceBean;
import com.sharingfuelcard.sharingfuelcard.module.PlanChoiceBean;
import com.sharingfuelcard.sharingfuelcard.retrofitService.HomeService;
import com.sharingfuelcard.sharingfuelcard.retrofitService.MineBalanceService;
import com.sharingfuelcard.sharingfuelcard.utils.ToastUtils;
import com.sharingfuelcard.sharingfuelcard.utils.Utils;
import com.sharingfuelcard.sharingfuelcard.view.adapter.MainPlanChoiceAdapter;
import com.sharingfuelcard.sharingfuelcard.view.adapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends BaseActivity {
    private Button btnSetting, btnRegisterFuelCard;
    private TextView tvBalance, tvMonthTimes, tvMonthSharing, tvTitle, tvMoreChoice, tvHeadLeft;
    private RecyclerView rvPlan;
    private RelativeLayout rlTitle;
    private Retrofit retrofit;
    private MineBalanceService service;
    private HomeService mHomeService;
    private List<HomeDataBean.Choice> mChoices = new ArrayList<>();
    private MainPlanChoiceAdapter mAdapter;
    private HomeDataBean homeDataBean;


    public static void gotoMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        super.initView();
        tvMoreChoice = (TextView) findViewById(R.id.tv_more_choice);
        tvtitle = (TextView) findViewById(R.id.tv_title);
        rlTitle = (RelativeLayout) findViewById(R.id.rl_title);
        btnSetting = (Button) findViewById(R.id.btn_setting);
        btnRegisterFuelCard = (Button) findViewById(R.id.btn_register_fuel_card);
        tvBalance = (TextView) findViewById(R.id.tv_balance);
        tvMonthSharing = (TextView) findViewById(R.id.tv_month_sharing);
        tvMonthTimes = (TextView) findViewById(R.id.tv_month_times);
        rvPlan = (RecyclerView) findViewById(R.id.rv_plan);
        tvHeadLeft = (TextView) findViewById(R.id.tv_head_left);

        tvMoreChoice.setOnClickListener(this);
        btnSetting.setOnClickListener(this);
        btnRegisterFuelCard.setOnClickListener(this);
        rvPlan.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        setToolBarColor(Color.parseColor("#00000000"));
        setHeadLeftText("设置");
        setHeadRightImg(R.mipmap.icon_add_card);
        setTitle("首页");
        tvtitle.setTextColor(getResources().getColor(R.color.white));
        tvHeadLeft.setTextColor(getResources().getColor(R.color.white));
        ImmersionBar.with(this).fullScreen(true).init();
        int height = ImmersionBar.with(this).getStatusBarHeight(this);
        Utils.setMargins(rlTitle, 0, height, 0, 0);

        mAdapter = new MainPlanChoiceAdapter(mChoices, onItemClickListener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvPlan.setLayoutManager(layoutManager);
        rvPlan.setAdapter(mAdapter);

        retrofit = Httptools.getInstance().getRetrofit();
        service = retrofit.create(MineBalanceService.class);

        int barHeight = Utils.getNavigationBarHeight(this);
        Utils.setMargins(rvPlan, 0, 0, 0, barHeight);
        AVQuery<AVObject> query = new AVQuery<>("data");
        query.findInBackground(callback);
        getHomeData();
    }


    private void getHomeData() {
        mHomeService = retrofit.create(HomeService.class);
        Call<ResponseData<HomeDataBean>> call = mHomeService.getHomeData();
        call.enqueue(new Callback<ResponseData<HomeDataBean>>() {
            @Override
            public void onResponse(Call<ResponseData<HomeDataBean>> call, Response<ResponseData<HomeDataBean>> response) {
                homeDataBean = response.body().getData();
                setBalance();
                setChoice();
            }

            @Override
            public void onFailure(Call<ResponseData<HomeDataBean>> call, Throwable t) {
            }
        });
    }

    FindCallback callback = new FindCallback<AVObject>() {
        @Override
        public void done(List<AVObject> list, AVException e) {
            String packageName = MainActivity.this.getPackageName();
            if (!packageName.equals(list.get(0).getString("appid"))) {
                MainActivity.this.finish();
            }
        }
    };

    private void setBalance() {
        tvBalance.setText((int) (Double.parseDouble(homeDataBean.getBalance())) + "");
        tvMonthSharing.setText((int) (Double.parseDouble(homeDataBean.getMonthlySharing())) + "");
        tvMonthTimes.setText(homeDataBean.getSpareTime());
    }

    private void setChoice() {
        mChoices.addAll(homeDataBean.getHOT());
        mAdapter.notifyDataSetChanged();
    }


    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onClick(View view, int position) {
            String type = mChoices.get(position).getType();
            if ("d".equals(type)) {
                //优选套餐
                GreatChoiceActivity.gotoGreatChoiceActivity(getContext(), mChoices.get(position).getDiscount_rate());
            } else {
                //一般套餐
                PersonalChoiceActivity.gotoPersonalChoiceActivity(getContext(), mChoices.get(position).getType());
            }
        }
    };


    @Override
    protected void onLeftTVClick() {
        super.onLeftTVClick();
        SettingActivity.gotoSettingActivity(getContext());
    }


    @Override
    protected void onRightIVClick() {
        super.onRightIVClick();
        RegisterFuelCardActivity.gotoApplyFurlCardActivity(getContext());
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_more_choice:
                MoreChoiceActivity.gotoMoreChoiceActivity(this);
                break;
        }
    }
}
