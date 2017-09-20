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
import android.widget.TextView;

import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.base.BaseActivity;
import com.sharingfuelcard.sharingfuelcard.module.MyCardBean;
import com.sharingfuelcard.sharingfuelcard.view.weiget.CycleGalleryViewPager;
import com.sharingfuelcard.sharingfuelcard.view.weiget.ViewPagerIndex;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 鹏祺 on 2017/9/17.
 */

public class MyFeulCardActivity extends BaseActivity {
    private TextView tvCacncel, tvBuy, tvName, tvCardCode, tvCardName;
    private CycleGalleryViewPager vpCard;
    private ViewPagerIndex index;
    private List<MyCardBean> list = new ArrayList<>();

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

        tvBuy.setOnClickListener(this);
        tvCacncel.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        setTitle("我的油卡");
        showBack();
        getData();
        index.setIndexCount(list.size());
        vpCard.setAdapter(new PagerAdapter() {
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
                tvCardCode.setText(list.get(position).getCode());
                tvCardName.setText(list.get(position).getCardName());
                tvName.setText(list.get(position).getName());
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
        });
        vpCard.setNarrowFactor(0.9f);
        vpCard.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvCardName.setText(list.get(position).getCardName());
                tvName.setText(list.get(position).getName());
                tvCardCode.setText(list.get(position).getCode());
                index.setCurrentCount(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void getData() {
        for (int i = 0; i < 3; i++) {
            MyCardBean myCardBean = new MyCardBean();
            myCardBean.setCardName("油卡" + i);
            myCardBean.setName("持卡人姓名");
            myCardBean.setCode("i" + "0000000000");
            list.add(myCardBean);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }
}
