package com.sharingfuelcard.sharingfuelcard.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.base.BaseActivity;
import com.sharingfuelcard.sharingfuelcard.http.Httptools;
import com.sharingfuelcard.sharingfuelcard.http.ResponseData;
import com.sharingfuelcard.sharingfuelcard.module.BuyCardBean;
import com.sharingfuelcard.sharingfuelcard.module.MyCardMsgBean;
import com.sharingfuelcard.sharingfuelcard.retrofitService.BuyPlanService;
import com.sharingfuelcard.sharingfuelcard.retrofitService.MyFuelCardService;
import com.sharingfuelcard.sharingfuelcard.utils.Constant;
import com.sharingfuelcard.sharingfuelcard.utils.ToastUtils;
import com.sharingfuelcard.sharingfuelcard.utils.Utils;
import com.sharingfuelcard.sharingfuelcard.view.adapter.CardAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 鹏祺 on 2017/9/19.
 */

public class GreatChoiceActivity extends BaseActivity implements AliPayActivity.AliPayCallbackInterface {
    private TextView tvAdd, tvReduce, tvMoney, tvBuy;
    private int money = 10000;
    private Retrofit mRetrofit;
    private LinearLayout llBottom;
    private RadioGroup rgBuy;
    private TextView tvAllMoney;
    private TextView tvRechargeDetail;
    private TextView tvDetail;
    private TextView tvMonthSharing;
    private double monthly_sharing = 500;
    private double combo_price = 10000;
    private int period = 60;
    private BuyPlanService buyPlanService;
    private double rate;


    private List<MyCardMsgBean> listCard = new ArrayList<>();
    private MyFuelCardService cardService;
    private PopupWindow popCard;
    private RecyclerView rvCard;
    private RadioGroup rbCard;
    private CardAdapter cardAdapter;
    private TextView tvConfirm;//确定油卡
    private List<MyCardMsgBean> chooseCards = new ArrayList<>();

    public static void gotoGreatChoiceActivity(Context context, double rate) {
        Intent intent = new Intent(context, GreatChoiceActivity.class);
        intent.putExtra("rate", rate);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_great_choice);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        super.initView();
        tvAdd = (TextView) findViewById(R.id.tv_add);
        tvReduce = (TextView) findViewById(R.id.tv_reduce);
        tvMoney = (TextView) findViewById(R.id.tv_money);
        tvBuy = (TextView) findViewById(R.id.tv_buy);
        rgBuy = (RadioGroup) findViewById(R.id.rg_buy);
        llBottom = (LinearLayout) findViewById(R.id.ll_bottom);
        tvAllMoney = (TextView) findViewById(R.id.tv_all_money);
        tvMonthSharing = (TextView) findViewById(R.id.tv_month_money);
        tvDetail = (TextView) findViewById(R.id.tv_detail);
        tvRechargeDetail = (TextView) findViewById(R.id.tv_recharge_detail);

        tvAdd.setOnClickListener(this);
        tvReduce.setOnClickListener(this);
        tvBuy.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        setToolBarColor(Color.parseColor("#00000000"));
        setTitle("优选套餐");
        tvtitle.setTextColor(getResources().getColor(R.color.white));
        ImmersionBar.with(this).fullScreen(true).init();
        int height = ImmersionBar.with(this).getStatusBarHeight(this);
        Utils.setMargins(rlTitle, 0, height, 0, 0);
        setHeadLeftImg(R.mipmap.icon_back_whie);

        tvAllMoney.setText("合计:" + combo_price + "元");

        mRetrofit = Httptools.getInstance().getRetrofit();
        rate = getIntent().getDoubleExtra("rate", 1);
        setMoneyDetai(combo_price);

        int barHeight = Utils.getNavigationBarHeight(this);
        Utils.setMargins(llBottom, 0, 0, 0, barHeight);
        getCard();
    }

    @Override
    protected void onLeftIVClick() {
        super.onLeftIVClick();
        this.finish();
    }

    private void setMoneyDetai(double combo_price) {
        monthly_sharing = (int) (combo_price / (period * rate));
        tvDetail.setText("原价" + (int) (monthly_sharing * period) + "元，折扣价" + combo_price + "元，共节省" + (int) (monthly_sharing * period - combo_price) + "元");
        tvMonthSharing.setText((int) monthly_sharing + "");
        tvRechargeDetail.setText(monthly_sharing + "元/月*" + period + "个月=" + monthly_sharing * period + "元");
        tvAllMoney.setText("合计:" + combo_price);
    }

    private void changeMoney(int money) {
        if (this.money + money <= 0) {
            ToastUtils.showShort("至少购买一份");
            return;
        }
        this.money += money;
        tvMoney.setText(this.money + "");
    }    //获取油卡信息

    private void getCard() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_choose_card, null, false);
        rbCard = (RadioGroup) view.findViewById(R.id.rg_card);
//        rvCard = (RecyclerView) view.findViewById(R.id.rv_card);
        tvConfirm = (TextView) view.findViewById(R.id.tv_confirm);
        tvConfirm.setOnClickListener(this);


        WindowManager wm = this.getWindowManager();
        popCard = new PopupWindow(view, wm.getDefaultDisplay().getWidth(), WindowManager.LayoutParams.WRAP_CONTENT);
        popCard.setAnimationStyle(R.style.popup_window_bottombar);
        popCard.setContentView(view);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        popCard.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        popCard.setBackgroundDrawable(dw);

        cardService = mRetrofit.create(MyFuelCardService.class);
        Call<ResponseData<List<MyCardMsgBean>>> call = cardService.getMyCardMsg();
        call.enqueue(new Callback<ResponseData<List<MyCardMsgBean>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<MyCardMsgBean>>> call, Response<ResponseData<List<MyCardMsgBean>>> response) {
                if (response.body().getCode() == 1) {
                    listCard.addAll(response.body().getData());
//                    cardAdapter.notifyDataSetChanged();
                } else {
                    ToastUtils.showShort("获取油卡失败");
                }
                for (int i = 0; i < listCard.size(); i++) {
                    RadioButton radioButton = new RadioButton(getContext());
                    radioButton.setText(listCard.get(i).getOilcard_name());
                    ViewGroup.LayoutParams layoutParams = new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                    radioButton.setLayoutParams(layoutParams);
                    radioButton.setId(i);
                    rbCard.addView(radioButton);
                }

            }

            @Override
            public void onFailure(Call<ResponseData<List<MyCardMsgBean>>> call, Throwable t) {

            }
        });
    }

    private void buy() {
        if (rgBuy.getCheckedRadioButtonId() == R.id.rb_wechatpay) {
            ToastUtils.showShort("暂未开通微信支付");
            return;
        }
        if (!popCard.isShowing()) {
            //在控件上方显示
//            popCard.showAsDropDown(llBottom);
            WindowManager wm = this.getWindowManager();
            popCard.showAtLocation(llBottom, Gravity.BOTTOM, wm.getDefaultDisplay().getWidth() / 2
                    , llBottom.getHeight());
        } else {
            popCard.dismiss();
        }
    }

    private void submit() {
        buyPlanService = mRetrofit.create(BuyPlanService.class);
        if (listCard.size() == 0)
            return;
        String cardId = listCard.get(rbCard.getCheckedRadioButtonId()).getOilcard_id();
        Call<ResponseData<BuyCardBean>> call = buyPlanService.buyPlan(cardId, Constant.GREAT_PLAN, monthly_sharing,
                period, (int) (combo_price / rate) + "", "", combo_price + "");
        call.enqueue(new Callback<ResponseData<BuyCardBean>>() {
            @Override
            public void onResponse(Call<ResponseData<BuyCardBean>> call, Response<ResponseData<BuyCardBean>> response) {
                if (1 == response.body().getCode()) {
                    AliPayActivity.gotoPay(GreatChoiceActivity.this, response.body().getData().getOrderInfo(),
                            GreatChoiceActivity.this);
                }
            }

            @Override
            public void onFailure(Call<ResponseData<BuyCardBean>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_reduce:
                changeMoney(-10000);
                if (combo_price - 10000 <= 0)
                    return;
                combo_price -= 10000;
                setMoneyDetai(combo_price);
                break;
            case R.id.tv_add:
                changeMoney(10000);
                combo_price += 10000;
                setMoneyDetai(combo_price);
                break;
            case R.id.tv_buy:
                buy();
                break;
            case R.id.tv_confirm:
                submit();
                break;
        }
    }

    @Override
    public void paySuccess() {
        GreatChoiceActivity.this.finish();
    }

    @Override
    public void payFail() {

    }

    @Override
    public void payWaitting() {

    }
}
