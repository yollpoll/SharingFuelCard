package com.sharingfuelcard.sharingfuelcard.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.base.BaseActivity;
import com.sharingfuelcard.sharingfuelcard.http.Httptools;
import com.sharingfuelcard.sharingfuelcard.http.ResponseData;
import com.sharingfuelcard.sharingfuelcard.module.BaseBean;
import com.sharingfuelcard.sharingfuelcard.module.UserBean;
import com.sharingfuelcard.sharingfuelcard.retrofitService.BindCardService;
import com.sharingfuelcard.sharingfuelcard.utils.SPUtiles;
import com.sharingfuelcard.sharingfuelcard.utils.ToastUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 鹏祺 on 2017/9/7.
 */

public class BindFuelActivity extends BaseActivity {
    public static final int TYPE_SINOPEC = 1;
    public static final int TYPE_CNPC = 2;
    public static final int TYPE_OTHERS = 3;
    private Button btnSubmit;
    private int cardType;
    private String imgUrl;
    private ImageView ivCard;
    private String cardId, cardPassword, cardName;
    private TextInputEditText edtCardId, edtCardPassword, edtCardName;


    public static void gotoBindFuelActivityByCNPC(Context context) {
        Intent intent = new Intent(context, BindFuelActivity.class);
        intent.putExtra("type", TYPE_CNPC);
        context.startActivity(intent);
    }

    public static void gotoBindFuelActivityBySinopec(Context context) {
        Intent intent = new Intent(context, BindFuelActivity.class);
        intent.putExtra("type", TYPE_SINOPEC);
        context.startActivity(intent);
    }

    public static void gotoBindFuelActivityByOthers(Context context, String imgUrl) {
        Intent intent = new Intent(context, BindFuelActivity.class);
        intent.putExtra("type", TYPE_OTHERS);
        intent.putExtra("imgUrl", imgUrl);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_fuel_card);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        super.initView();
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        ivCard = (ImageView) findViewById(R.id.iv_furl_card);
        edtCardId = (TextInputEditText) findViewById(R.id.edt_card_number);
        edtCardPassword = (TextInputEditText) findViewById(R.id.edt_card_pswd);
        edtCardName = (TextInputEditText) findViewById(R.id.edt_card_name);
        btnSubmit.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        super.initData();
        cardType = getIntent().getIntExtra("type", 3);
        switch (cardType) {
            case TYPE_CNPC:
                ivCard.setImageResource(R.mipmap.icon_cnpc);
                break;
            case TYPE_SINOPEC:
                ivCard.setImageResource(R.mipmap.icon_sinopec);
                break;
            case TYPE_OTHERS:
                //TODO glide
                imgUrl = getIntent().getStringExtra("imgUrl");
                break;
        }
        showBack();
        setHeadRightText("跳过");
        setTitle("登陆油卡");
    }

    @Override
    protected void onRightTVClick() {
        super.onRightTVClick();
        MainActivity.gotoMainActivity(this);
    }

    private void bind() {
        UserBean userBean = SPUtiles.getUser();
        String token = SPUtiles.getToken();
        if (null == userBean) {
            ToastUtils.showShort("获取用户信息失败，请重新登陆");
            return;
        }
        Retrofit retrofit = Httptools.getInstance().getRetrofit();
        BindCardService service = retrofit.create(BindCardService.class);
        Call<ResponseData<BaseBean>> call = service.bindCard(cardId, cardPassword, cardType, cardName);
        call.enqueue(new Callback<ResponseData<BaseBean>>() {
            @Override
            public void onResponse(Call<ResponseData<BaseBean>> call, Response<ResponseData<BaseBean>> response) {
                ToastUtils.showShort(response.body().getMessage());
                if (1 == response.body().getCode()) {
                    BindFuelActivity.this.finish();
                    MainActivity.gotoMainActivity(getContext());
                }

            }

            @Override
            public void onFailure(Call<ResponseData<BaseBean>> call, Throwable t) {

            }
        });
    }

    private boolean check() {
        cardId = edtCardId.getText().toString();
        cardName = edtCardName.getText().toString();
        cardPassword = edtCardPassword.getText().toString();
        if (TextUtils.isEmpty(cardId)) {
            ToastUtils.showShort("请填写卡号");
            return false;
        } else if (TextUtils.isEmpty(cardPassword)) {
            ToastUtils.showShort("请填写密码");
            return false;
        } else if (TextUtils.isEmpty(cardName)) {
            ToastUtils.showShort("请填写油卡名");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_submit:
                if (check())
//                MainActivity.gotoMainActivity(getContext());
                    bind();
                break;
        }
    }
}
