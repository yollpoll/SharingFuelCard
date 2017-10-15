package com.sharingfuelcard.sharingfuelcard.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.base.BaseActivity;
import com.sharingfuelcard.sharingfuelcard.http.Httptools;
import com.sharingfuelcard.sharingfuelcard.http.ResponseData;
import com.sharingfuelcard.sharingfuelcard.module.MyCardMsgBean;
import com.sharingfuelcard.sharingfuelcard.retrofitService.BindCardService;
import com.sharingfuelcard.sharingfuelcard.retrofitService.MyFuelCardService;
import com.sharingfuelcard.sharingfuelcard.utils.Constant;
import com.sharingfuelcard.sharingfuelcard.view.weiget.CycleGalleryViewPager;
import com.sharingfuelcard.sharingfuelcard.view.weiget.ViewPagerIndex;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 鹏祺 on 2017/9/17.
 */

public class MyFeulCardActivity extends BaseActivity {
    private TextView tvCacncel, tvBuy, tvName, tvCardCode, tvCardName;
    private CycleGalleryViewPager vpCard;
    private ViewPagerIndex index;
    private List<MyCardMsgBean> list = new ArrayList<>();
    private Retrofit retrofit;
    private MyFuelCardService myFuelCardService;
    private PagerAdapter mAdapter;
    private RelativeLayout rlCard;
    private ImageView ivNoCard;

    public static void gotoMyFuelCardActivity(Context context) {
        Intent intent = new Intent(context, MyFeulCardActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_card);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        super.initView();
        tvCacncel = (TextView) findViewById(R.id.tv_cancel_bind);
        tvBuy = (TextView) findViewById(R.id.tv_buy);
        vpCard = (CycleGalleryViewPager) findViewById(R.id.vp_card);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvCardName = (TextView) findViewById(R.id.tv_card_type);
        tvCardCode = (TextView) findViewById(R.id.tv_card_code);
        index = (ViewPagerIndex) findViewById(R.id.vpindex);
        ivNoCard = (ImageView) findViewById(R.id.iv_nocard);
        rlCard = (RelativeLayout) findViewById(R.id.rl_card);

        tvBuy.setOnClickListener(this);
        tvCacncel.setOnClickListener(this);
        ivNoCard.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        setTitle("我的油卡");
        showBack();
        index.setIndexCount(list.size());
        mAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_my_card, container, false);
                TextView tvCardCode = (TextView) view.findViewById(R.id.tv_card_code);
                TextView tvCardName = (TextView) view.findViewById(R.id.tv_card_name);
                TextView tvName = (TextView) view.findViewById(R.id.tv_name);
                tvCardCode.setText(list.get(position).getOilcard_id());
                switch (list.get(position).getOilcard_type()) {
                    case Constant.SINOPEC:
                        tvCardName.setText("中国石化");
                        break;
                    case Constant.CNPC:
                        tvCardName.setText("中国石油");
                        break;
                }
                tvName.setText(list.get(position).getOilcard_name());
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public float getPageWidth(int position) {
                return 0.8f;
            }
        };
        vpCard.setAdapter(mAdapter);
        vpCard.setNarrowFactor(0.9f);
        vpCard.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (list.get(position).getOilcard_type()) {
                    case Constant.SINOPEC:
                        tvCardName.setText("中国石化");
                        break;
                    case Constant.CNPC:
                        tvCardName.setText("中国石油");
                        break;
                }
                tvName.setText(list.get(position).getOilcard_name());
                tvCardCode.setText(list.get(position).getOilcard_id());
                index.setCurrentCount(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        getData();
    }

    private void getData() {
        retrofit = Httptools.getInstance().getRetrofit();
        myFuelCardService = retrofit.create(MyFuelCardService.class);
        Call<ResponseData<List<MyCardMsgBean>>> call = myFuelCardService.getMyCardMsg();
        call.enqueue(new Callback<ResponseData<List<MyCardMsgBean>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<MyCardMsgBean>>> call, Response<ResponseData<List<MyCardMsgBean>>> response) {
                if (null == response.body()) {
                    ivNoCard.setVisibility(View.VISIBLE);
                    rlCard.setVisibility(View.GONE);
                    return;
                }
                list.addAll(response.body().getData());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseData<List<MyCardMsgBean>>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_nocard:
                ApplyFuelCardActivity.gotoApplyFurlCard(this);
                break;
        }
    }
}
