package com.sharingfuelcard.sharingfuelcard.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.FindCallback;
import com.gyf.barlibrary.ImmersionBar;
import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.base.BaseActivity;
import com.sharingfuelcard.sharingfuelcard.http.Httptools;
import com.sharingfuelcard.sharingfuelcard.http.ResponseData;
import com.sharingfuelcard.sharingfuelcard.module.BuyCardBean;
import com.sharingfuelcard.sharingfuelcard.module.MyCardMsgBean;
import com.sharingfuelcard.sharingfuelcard.module.PlanChoiceBean;
import com.sharingfuelcard.sharingfuelcard.myInterface.OnBuyPlanCheckListener;
import com.sharingfuelcard.sharingfuelcard.retrofitService.BuyCardService;
import com.sharingfuelcard.sharingfuelcard.retrofitService.BuyPlanService;
import com.sharingfuelcard.sharingfuelcard.retrofitService.MineBalanceService;
import com.sharingfuelcard.sharingfuelcard.retrofitService.MyFuelCardService;
import com.sharingfuelcard.sharingfuelcard.utils.Constant;
import com.sharingfuelcard.sharingfuelcard.utils.ToastUtils;
import com.sharingfuelcard.sharingfuelcard.utils.Utils;
import com.sharingfuelcard.sharingfuelcard.view.adapter.CardAdapter;
import com.sharingfuelcard.sharingfuelcard.view.adapter.DiscountChooseAdapter;
import com.sharingfuelcard.sharingfuelcard.view.adapter.OnItemClickListener;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 鹏祺 on 2017/9/19.
 */

public class PersonalChoiceActivity extends BaseActivity implements OnBuyPlanCheckListener, RadioGroup.OnCheckedChangeListener,
        AliPayActivity.AliPayCallbackInterface {
    public static final String PAY_WEY_ALI = "ali";
    public static final String PAY_WEY_WECHAT = "wechat";
    private List<PlanChoiceBean> mList = new ArrayList<>();
    private Retrofit mRetrofit;
    private BuyPlanService buyPlanService;
    private MineBalanceService mService;
    private Call<ResponseData<List<PlanChoiceBean>>> mCall;
    private TextView tvMonthSharing;
    private TextView tvDetail;
    private PlanChoiceBean currentPlan;
    private TextView tvRechargeDetail;
    private TextView tvFinalMoney;
    private GridLayout mGridLayout;
    private String type;
    private LinearLayout llBottom;
    private RadioGroup rgBuy;
    private OnBuyPlanCheckListener onCheckListener;
    private TextView tvBuy;
    private String payWey = PAY_WEY_ALI;

    private List<MyCardMsgBean> listCard = new ArrayList<>();
    private MyFuelCardService cardService;
    private PopupWindow popCard;
    private RecyclerView rvCard;
    private RadioGroup rbCard;
    private CardAdapter cardAdapter;
    private TextView tvConfirm;//确定油卡
    private List<MyCardMsgBean> chooseCards = new ArrayList<>();


    public static void gotoPersonalChoiceActivity(Context context, String type) {
        Intent intent = new Intent(context, PersonalChoiceActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_choice);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        super.initView();
        llBottom = (LinearLayout) findViewById(R.id.ll_bottom);
        mGridLayout = (GridLayout) findViewById(R.id.gl_discount);
        tvMonthSharing = (TextView) findViewById(R.id.tv_month_money);
        tvDetail = (TextView) findViewById(R.id.tv_detail);
        tvRechargeDetail = (TextView) findViewById(R.id.tv_recharge_detail);
        tvFinalMoney = (TextView) findViewById(R.id.tv_final_money);
        tvBuy = (TextView) findViewById(R.id.tv_buy);
        rgBuy = (RadioGroup) findViewById(R.id.rg_buy);

        tvBuy.setOnClickListener(this);
        rgBuy.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initData() {
        type = getIntent().getStringExtra("type");
        setToolBarColor(Color.parseColor("#00000000"));
        tvtitle.setTextColor(getResources().getColor(R.color.white));
        ImmersionBar.with(this).fullScreen(true).init();
        int height = ImmersionBar.with(this).getStatusBarHeight(this);
        Utils.setMargins(rlTitle, 0, height, 0, 0);
        setHeadLeftImg(R.mipmap.icon_back_whie);
        switch (type) {
            case Constant.PERSONAL_PLAN:
                setTitle("个人套餐");
                break;
            case Constant.FAMILY_PLAN:
                setTitle("家庭套餐");
                break;
            case Constant.COMPANY_PLAN:
                setTitle("企业套餐");
                break;
        }

        onCheckListener = this;
        mRetrofit = Httptools.getInstance().getRetrofit();
        mService = mRetrofit.create(MineBalanceService.class);
        mCall = mService.getAllChoice();

        int barHeight = Utils.getNavigationBarHeight(this);
        Utils.setMargins(llBottom, 0, 0, 0, barHeight);
        getCard();
        getData();
    }


    @Override
    protected void onLeftIVClick() {
        super.onLeftIVClick();
        this.finish();
    }

    //获取油卡信息
    private void getCard() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_choose_card, null, false);
        rbCard = (RadioGroup) view.findViewById(R.id.rg_card);
//        rvCard = (RecyclerView) view.findViewById(R.id.rv_card);
        tvConfirm = (TextView) view.findViewById(R.id.tv_confirm);
        tvConfirm.setOnClickListener(this);
//        cardAdapter = new CardAdapter(listCard, new CardAdapter.OnCheckListener() {
//            @Override
//            public void onCheck(int position, boolean check) {
//                if (check) {
//                    chooseCards.add(listCard.get(position));
//                } else {
//                    chooseCards.remove(listCard.get(position));
//                }
//            }
//        });
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        rvCard.setAdapter(cardAdapter);
//        rvCard.setLayoutManager(layoutManager);


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

    private void getData() {
        mCall.enqueue(new Callback<ResponseData<List<PlanChoiceBean>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<PlanChoiceBean>>> call, Response<ResponseData<List<PlanChoiceBean>>> response) {
                if (1 == response.body().getCode()) {
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        if (type.equals(response.body().getData().get(i).getType()))
                            mList.add(response.body().getData().get(i));
                    }
                    if (mList.size() > 0) {
                        mList.get(0).setCheck(true);
//                        currentPlan = mList.get(0);
                        onCheckListener.onCheck(mList.get(0));
                    }
                    addItem();
                }
            }

            @Override
            public void onFailure(Call<ResponseData<List<PlanChoiceBean>>> call, Throwable t) {

            }
        });
    }


    private void addItem() {
        for (int i = 0; i < mList.size(); i++) {
            PlanChoiceBean item = mList.get(i);
            final int position = i;
            View view = LayoutInflater.from(this).inflate(R.layout.item_personal_plan_discount, mGridLayout, false);
            TextView tvMonth = (TextView) view.findViewById(R.id.tv_discount_month);
            TextView tvDiscount = (TextView) view.findViewById(R.id.tv_discount);
            LinearLayout llRoot = (LinearLayout) view.findViewById(R.id.ll_root);
            tvDiscount.setText((int) (item.getDiscount_rate() * 10) + "折");
            tvMonth.setText("周期" + item.getPeriod() + "个月");

            if (item.isCheck()) {
                llRoot.setBackground(getResources().getDrawable(R.drawable.shape_discount_orange));
                tvMonth.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvDiscount.setTextColor(getResources().getColor(R.color.colorPrimary));
            } else {
                llRoot.setBackground(getResources().getDrawable(R.drawable.shape_discount_gray));
                tvMonth.setTextColor(getResources().getColor(R.color.colorTextGray));
                tvDiscount.setTextColor(getResources().getColor(R.color.colorTextGray));
            }
            llRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(v, position);
                }
            });
            mGridLayout.addView(view);
        }
        onCheckListener.onCheck(mList.get(0));
    }

    private void notifyData() {
        for (int i = 0; i < mList.size(); i++) {
            View view = mGridLayout.getChildAt(i);
            TextView tvMonth = (TextView) view.findViewById(R.id.tv_discount_month);
            TextView tvDiscount = (TextView) view.findViewById(R.id.tv_discount);
            LinearLayout llRoot = (LinearLayout) view.findViewById(R.id.ll_root);
            if (mList.get(i).isCheck()) {
                llRoot.setBackground(getResources().getDrawable(R.drawable.shape_discount_orange));
                tvMonth.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvDiscount.setTextColor(getResources().getColor(R.color.colorPrimary));
            } else {
                llRoot.setBackground(getResources().getDrawable(R.drawable.shape_discount_gray));
                tvMonth.setTextColor(getResources().getColor(R.color.colorTextGray));
                tvDiscount.setTextColor(getResources().getColor(R.color.colorTextGray));
            }
        }
    }

    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onClick(View view, int position) {
            onCheckListener.onCheck(mList.get(position));
            boolean isCheck = mList.get(position).isCheck();
            for (int i = 0; i < mList.size(); i++) {
                mList.get(i).setCheck(false);
            }
            mList.get(position).setCheck(!isCheck);
            notifyData();
        }
    };

    @Override
    public void onCheck(PlanChoiceBean item) {
        currentPlan = item;
        tvFinalMoney.setText("合计:" + (int) (item.getOriginal_price()) + "元");
        tvMonthSharing.setText((int) item.getMonthly_sharing() + "");
        tvDetail.setText("原价" + (int) item.getCombo_price() + "元,折扣价" +
                (int) item.getOriginal_price() + "元,共节省" +
                (int) (item.getCombo_price() - item.getOriginal_price()) + "元");
        tvRechargeDetail.setText((int) item.getMonthly_sharing() + "元/月*" +  item.getPeriod() + "个月=" + (int) item.getMonthly_sharing() * item.getPeriod()+"元");
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
        Call<ResponseData<BuyCardBean>> call = buyPlanService.buyPlan(cardId, type, currentPlan.getMonthly_sharing(),
                currentPlan.getPeriod(), currentPlan.getCombo_price() + "", "", currentPlan.getOriginal_price() + "");
        call.enqueue(new Callback<ResponseData<BuyCardBean>>() {
            @Override
            public void onResponse(Call<ResponseData<BuyCardBean>> call, Response<ResponseData<BuyCardBean>> response) {
                if (1 == response.body().getCode()) {
                    if (null == response.body().getData()) {
                        ToastUtils.showShort("没有数据");
                    } else {
                        AliPayActivity.gotoPay(PersonalChoiceActivity.this, response.body().getData().getOrderInfo(),
                                PersonalChoiceActivity.this);
                    }
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
            case R.id.tv_buy:
                buy();
                break;
            case R.id.tv_confirm:
                submit();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rb_wechatpay:
                ToastUtils.showShort("微信支付尚未开通");
                break;
            case R.id.rb_alipay:
                payWey = PAY_WEY_ALI;
                break;
        }
    }

    @Override
    public void paySuccess() {
        PersonalChoiceActivity.this.finish();
    }

    @Override
    public void payFail() {
    }

    @Override
    public void payWaitting() {

    }
}
