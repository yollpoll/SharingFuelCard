package com.sharingfuelcard.sharingfuelcard.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sharingfuelcard.sharingfuelcard.R;
import com.sharingfuelcard.sharingfuelcard.base.BaseActivity;
import com.sharingfuelcard.sharingfuelcard.http.Httptools;
import com.sharingfuelcard.sharingfuelcard.http.ResponseData;
import com.sharingfuelcard.sharingfuelcard.module.RegisterBean;
import com.sharingfuelcard.sharingfuelcard.retrofitService.RegisterService;
import com.sharingfuelcard.sharingfuelcard.utils.FileUtils;
import com.sharingfuelcard.sharingfuelcard.utils.ToastUtils;
import com.sharingfuelcard.sharingfuelcard.utils.Utils;
import com.sharingfuelcard.sharingfuelcard.view.weiget.CannotClickChildRl;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 鹏祺 on 2017/9/6.
 */

public class RegisterVertifyMsgActivity extends BaseActivity {
    private String phone;
    private String password;
    private String vertifyCode;
    private Button btnRegister;
    private TextInputEditText edtUsername, edtGender, edtCarCode, edtCarMsg;
    private String username, gender, carCode = "", carMsg = "";
    private Retrofit retrofit;
    private RegisterService registerService;
    private AppCompatCheckBox cbConfirm;
    private ImageView ivAvatar;
    private File file;
    private CannotClickChildRl rlGender;


    public static void gotoRegisterVertifiyMsgActivity(Context context, String phone, String password, String vertifyCode) {
        Intent intent = new Intent(context, RegisterVertifyMsgActivity.class);
        intent.putExtra("phone", phone);
        intent.putExtra("password", password);
        intent.putExtra("vertifyCode", vertifyCode);
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
                String path = FileUtils.getPathByUri(uri, this);
                file = new File(path);
                ivAvatar.setImageURI(uri);
                break;
            case Utils.PIC_FROM_CAMERA:
                if (resultCode != RESULT_OK) {
                    ToastUtils.showShort("拍照失败");
                    break;
                }
                path = FileUtils.getPathByUri(Utils.filrUri, this);
                ivAvatar.setImageURI(Utils.filrUri);
                file = new File(path);
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertify_msg);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        super.initView();
        btnRegister = (Button) findViewById(R.id.btn_register);
        edtUsername = (TextInputEditText) findViewById(R.id.edt_username);
        edtGender = (TextInputEditText) findViewById(R.id.edt_gender);
        edtCarCode = (TextInputEditText) findViewById(R.id.edt_car_code);
        edtCarMsg = (TextInputEditText) findViewById(R.id.edt_car_msg);
        cbConfirm = (AppCompatCheckBox) findViewById(R.id.cb_confirm);
        btnRegister.setOnClickListener(this);
        ivAvatar = (ImageView) findViewById(R.id.iv_avatar);
        rlGender = (CannotClickChildRl) findViewById(R.id.rl_gender);

        rlGender.setOnClickListener(this);
        ivAvatar.setOnClickListener(this);
        edtGender.setOnClickListener(this);
    }

    private boolean check() {
        username = edtUsername.getText().toString();
        gender = edtGender.getText().toString();
        carCode = edtCarCode.getText().toString();
        carMsg = edtCarMsg.getText().toString();
        if (TextUtils.isEmpty(username)) {
            ToastUtils.showShort("请输入用户名");
            return false;
        } else if (TextUtils.isEmpty(gender)) {
            ToastUtils.showShort("请输入性别");
            return false;
        }
//        else if (TextUtils.isEmpty(carCode)) {
//            ToastUtils.showShort("请输入车牌号");
//            return false;
//        } else if (TextUtils.isEmpty(carMsg)) {
//            ToastUtils.showShort("请输入车辆型号");
//            return false;
//        }
        return true;
    }

    private void register() {
        if (null == file) {
            ToastUtils.showShort("请上传头像");
            return;
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part1 = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        Call<ResponseData<RegisterBean>> callRegister = registerService.register(phone, password, username, gender, vertifyCode,
                carCode, carMsg, part1);
        callRegister.enqueue(new Callback<ResponseData<RegisterBean>>() {
            @Override
            public void onResponse(Call<ResponseData<RegisterBean>> call, Response<ResponseData<RegisterBean>> response) {
                if (null == response.body()) {
                    ToastUtils.showShort(response.message());
                    return;
                }

                ToastUtils.showShort(response.body().getMessage());
                RegisterVertifyMsgActivity.this.finish();
            }

            @Override
            public void onFailure(Call<ResponseData<RegisterBean>> call, Throwable t) {
                ToastUtils.showShort(t.getMessage());
            }
        });

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_register:
                if (check())
                    register();
                break;
            case R.id.iv_avatar:
                Utils.showChoosePicDialog(RegisterVertifyMsgActivity.this);
                break;
            case R.id.rl_gender:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("请选择性别").setPositiveButton("男", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edtGender.setText("男");
                    }
                }).setNegativeButton("女", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edtGender.setText("女");
                    }
                }).create().show();
                break;
        }
    }

    @Override
    protected void initData() {
        super.initData();
        showBack();
        setTitle("基本信息");
        phone = getIntent().getStringExtra("phone");
        password = getIntent().getStringExtra("password");
        vertifyCode = getIntent().getStringExtra("vertifyCode");

        retrofit = Httptools.getInstance().getRetrofit();
        registerService = retrofit.create(RegisterService.class);
        btnRegister.setEnabled(false);
        cbConfirm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                btnRegister.setEnabled(isChecked);
            }
        });
    }

}
