package com.sharingfuelcard.sharingfuelcard.view.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gyf.barlibrary.ImmersionBar;
import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.base.BaseActivity;
import com.sharingfuelcard.sharingfuelcard.http.Httptools;
import com.sharingfuelcard.sharingfuelcard.http.ResponseData;
import com.sharingfuelcard.sharingfuelcard.http.Url;
import com.sharingfuelcard.sharingfuelcard.module.UserBean;
import com.sharingfuelcard.sharingfuelcard.retrofitService.SettingService;
import com.sharingfuelcard.sharingfuelcard.utils.SPUtiles;
import com.sharingfuelcard.sharingfuelcard.utils.ToastUtils;
import com.sharingfuelcard.sharingfuelcard.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 鹏祺 on 2017/9/7.
 * 个人中心
 */

public class SettingActivity extends BaseActivity {
    private TextView tvMyFuelCard, tvCurrentPlan, tvHistoryRecharge, tvChangePswd, tvChangeAdress,
            tvContact, tvUsername, tvTitle;
    private RelativeLayout rlTitle;
    private TextView tvCarCode;
    private ImageView ivAvatar;
    private UserBean userBean;
    private String address;
    private Retrofit retrofit;
    private Button btnLogout;
    private SettingService mSettingService;
    private Dialog dialogChangeAddress, dialogChangePassword;

    public static void gotoSettingActivity(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        super.initView();
        rlTitle = (RelativeLayout) findViewById(R.id.rl_title);
        tvMyFuelCard = (TextView) findViewById(R.id.tv_my_fuel_card);
        tvCurrentPlan = (TextView) findViewById(R.id.tv_current_plan);
        tvChangeAdress = (TextView) findViewById(R.id.tv_change_adress);
        tvHistoryRecharge = (TextView) findViewById(R.id.tv_history_recharge);
        tvChangePswd = (TextView) findViewById(R.id.tv_change_password);
        tvContact = (TextView) findViewById(R.id.tv_contact);
        btnLogout = (Button) findViewById(R.id.btn_logout);
        ivAvatar = (ImageView) findViewById(R.id.iv_avatar);
        tvtitle = (TextView) findViewById(R.id.tv_title);
        tvUsername = (TextView) findViewById(R.id.tv_username);
        tvCarCode = (TextView) findViewById(R.id.tv_car_code);

        tvMyFuelCard.setOnClickListener(this);
        tvCurrentPlan.setOnClickListener(this);
        tvHistoryRecharge.setOnClickListener(this);
        tvChangeAdress.setOnClickListener(this);
        tvChangePswd.setOnClickListener(this);
//        tvContact.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        ivAvatar.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        setTitle("个人中心");
        tvtitle.setTextColor(Color.parseColor("#ffffff"));
        setToolBarColor(Color.parseColor("#00000000"));
        ImmersionBar.with(this).fullScreen(true).init();
        int height = ImmersionBar.with(this).getStatusBarHeight(this);
        Utils.setMargins(rlTitle, 0, height, 0, 0);
        setHeadLeftImg(R.mipmap.icon_back_whie);

        retrofit = Httptools.getInstance().getRetrofit();
        mSettingService = retrofit.create(SettingService.class);
        userBean = SPUtiles.getUser();
        tvUsername.setText(userBean.getUsername());
        tvCarCode.setText(userBean.getLicense_plate());
        Glide.with(this).load(Url.IMG_HEAD_URL + userBean.getAvatar()).into(ivAvatar);
        getAddress();
    }

    @Override
    protected void onLeftIVClick() {
        super.onLeftIVClick();
        this.finish();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_my_fuel_card:
                MyFeulCardActivity.gotoMyFuelCardActivity(this);
                break;
            case R.id.tv_history_recharge:
                RechargeHistoryActivity.gotoRechargeHistoryActivity(this);
                break;
            case R.id.tv_current_plan:
                CurrentChoiceActivity.gotoCurrentChoiceActivity(this);
                break;
            case R.id.tv_change_adress:
                changeAddress();
                break;
            case R.id.btn_address_submit:
                setAddress();
                break;
            case R.id.btn_address_cancel:
                if (null != dialogChangeAddress)
                    dialogChangeAddress.dismiss();
                break;
            case R.id.tv_change_password:
                changePassword();
                break;
            case R.id.btn_password_submit:
                setPassword();
                break;
            case R.id.btn_password_cancel:
                dialogChangePassword.dismiss();
                break;
            case R.id.btn_logout:
                logout();
                break;
        }
    }

    private void logout() {
        SPUtiles.saveToken("");
        SPUtiles.saveUser(null);
        BaseActivity.closeAll();
        LuncherActivity.gotoLuncherActivity(this);
    }

    TextInputEditText edtOldPassword, edtNewPassword;

    private void changePassword() {
        dialogChangePassword = new Dialog(this);
        dialogChangePassword.show();
        dialogChangePassword.setContentView(R.layout.dialog_change_password);
        edtOldPassword = (TextInputEditText) dialogChangePassword.findViewById(R.id.edt_old_password);
        edtNewPassword = (TextInputEditText) dialogChangePassword.findViewById(R.id.edt_new_password);
        Button btnSubmit = (Button) dialogChangePassword.findViewById(R.id.btn_password_submit);
        Button btnCancel = (Button) dialogChangePassword.findViewById(R.id.btn_password_cancel);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    private void setPassword() {
        String oldPassword = edtOldPassword.getText().toString();
        if (TextUtils.isEmpty(oldPassword)) {
            ToastUtils.showShort("请输入旧密码");
            return;
        }
        String newPassword = edtNewPassword.getText().toString();
        if (TextUtils.isEmpty(newPassword)) {
            ToastUtils.showShort("请输入新密码");
            return;
        }
        Call<ResponseData<String>> callPassword = mSettingService.setPassword(oldPassword, newPassword);
        callPassword.enqueue(new Callback<ResponseData<String>>() {
            @Override
            public void onResponse(Call<ResponseData<String>> call, Response<ResponseData<String>> response) {
                ToastUtils.showShort(response.body().getMessage());
                if (response.body().getCode() == 1) {
                    dialogChangePassword.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseData<String>> call, Throwable t) {

            }
        });
    }

    private void setAddress() {
        String newAddress = edtAddress.getText().toString();
        Call<ResponseData<String>> call = mSettingService.setAddress(newAddress);
        call.enqueue(new Callback<ResponseData<String>>() {
            @Override
            public void onResponse(Call<ResponseData<String>> call, Response<ResponseData<String>> response) {
                ToastUtils.showShort(response.body().getMessage());
                if (response.body().getCode() == 1)
                    dialogChangeAddress.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseData<String>> call, Throwable t) {

            }
        });
    }

    TextInputEditText edtAddress;

    private void changeAddress() {
        dialogChangeAddress = new Dialog(this);
        dialogChangeAddress.show();
        dialogChangeAddress.setContentView(R.layout.dialog_change_address);
        TextView tvCurrentAddress = (TextView) dialogChangeAddress.findViewById(R.id.tv_current_address);
        edtAddress = (TextInputEditText) dialogChangeAddress.findViewById(R.id.edt_address);
        Button btnSubmit = (Button) dialogChangeAddress.findViewById(R.id.btn_address_submit);
        Button btnCancel = (Button) dialogChangeAddress.findViewById(R.id.btn_address_cancel);
        if (TextUtils.isEmpty(address))
            address = "当前没有收货地址";
        tvCurrentAddress.setText(address);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    private void getAddress() {
        Call<ResponseData<String>> callAddress = mSettingService.getAddress();
        callAddress.enqueue(new Callback<ResponseData<String>>() {
            @Override
            public void onResponse(Call<ResponseData<String>> call, Response<ResponseData<String>> response) {
                address = response.body().getData();
            }

            @Override
            public void onFailure(Call<ResponseData<String>> call, Throwable t) {

            }
        });
    }
}
