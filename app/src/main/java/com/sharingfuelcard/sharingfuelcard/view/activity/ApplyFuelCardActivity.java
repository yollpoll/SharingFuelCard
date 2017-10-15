package com.sharingfuelcard.sharingfuelcard.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.base.BaseActivity;
import com.sharingfuelcard.sharingfuelcard.http.Httptools;
import com.sharingfuelcard.sharingfuelcard.http.ResponseData;
import com.sharingfuelcard.sharingfuelcard.retrofitService.BuyCardService;
import com.sharingfuelcard.sharingfuelcard.retrofitService.RegisterService;
import com.sharingfuelcard.sharingfuelcard.utils.FileUtils;
import com.sharingfuelcard.sharingfuelcard.utils.ToastUtils;
import com.sharingfuelcard.sharingfuelcard.utils.Utils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 鹏祺 on 2017/9/7.
 */

public class ApplyFuelCardActivity extends BaseActivity {
    private static final int PHOTO1 = 1;
    private static final int PHOTO2 = 2;

    private Button btnSubmit;
    private ImageView ivPhoto1, ivPhoto2;
    private String path;
    private Bitmap bitmap;
    private File file1, file2;
    private int photo = 0;
    private TextInputEditText edtTrueName, edtPassport, edtPhone, edtSmsCode, edtAdress;
    private String trueName, identifyCode, tel, smsCode, adress;
    private String vertifyCode;
    private Retrofit retrofit;
    private RegisterService mRegisterService;
    private BuyCardService mBuyCardService;
    private TextView tvGetSmsCode;


    public static void gotoApplyFurlCard(Context context) {
        Intent intent = new Intent(context, ApplyFuelCardActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Utils.PIC_FROM_PHOTO:
                if (resultCode != RESULT_OK) {
                    ToastUtils.showShort("获取图片失败");
                    break;
                }
                Uri uri = data.getData();
                path = FileUtils.getPathByUri(uri, this);
                bitmap = Utils.compressBitmap(path, 1024, 1024);
                switch (photo) {
                    case PHOTO1:
                        ivPhoto1.setImageBitmap(bitmap);
                        file1 = new File(path);
                        break;
                    case PHOTO2:
                        ivPhoto2.setImageBitmap(bitmap);
                        file2 = new File(path);
                        break;
                }

                break;
            case Utils.PIC_FROM_CAMERA:
                if (resultCode != RESULT_OK) {
                    ToastUtils.showShort("拍照失败");
                    break;
                }
                path = FileUtils.getPathByUri(Utils.filrUri, this);
                bitmap = Utils.compressBitmap(path, 1024, 1024);
                switch (photo) {
                    case PHOTO1:
                        ivPhoto1.setImageBitmap(bitmap);
                        file1 = new File(path);
                        break;
                    case PHOTO2:
                        file2 = new File(path);
                        ivPhoto2.setImageBitmap(bitmap);
                        break;
                }
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_fuel_card);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        super.initView();
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        ivPhoto1 = (ImageView) findViewById(R.id.iv_photo1);
        ivPhoto2 = (ImageView) findViewById(R.id.iv_photo2);
        edtTrueName = (TextInputEditText) findViewById(R.id.edt_name);
        edtPassport = (TextInputEditText) findViewById(R.id.edt_identify_code);
        edtPhone = (TextInputEditText) findViewById(R.id.edt_phone);
        edtSmsCode = (TextInputEditText) findViewById(R.id.edt_vertify_code);
        edtAdress = (TextInputEditText) findViewById(R.id.edt_adress);
        tvGetSmsCode = (TextView) findViewById(R.id.tv_get_vertify_code);

        tvGetSmsCode.setOnClickListener(this);
        ivPhoto1.setOnClickListener(this);
        ivPhoto2.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        setTitle("申请油卡");
        showBack();
        retrofit = Httptools.getInstance().getRetrofit();
        mRegisterService = retrofit.create(RegisterService.class);
    }

    private boolean checkInput() {
        trueName = edtTrueName.getText().toString();
        identifyCode = edtPassport.getText().toString();
        tel = edtPhone.getText().toString();
        smsCode = edtSmsCode.getText().toString();
        adress = edtAdress.getText().toString();
        if (TextUtils.isEmpty(trueName)) {
            ToastUtils.showShort("请输入真实姓名");
            return false;
        } else if (TextUtils.isEmpty(identifyCode)) {
            ToastUtils.showShort("请输入身份证号码");
            return false;
        } else if (TextUtils.isEmpty(tel)) {
            ToastUtils.showShort("请输入手机号");
            return false;
        } else if (TextUtils.isEmpty(smsCode)) {
            ToastUtils.showShort("请输入验证码");
            return false;
        } else if (TextUtils.isEmpty(adress)) {
            ToastUtils.showShort("请填写地址");
            return false;
        } else if (!smsCode.equals(vertifyCode)) {
            ToastUtils.showShort("验证码错误");
            return false;
        }
        if (null == file1 || null == file2) {
            ToastUtils.showShort("请添加身份证");
            return false;
        }
        return true;
    }

    private void buyCard() {
        mBuyCardService = retrofit.create(BuyCardService.class);
        RequestBody body1 = RequestBody.create(MediaType.parse("multipart/form-data"), file1);
        RequestBody body2 = RequestBody.create(MediaType.parse("multipart/form-data"), file2);
        Call<ResponseData<String>> call = mBuyCardService.buyCard(identifyCode, trueName, adress, tel, "",
                body1, body2);
        call.enqueue(new Callback<ResponseData<String>>() {
            @Override
            public void onResponse(Call<ResponseData<String>> call, Response<ResponseData<String>> response) {
                if (response.body().getCode() == 200) {
                    ToastUtils.showShort("购买成功");
                    ApplyFuelCardActivity.this.finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseData<String>> call, Throwable t) {
                Log.d("spq", "xxxxxxxxxxx");

            }
        });
    }

    private void getSmsCode() {
        tel = edtPhone.getText().toString();
        Call<ResponseData<String>> call = mRegisterService.getSmsCode(tel);
        call.enqueue(new Callback<ResponseData<String>>() {
            @Override
            public void onResponse(Call<ResponseData<String>> call, Response<ResponseData<String>> response) {
                String[] code = response.body().getData().split(":");
                if (response.body().getCode() == 0) {
                    ToastUtils.showShort("访问失败");
                    return;
                } else {
                    if (code.length == 2) {
                        String tel = edtPhone.getText().toString();
                        if (!tel.equals(code[0])) {
                            ToastUtils.showShort("手机号码不同");
                            return;
                        }
                        vertifyCode = code[1];

                    } else {
                        ToastUtils.showShort("验证码出错");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseData<String>> call, Throwable t) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_submit:
                if (checkInput())
                    buyCard();
                break;
            case R.id.iv_photo1:
                Utils.showChoosePicDialog(ApplyFuelCardActivity.this);
                photo = PHOTO1;
                break;
            case R.id.iv_photo2:
                Utils.showChoosePicDialog(ApplyFuelCardActivity.this);
                photo = PHOTO2;
                break;
            case R.id.tv_get_vertify_code:
                getSmsCode();
                break;
        }
    }
}
